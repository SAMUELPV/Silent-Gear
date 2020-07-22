package net.silentchaos512.gear.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;
import net.minecraftforge.fml.RegistryObject;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.material.MaterialLayer;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.gear.material.MaterialManager;
import net.silentchaos512.gear.init.Registration;

import java.util.*;
import java.util.function.Function;

public class GearModel implements IModelGeometry<GearModel> {
    private final ItemCameraTransforms cameraTransforms;
    final GearType gearType;
    private final ICoreItem item;
    private GearModelOverrideList overrideList;

    public GearModel(ItemCameraTransforms cameraTransforms, GearType gearType) {
        this.cameraTransforms = cameraTransforms;
        this.gearType = gearType;
        this.item = Registration.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(item -> item instanceof ICoreItem && ((ICoreItem) item).getGearType() == this.gearType)
                .map(item -> (ICoreItem) item)
                .findFirst()
                .orElse(null);
    }

    public void clearCache() {
        if (overrideList != null) {
            overrideList.clearCache();
        }
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        overrideList = new GearModelOverrideList(this, owner, bakery, spriteGetter, modelTransform, modelLocation);
        return new BakedWrapper(this, owner, bakery, spriteGetter, modelTransform, modelLocation, overrideList);
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    public IBakedModel bake(List<MaterialLayer> layers,
                            int animationFrame,
                            String transformVariant,
                            IModelConfiguration owner,
                            ModelBakery bakery,
                            Function<RenderMaterial, TextureAtlasSprite> spriteGetter,
                            IModelTransform modelTransform,
                            ItemOverrideList overrideList,
                            ResourceLocation modelLocation) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        TransformationMatrix rotation = modelTransform.getRotation();
        ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transforms = PerspectiveMapWrapper.getTransforms(modelTransform);

        for (int i = 0; i < layers.size(); i++) {
            MaterialLayer layer = layers.get(i);
            TextureAtlasSprite texture = spriteGetter.apply(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, layer.getTexture(this.gearType, animationFrame)));
            builder.addAll(getQuadsForSprite(i, texture, rotation, layer.getColor()));
        }

        // No layers?
        if (layers.isEmpty()) {
            IMaterial material = MaterialManager.get(SilentGear.getId("example"));
            if (material != null) {
                buildFakeModel(spriteGetter, builder, rotation, material);
            } else {
                // Shouldn't happen, but...
                TextureAtlasSprite texture = spriteGetter.apply(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, SilentGear.getId("item/error")));
                builder.addAll(getQuadsForSprite(0, texture, rotation, 0xFFFFFF));
            }
        }

        TextureAtlasSprite particle = spriteGetter.apply(owner.resolveTexture("particle"));

        return new BakedPerspectiveModel(builder.build(), particle, transforms, overrideList, rotation.isIdentity(), owner.isSideLit(), getCameraTransforms(transformVariant));
    }

    private void buildFakeModel(Function<RenderMaterial, TextureAtlasSprite> spriteGetter, ImmutableList.Builder<BakedQuad> builder, TransformationMatrix rotation, IMaterial material) {
        // This method will display an example tool for items with no data (ie, for advancements)
        if (!gearType.matches(GearType.ARMOR)) {
            MaterialLayer exampleRod = material.getMaterialDisplay(ItemStack.EMPTY, PartType.ROD).getFirstLayer();
            if (exampleRod != null) {
                builder.addAll(getQuadsForSprite(0, spriteGetter.apply(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, exampleRod.getTexture(gearType, 0))), rotation, exampleRod.getColor()));
            }
        }

        MaterialLayer exampleMain = material.getMaterialDisplay(ItemStack.EMPTY, PartType.MAIN).getFirstLayer();
        if (exampleMain != null) {
            builder.addAll(getQuadsForSprite(0, spriteGetter.apply(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, exampleMain.getTexture(gearType, 0))), rotation, exampleMain.getColor()));
        }

        if (gearType.matches(GearType.RANGED_WEAPON)) {
            MaterialLayer exampleBowstring = material.getMaterialDisplay(ItemStack.EMPTY, PartType.BOWSTRING).getFirstLayer();
            if (exampleBowstring != null) {
                builder.addAll(getQuadsForSprite(0, spriteGetter.apply(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, exampleBowstring.getTexture(gearType, 0))), rotation, exampleBowstring.getColor()));
            }
        }
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<RenderMaterial> ret = new HashSet<>();

        ret.add(new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, SilentGear.getId("item/error")));

        for (PartTextures tex : PartTextures.getTextures(this.gearType)) {
            int animationFrames = tex.isAnimated() ? item.getAnimationFrames() : 1;
            for (int i = 0; i < animationFrames; ++i) {
                ret.add(getTexture(tex.getTexture(), i));
            }
        }

        return ret;
    }

    private RenderMaterial getTexture(ResourceLocation tex, int animationFrame) {
        String path = "item/" + gearType.getName() + "/" + tex.getPath();
        String suffix = animationFrame > 0 ? "_" + animationFrame : "";
        ResourceLocation location = new ResourceLocation(tex.getNamespace(), path + suffix);
        return new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, location);
    }

    @Override
    public Collection<? extends IModelGeometryPart> getParts() {
        return Collections.emptyList();
    }

    @Override
    public Optional<? extends IModelGeometryPart> getPart(String name) {
        return Optional.empty();
    }

    protected ItemCameraTransforms getCameraTransforms(String transformVariant) {
        return cameraTransforms;
    }

    //region Quad builders (credit to Tetra, https://github.com/mickelus/tetra/blob/master/src/main/java/se/mickelus/tetra/client/model/ModularItemModel.java)

    public static List<BakedQuad> getQuadsForSprite(int tintIndex, TextureAtlasSprite sprite, TransformationMatrix transform, int color) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        int uMax = sprite.getWidth();
        int vMax = sprite.getHeight();

        // todo: figure out why this doesn't work when considering fully transparent rows
        for (int v = 0; v < vMax; v++) {
            builder.add(buildSideQuad(transform, Direction.UP, tintIndex, color, sprite, 0, v, uMax));
            builder.add(buildSideQuad(transform, Direction.DOWN, tintIndex, color, sprite, 0, v + 1, uMax));
        }

        for (int u = 0; u < uMax; u++) {
            builder.add(buildSideQuad(transform, Direction.EAST, tintIndex, color, sprite, u + 1, 0, vMax));
            builder.add(buildSideQuad(transform, Direction.WEST, tintIndex, color, sprite, u, 0, vMax));
        }

        // front
        builder.add(buildQuad(transform, Direction.NORTH, sprite, tintIndex, color,
                0, 0, 7.5f / 16f, sprite.getMinU(), sprite.getMaxV(),
                0, 1, 7.5f / 16f, sprite.getMinU(), sprite.getMinV(),
                1, 1, 7.5f / 16f, sprite.getMaxU(), sprite.getMinV(),
                1, 0, 7.5f / 16f, sprite.getMaxU(), sprite.getMaxV()
        ));
        // back
        builder.add(buildQuad(transform, Direction.SOUTH, sprite, tintIndex, color,
                0, 0, 8.5f / 16f, sprite.getMinU(), sprite.getMaxV(),
                1, 0, 8.5f / 16f, sprite.getMaxU(), sprite.getMaxV(),
                1, 1, 8.5f / 16f, sprite.getMaxU(), sprite.getMinV(),
                0, 1, 8.5f / 16f, sprite.getMinU(), sprite.getMinV()
        ));

        return builder.build();
    }

    private static BakedQuad buildSideQuad(TransformationMatrix transform, Direction side, int tintIndex, int color, TextureAtlasSprite sprite, int u, int v, int size) {
        final float eps = 1e-2f;

        int width = sprite.getWidth();
        int height = sprite.getHeight();

        float x0 = (float) u / width;
        float y0 = (float) v / height;

        float x1 = x0;
        float y1 = y0;

        float z0 = 7.5f / 16f;
        float z1 = 8.5f / 16f;

        switch (side) {
            case WEST:
                z0 = 8.5f / 16f;
                z1 = 7.5f / 16f;

                y1 = (float) (v + size) / height;
                break;
            case EAST:
                y1 = (float) (v + size) / height;
                break;
            case DOWN:
                z0 = 8.5f / 16f;
                z1 = 7.5f / 16f;

                x1 = (float) (u + size) / width;
                break;
            case UP:
                x1 = (float) (u + size) / width;
                break;
            default:
                throw new IllegalArgumentException("can't handle z-oriented side");
        }

        float dx = side.getDirectionVec().getX() * eps / width;
        float dy = side.getDirectionVec().getY() * eps / height;

        float u0 = 16f * (x0 - dx);
        float u1 = 16f * (x1 - dx);
        float v0 = 16f * (1f - y0 - dy);
        float v1 = 16f * (1f - y1 - dy);

        return buildQuad(
                transform, remap(side), sprite, tintIndex, color,
                x0, y0, z0, sprite.getInterpolatedU(u0), sprite.getInterpolatedV(v0),
                x1, y1, z0, sprite.getInterpolatedU(u1), sprite.getInterpolatedV(v1),
                x1, y1, z1, sprite.getInterpolatedU(u1), sprite.getInterpolatedV(v1),
                x0, y0, z1, sprite.getInterpolatedU(u0), sprite.getInterpolatedV(v0)
        );
    }

    private static Direction remap(Direction side) {
        // getOpposite is related to the swapping of V direction
        return side.getAxis() == Direction.Axis.Y ? side.getOpposite() : side;
    }

    private static BakedQuad buildQuad(TransformationMatrix transform, Direction side, TextureAtlasSprite sprite, int tintIndex, int color,
                                       float x0, float y0, float z0, float u0, float v0,
                                       float x1, float y1, float z1, float u1, float v1,
                                       float x2, float y2, float z2, float u2, float v2,
                                       float x3, float y3, float z3, float u3, float v3) {
        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);

        builder.setQuadTint(tintIndex);
        builder.setQuadOrientation(side);

        boolean hasTransform = !transform.isIdentity();
        IVertexConsumer consumer = hasTransform ? new TRSRTransformer(builder, transform) : builder;

        putVertex(consumer, side, x0, y0, z0, u0, v0, color);
        putVertex(consumer, side, x1, y1, z1, u1, v1, color);
        putVertex(consumer, side, x2, y2, z2, u2, v2, color);
        putVertex(consumer, side, x3, y3, z3, u3, v3, color);

        return builder.build();
    }

    private static void putVertex(IVertexConsumer consumer, Direction side, float x, float y, float z, float u, float v, int color) {
        VertexFormat format = consumer.getVertexFormat();
        for (int e = 0; e < format.getElements().size(); e++) {
            switch (format.getElements().get(e).getUsage()) {
                case POSITION:
                    consumer.put(e, x, y, z, 1f);
                    break;
                case COLOR:
                    float r = ((color >> 16) & 0xFF) / 255f; // red
                    float g = ((color >> 8) & 0xFF) / 255f; // green
                    float b = ((color >> 0) & 0xFF) / 255f; // blue
                    float a = ((color >> 24) & 0xFF) / 255f; // alpha

                    // reset alpha to 1 if it's 0 to avoid mistakes & make things cleaner
                    a = a == 0 ? 1 : a;

                    consumer.put(e, r, g, b, a);
                    break;
                case NORMAL:
                    float offX = (float) side.getXOffset();
                    float offY = (float) side.getYOffset();
                    float offZ = (float) side.getZOffset();
                    consumer.put(e, offX, offY, offZ, 0f);
                    break;
                case UV:
                    if (format.getElements().get(e).getIndex() == 0) {
                        consumer.put(e, u, v, 0f, 1f);
                        break;
                    }
                    // else fallthrough to default
                default:
                    consumer.put(e);
                    break;
            }
        }
    }

    //endregion
}

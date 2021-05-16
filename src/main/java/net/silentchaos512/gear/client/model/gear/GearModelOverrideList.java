package net.silentchaos512.gear.client.model.gear;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.material.IMaterialDisplay;
import net.silentchaos512.gear.api.material.MaterialLayer;
import net.silentchaos512.gear.api.part.IPartDisplay;
import net.silentchaos512.gear.api.part.PartDataList;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.client.material.MaterialDisplayManager;
import net.silentchaos512.gear.client.model.ModelErrorLogging;
import net.silentchaos512.gear.client.model.PartTextures;
import net.silentchaos512.gear.config.Config;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.gear.part.CompoundPart;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.gear.item.gear.CoreCrossbow;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class GearModelOverrideList extends ItemOverrideList {
    private final Cache<CacheKey, IBakedModel> bakedModelCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final GearModel model;
    private final IModelConfiguration owner;
    private final ModelBakery bakery;
    private final Function<RenderMaterial, TextureAtlasSprite> spriteGetter;
    private final IModelTransform modelTransform;
    private final ResourceLocation modelLocation;

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public GearModelOverrideList(GearModel model,
                                 IModelConfiguration owner,
                                 ModelBakery bakery,
                                 Function<RenderMaterial, TextureAtlasSprite> spriteGetter,
                                 IModelTransform modelTransform,
                                 ResourceLocation modelLocation) {
        this.model = model;
        this.owner = owner;
        this.bakery = bakery;
        this.spriteGetter = spriteGetter;
        this.modelTransform = modelTransform;
        this.modelLocation = modelLocation;
    }

    static boolean isDebugLoggingEnabled() {
        return Config.Common.modelAndTextureLogging.get();
    }

    @Nullable
    @Override
    public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
        int animationFrame = getAnimationFrame(stack, worldIn, entityIn);
        CacheKey key = getKey(model, stack, worldIn, entityIn, animationFrame);
        try {
            return bakedModelCache.get(key, () -> getOverrideModel(key, stack, worldIn, entityIn, animationFrame));
        } catch (Exception e) {
            ModelErrorLogging.notifyOfException(e, "gear item");
        }
        return model;
    }

    private static int getAnimationFrame(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        return ((ICoreItem) stack.getItem()).getAnimationFrame(stack, world, entity);
    }

    private IBakedModel getOverrideModel(CacheKey key, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn, int animationFrame) {
        boolean broken = GearHelper.isBroken(stack);
        if (isDebugLoggingEnabled()) {
            SilentGear.LOGGER.info("getOverrideModel for {} ({})", stack.getDisplayName().getString(), broken ? "broken" : "normal");
            SilentGear.LOGGER.info("- model key {}", key.data);
        }
        List<MaterialLayer> layers = new ArrayList<>();

        for (PartData part : getPartsInRenderOrder(stack)) {
            if (((ICoreItem) stack.getItem()).hasTexturesFor(part.getType())) {
                addSimplePartLayers(layers, part, stack);

                if (part.get() instanceof CompoundPart) {
                    MaterialInstance mat = CompoundPart.getPrimaryMaterial(part);
                    if (mat != null) {
                        addWithBlendedColor(layers, part, mat, stack);
                    }
                }
            }
        }

        // TODO: Make this not a special case...
        if (stack.getItem() instanceof CoreCrossbow) {
            getCrossbowCharge(stack, worldIn, entityIn).ifPresent(layers::add);
        }

        return model.bake(stack, layers, animationFrame, "test", owner, bakery, spriteGetter, modelTransform, this, modelLocation);
    }

    private static PartDataList getPartsInRenderOrder(ItemStack stack) {
        PartDataList unsorted = GearData.getConstructionParts(stack);
        PartDataList ret = PartDataList.of();
        ICoreItem item = (ICoreItem) stack.getItem();

        for (PartType partType : item.getRenderParts()) {
            ret.addAll(unsorted.getPartsOfType(partType));
        }

        for (PartData part : unsorted) {
            if (!ret.contains(part)) {
                ret.add(part);
            }
        }

        return ret;
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private static void addWithBlendedColor(List<MaterialLayer> list, PartData part, MaterialInstance material, ItemStack stack) {
        IMaterialDisplay model = MaterialDisplayManager.get(material);
        GearType gearType = GearHelper.getType(stack);
        List<MaterialLayer> layers = model.getLayerList(gearType, part, material).getLayers();
        addColorBlendedLayers(list, part, stack, layers);
    }

    private static void addSimplePartLayers(List<MaterialLayer> list, PartData part, ItemStack stack) {
        IPartDisplay model = MaterialDisplayManager.get(part.get());
        if (model != null) {
            GearType gearType = GearHelper.getType(stack);
            List<MaterialLayer> layers = model.getLayers(gearType, part).getLayers();
            addColorBlendedLayers(list, part, stack, layers);
        }
    }

    private static void addColorBlendedLayers(List<MaterialLayer> list, PartData part, ItemStack stack, List<MaterialLayer> layers) {
        for (int i = 0; i < layers.size(); i++) {
            MaterialLayer layer = layers.get(i);
            if ((layer.getColor() & 0xFFFFFF) < 0xFFFFFF) {
                int blendedColor = part.getColor(stack, i, 0);
                MaterialLayer coloredLayer = layer.withColor(blendedColor);
                list.add(coloredLayer);
                if (isDebugLoggingEnabled()) {
                    debugLogLayer(coloredLayer, Color.format(blendedColor));
                }
            } else {
                list.add(layer);
                if (isDebugLoggingEnabled()) {
                    debugLogLayer(layer, "colorless");
                }
            }
        }
    }

    private static void debugLogLayer(MaterialLayer layer, String colorStr) {
        //noinspection ConstantConditions -- unknown NPE for some data pack users
        String partTypeStr = layer.getPartType() != null
                ? SilentGear.shortenId(layer.getPartType().getName())
                : "null type?";
        SilentGear.LOGGER.info("  - add layer {} ({}, {})",
                layer.getTextureId(),
                partTypeStr,
                colorStr);
    }

    private static Optional<MaterialLayer> getCrossbowCharge(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        // TODO: Maybe should add an ICoreItem method to get additional layers?
        IItemPropertyGetter chargedProperty = ItemModelsProperties.func_239417_a_(stack.getItem(), new ResourceLocation("charged"));
        IItemPropertyGetter fireworkProperty = ItemModelsProperties.func_239417_a_(stack.getItem(), new ResourceLocation("firework"));

        if (chargedProperty != null && fireworkProperty != null) {
            boolean charged = chargedProperty.call(stack, world, entity) > 0;
            boolean firework = fireworkProperty.call(stack, world, entity) > 0;
            if (charged) {
                if (firework) {
                    return Optional.of(new MaterialLayer(PartTextures.CHARGED_FIREWORK, Color.VALUE_WHITE));
                }
                return Optional.of(new MaterialLayer(PartTextures.CHARGED_ARROW, Color.VALUE_WHITE));
            }
        }

        return Optional.empty();
    }

    private static CacheKey getKey(IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int animationFrame) {
        String brokenSuffix = GearHelper.isBroken(stack) ? "broken" : "";
        String chargeSuffix = getCrossbowCharge(stack, world, entity)
                .map(l -> ";" + l.getTextureId().getPath())
                .orElse("");
        return new CacheKey(model, GearData.getModelKey(stack, animationFrame) + brokenSuffix + chargeSuffix);
    }

    @Override
    public ImmutableList<ItemOverride> getOverrides() {
        return super.getOverrides();
    }

    public void clearCache() {
        SilentGear.LOGGER.debug("Clearing model cache for {}", this.model.gearType);
        bakedModelCache.invalidateAll();
    }

    static final class CacheKey {
        final IBakedModel parent;
        final String data;

        CacheKey(IBakedModel parent, String hash) {
            this.parent = parent;
            this.data = hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return parent == cacheKey.parent && Objects.equals(data, cacheKey.data);
        }

        @Override
        public int hashCode() {
            return 31 * parent.hashCode() + data.hashCode();
        }
    }
}

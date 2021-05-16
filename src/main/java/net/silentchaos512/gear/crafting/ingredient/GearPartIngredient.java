/*
 * Silent Gear -- GearPartIngredient
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.gear.crafting.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.part.IGearPart;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.gear.gear.part.PartManager;
import net.silentchaos512.gear.util.TextUtil;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class GearPartIngredient extends Ingredient implements IGearIngredient {
    private final PartType type;

    private GearPartIngredient(PartType type) {
        // Note: gear parts are NOT loaded at this time!
        super(Stream.of());
        this.type = type;
    }

    public static GearPartIngredient of(PartType type) {
        return new GearPartIngredient(type);
    }

    @Override
    public PartType getPartType() {
        return type;
    }

    @Override
    public Optional<ITextComponent> getJeiHint() {
        IFormattableTextComponent typeText = new StringTextComponent(this.type.getShortName());
        IFormattableTextComponent text = TextUtil.withColor(typeText, Color.GOLD);
        return Optional.of(TextUtil.translate("jei", "partType", text));
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        PartData part = PartData.from(stack);
        return part != null && part.getType().equals(type);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        // Although gear parts are not available when the ingredient is constructed,
        // they are available later on
        Collection<IGearPart> parts = PartManager.getPartsOfType(this.type);
        if (!parts.isEmpty()) {
            return parts.stream()
                    .flatMap(part -> Stream.of(part.getIngredient().getMatchingStacks()))
                    .filter(stack -> !stack.isEmpty())
                    .toArray(ItemStack[]::new);
        }
        return super.getMatchingStacks();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean hasNoMatchingItems() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Serializer.NAME.toString());
        json.addProperty("part_type", this.type.getName().toString());
        return json;
    }

    public static final class Serializer implements IIngredientSerializer<GearPartIngredient> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation NAME = new ResourceLocation(SilentGear.MOD_ID, "part_type");

        private Serializer() {}

        @Override
        public GearPartIngredient parse(PacketBuffer buffer) {
            ResourceLocation typeName = buffer.readResourceLocation();
            PartType type = PartType.get(typeName);
            if (type == null) throw new JsonParseException("Unknown part type: " + typeName);
            return new GearPartIngredient(type);
        }

        @Override
        public GearPartIngredient parse(JsonObject json) {
            String typeName = JSONUtils.getString(json, "part_type", "");
            if (typeName.isEmpty())
                throw new JsonSyntaxException("'part_type' is missing");

            ResourceLocation id = typeName.contains(":")
                    ? new ResourceLocation(typeName)
                    : SilentGear.getId(typeName);
            PartType type = PartType.get(id);
            if (type == null)
                throw new JsonSyntaxException("part_type " + typeName + " does not exist");

            return new GearPartIngredient(type);
        }

        @Override
        public void write(PacketBuffer buffer, GearPartIngredient ingredient) {
            buffer.writeResourceLocation(ingredient.type.getName());
        }
    }
}

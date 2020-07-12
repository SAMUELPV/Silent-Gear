package net.silentchaos512.gear.crafting.recipe.salvage;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.init.ModRecipes;
import net.silentchaos512.gear.item.CompoundPartItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CompoundPartSalvagingRecipe extends SalvagingRecipe {
    public CompoundPartSalvagingRecipe(ResourceLocation recipeId) {
        super(recipeId);
    }

    @Override
    public List<ItemStack> getPossibleResults(IInventory inv) {
        ItemStack input = inv.getStackInSlot(0);
        List<ItemStack> ret = new ArrayList<>();

        if (input.getItem() instanceof CompoundPartItem) {
            CompoundPartItem.getMaterials(input).stream()
                    .map(MaterialInstance::getItem)
                    .filter(s -> !s.isEmpty())
                    .forEach(ret::add);
        }

        return ret;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return inv.getStackInSlot(0).getItem() instanceof CompoundPartItem;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.SALVAGING_COMPOUND_PART_SERIALIZER;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CompoundPartSalvagingRecipe> {
        @Override
        public CompoundPartSalvagingRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new CompoundPartSalvagingRecipe(recipeId);
        }

        @Nullable
        @Override
        public CompoundPartSalvagingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new CompoundPartSalvagingRecipe(recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, CompoundPartSalvagingRecipe recipe) {
        }
    }
}
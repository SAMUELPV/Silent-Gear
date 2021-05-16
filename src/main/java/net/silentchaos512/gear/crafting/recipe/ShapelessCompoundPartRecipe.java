package net.silentchaos512.gear.crafting.recipe;

import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.world.World;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.material.MaterialList;
import net.silentchaos512.gear.config.Config;
import net.silentchaos512.gear.gear.material.LazyMaterialInstance;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.init.ModRecipes;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.gear.util.Const;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapelessRecipe;

public class ShapelessCompoundPartRecipe extends ExtendedShapelessRecipe {
    private final CompoundPartItem item;

    public ShapelessCompoundPartRecipe(ShapelessRecipe recipe) {
        super(recipe);

        ItemStack output = recipe.getRecipeOutput();
        if (!(output.getItem() instanceof CompoundPartItem)) {
            throw new JsonParseException("result is not a compound part item: " + output);
        }
        this.item = (CompoundPartItem) output.getItem();
    }

    protected GearType getGearType() {
        return this.item.getGearType();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.COMPOUND_PART.get();
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        if (!this.getBaseRecipe().matches(inv, worldIn)) return false;

        IMaterial first = null;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            MaterialInstance mat = MaterialInstance.from(stack);

            if (mat != null) {
                if (!mat.get().isCraftingAllowed(mat, item.getPartType(), this.getGearType(), inv)) {
                    return false;
                }

                // If classic mixing is disabled, all materials must be the same
                if (first == null) {
                    first = mat.get();
                } else if (!Config.Common.allowClassicMaterialMixing.get() && first != mat.get()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        int craftedCount = getBaseRecipe().getRecipeOutput().getCount();
        return item.create(getMaterials(inv), craftedCount);
    }

    private static MaterialList getMaterials(IInventory inv) {
        MaterialList ret = MaterialList.empty();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                MaterialInstance material = MaterialInstance.from(stack.copy().split(1));
                if (material != null) {
                    ret.add(material);
                }
            }
        }

        return ret;
    }

    @Override
    public ItemStack getRecipeOutput() {
        // Create an example item, so we're not just showing a broken item
        int craftedCount = getBaseRecipe().getRecipeOutput().getCount();
        return item.create(MaterialList.of(LazyMaterialInstance.of(Const.Materials.EXAMPLE)), craftedCount);
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}

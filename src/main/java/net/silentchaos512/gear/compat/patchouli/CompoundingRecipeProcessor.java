package net.silentchaos512.gear.compat.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gear.crafting.recipe.compounder.CompoundingRecipe;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class CompoundingRecipeProcessor implements IComponentProcessor {
    private CompoundingRecipe recipe;

    @Override
    public void setup(IVariableProvider variables) {
        ResourceLocation recipeId = new ResourceLocation(variables.get("recipe").asString());
        ClientWorld world = Minecraft.getInstance().world;
        assert world != null;
        this.recipe = (CompoundingRecipe) world.getRecipeManager().getRecipe(recipeId).orElse(null);
    }

    @Override
    public IVariable process(String key) {
        if (key.startsWith("item")) {
            int index = Integer.parseInt(key.substring(4)) - 1;
            Ingredient ingredient = recipe.getIngredients().get(index);
            ItemStack[] stacks = ingredient.getMatchingStacks();
            ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];
            return IVariable.from(stack);
        } else if ("text".equals(key)) {
            ItemStack out = recipe.getRecipeOutput();
            return IVariable.wrap(out.getCount() + "x$(br)" + out.getDisplayName());
        } else if ("icount".equals(key)) {
            return IVariable.wrap(recipe.getRecipeOutput().getCount());
        } else if ("iname".equals(key)) {
            return IVariable.wrap(recipe.getRecipeOutput().getDisplayName().getString());
        }

        return null;
    }
}

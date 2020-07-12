package net.silentchaos512.gear.data.recipes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.crafting.recipe.ConversionRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapedGearRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessCompoundPartRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessGearRecipe;
import net.silentchaos512.gear.gear.material.LazyMaterialInstance;
import net.silentchaos512.gear.init.ModBlocks;
import net.silentchaos512.gear.init.ModItems;
import net.silentchaos512.gear.init.ModTags;
import net.silentchaos512.gear.init.Registration;
import net.silentchaos512.gear.item.CraftingItems;
import net.silentchaos512.lib.data.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Silent Gear - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        metals(consumer, 1.0f, new Metals("blaze_gold", CraftingItems.BLAZE_GOLD_INGOT, ModTags.Items.INGOTS_BLAZE_GOLD)
                .block(ModBlocks.BLAZE_GOLD_BLOCK, ModTags.Items.STORAGE_BLOCKS_BLAZE_GOLD)
                .dust(CraftingItems.BLAZE_GOLD_DUST, ModTags.Items.DUSTS_BLAZE_GOLD)
                .nugget(CraftingItems.BLAZE_GOLD_NUGGET, ModTags.Items.NUGGETS_BLAZE_GOLD));
        metals(consumer, 1.0f, new Metals("crimson_iron", CraftingItems.CRIMSON_IRON_INGOT, ModTags.Items.INGOTS_CRIMSON_IRON)
                .block(ModBlocks.CRIMSON_IRON_BLOCK, ModTags.Items.STORAGE_BLOCKS_CRIMSON_IRON)
                .dust(CraftingItems.CRIMSON_IRON_DUST, ModTags.Items.DUSTS_CRIMSON_IRON)
                .ore(ModBlocks.CRIMSON_IRON_ORE, ModTags.Items.ORES_CRIMSON_IRON)
                .nugget(CraftingItems.CRIMSON_IRON_NUGGET, ModTags.Items.NUGGETS_CRIMSON_IRON));
        metals(consumer, 1.0f, new Metals("crimson_steel", CraftingItems.CRIMSON_STEEL_INGOT, ModTags.Items.INGOTS_CRIMSON_STEEL)
                .block(ModBlocks.CRIMSON_STEEL_BLOCK, ModTags.Items.STORAGE_BLOCKS_CRIMSON_STEEL)
                .nugget(CraftingItems.CRIMSON_STEEL_NUGGET, ModTags.Items.NUGGETS_CRIMSON_STEEL));

        //region Blueprints and Templates
        toolBlueprint(consumer, "sword", ModItems.SWORD_BLUEPRINT, ModItems.SWORD_TEMPLATE, "#", "#", "/");
        toolBlueprint(consumer, "dagger", ModItems.DAGGER_BLUEPRINT, ModItems.DAGGER_TEMPLATE, "#", "/");
        toolBlueprint(consumer, "katana", ModItems.KATANA_BLUEPRINT, ModItems.KATANA_TEMPLATE, "##", "# ", "/ ");
        toolBlueprint(consumer, "machete", ModItems.MACHETE_BLUEPRINT, ModItems.MACHETE_TEMPLATE, "  #", " ##", "/  ");
        toolBlueprint(consumer, "spear", ModItems.SPEAR_BLUEPRINT, ModItems.SPEAR_TEMPLATE, "#  ", " / ", "  /");
        toolBlueprint(consumer, "pickaxe", ModItems.PICKAXE_BLUEPRINT, ModItems.PICKAXE_TEMPLATE, "###", " / ", " / ");
        toolBlueprint(consumer, "shovel", ModItems.SHOVEL_BLUEPRINT, ModItems.SHOVEL_TEMPLATE, "#", "/", "/");
        toolBlueprint(consumer, "axe", ModItems.AXE_BLUEPRINT, ModItems.AXE_TEMPLATE, "##", "#/", " /");
        toolBlueprint(consumer, "paxel", ModItems.PAXEL_BLUEPRINT, ModItems.PAXEL_TEMPLATE, "###", "#/#", " /#");
        toolBlueprint(consumer, "hammer", ModItems.HAMMER_BLUEPRINT, ModItems.HAMMER_TEMPLATE, "###", "###", " / ");
        toolBlueprint(consumer, "excavator", ModItems.EXCAVATOR_BLUEPRINT, ModItems.EXCAVATOR_TEMPLATE, "# #", "###", " / ");
        toolBlueprint(consumer, "lumber_axe", ModItems.LUMBER_AXE_BLUEPRINT, ModItems.LUMBER_AXE_TEMPLATE, "###", "##/", "  /");
        toolBlueprint(consumer, "mattock", ModItems.MATTOCK_BLUEPRINT, ModItems.MATTOCK_TEMPLATE, "## ", "#/#", " / ");
        toolBlueprint(consumer, "sickle", ModItems.SICKLE_BLUEPRINT, ModItems.SICKLE_TEMPLATE, " #", "##", "/ ");
        toolBlueprint(consumer, "shears", ModItems.SHEARS_BLUEPRINT, ModItems.SHEARS_TEMPLATE, " #", "#/");
        toolBlueprint(consumer, "bow", ModItems.BOW_BLUEPRINT, ModItems.BOW_TEMPLATE, " #/", "# /", " #/");
        toolBlueprint(consumer, "crossbow", ModItems.CROSSBOW_BLUEPRINT, ModItems.CROSSBOW_TEMPLATE, "/#/", "###", " / ");
        toolBlueprint(consumer, "slingshot", ModItems.SLINGSHOT_BLUEPRINT, ModItems.SLINGSHOT_TEMPLATE, "# #", " / ", " / ");
        toolBlueprint(consumer, "shield", ModItems.SHIELD_BLUEPRINT, ModItems.SHIELD_TEMPLATE, "# #", "///", " # ");
        armorBlueprint(consumer, "helmet", ModItems.HELMET_BLUEPRINT, ModItems.HELMET_TEMPLATE, "###", "# #");
        armorBlueprint(consumer, "chestplate", ModItems.CHESTPLATE_BLUEPRINT, ModItems.CHESTPLATE_TEMPLATE, "# #", "###", "###");
        armorBlueprint(consumer, "leggings", ModItems.LEGGINGS_BLUEPRINT, ModItems.LEGGINGS_TEMPLATE, "###", "# #", "# #");
        armorBlueprint(consumer, "boots", ModItems.BOOTS_BLUEPRINT, ModItems.BOOTS_TEMPLATE, "# #", "# #");
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.BINDING_BLUEPRINT)
                .setGroup("silentgear:blueprints/binding")
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER_BLUEPRINT), 1)
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING, GearType.TOOL), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.BINDING_TEMPLATE)
                .setGroup("silentgear:blueprints/binding")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 1)
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING, GearType.TOOL), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BOWSTRING_BLUEPRINT)
                .setGroup("silentgear:blueprints/bowstring")
                .key('#', ModTags.Items.PAPER_BLUEPRINT)
                .key('/', PartMaterialIngredient.of(PartType.BOWSTRING, GearType.TOOL))
                .patternLine("#/")
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BOWSTRING_TEMPLATE)
                .setGroup("silentgear:blueprints/bowstring")
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', PartMaterialIngredient.of(PartType.BOWSTRING, GearType.TOOL))
                .patternLine("#/")
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.FLETCHING_BLUEPRINT)
                .setGroup("silentgear:blueprints/fletching")
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER_BLUEPRINT), 2)
                .addIngredient(Tags.Items.FEATHERS)
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.FLETCHING_TEMPLATE)
                .setGroup("silentgear:blueprints/fletching")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 2)
                .addIngredient(Tags.Items.FEATHERS)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.GRIP_BLUEPRINT)
                .setGroup("silentgear:blueprints/grip")
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER_BLUEPRINT), 2)
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP, GearType.TOOL))
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.GRIP_TEMPLATE)
                .setGroup("silentgear:blueprints/grip")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 2)
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP, GearType.TOOL))
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ROD_BLUEPRINT)
                .setGroup("silentgear:blueprints/rod")
                .key('#', ModTags.Items.PAPER_BLUEPRINT)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ROD_TEMPLATE)
                .setGroup("silentgear:blueprints/rod")
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.TIP_BLUEPRINT)
                .setGroup("silentgear:blueprints/tip")
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER_BLUEPRINT), 2)
                .addIngredient(ModTags.Items.PAPER)
                .addIngredient(Tags.Items.STONE)
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.TIP_TEMPLATE)
                .setGroup("silentgear:blueprints/tip")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 2)
                .addIngredient(ModTags.Items.PAPER)
                .addIngredient(Tags.Items.STONE)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        //endregion

        //region Compound Parts
        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.ROD, 4)
                .addIngredient(ModItems.ROD_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.ROD), 2)
                .build(consumer, SilentGear.getId("part/rod"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.LONG_ROD, 2)
                .addIngredient(ModItems.ROD_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.ROD), 3)
                .build(consumer, SilentGear.getId("part/long_rod"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.BINDING, 1)
                .addIngredient(ModItems.BINDING_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING))
                .build(consumer, SilentGear.getId("part/binding"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.BINDING, 2)
                .addIngredient(ModItems.BINDING_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING), 2)
                .build(consumer, SilentGear.getId("part/binding2"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.BOWSTRING, 1)
                .addIngredient(ModItems.BOWSTRING_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.BOWSTRING), 3)
                .build(consumer, SilentGear.getId("part/bowstring"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.FLETCHING, 1)
                .addIngredient(ModItems.FLETCHING_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.FLETCHING), 1)
                .build(consumer, SilentGear.getId("part/fletching"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.GRIP, 1)
                .addIngredient(ModItems.GRIP_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP))
                .build(consumer, SilentGear.getId("part/grip"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.GRIP, 2)
                .addIngredient(ModItems.GRIP_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP), 2)
                .build(consumer, SilentGear.getId("part/grip2"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.TIP, 1)
                .addIngredient(ModItems.TIP_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.TIP))
                .build(consumer, SilentGear.getId("part/tip"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.TIP, 2)
                .addIngredient(ModItems.TIP_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.TIP), 2)
                .build(consumer, SilentGear.getId("part/tip2"));
        //endregion

        //region Gear items
        toolRecipes(consumer, "sword", 2, ModItems.SWORD, ModItems.SWORD_BLADE, ModItems.SWORD_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "dagger", 1, ModItems.DAGGER, ModItems.DAGGER_BLADE, ModItems.DAGGER_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "katana", 3, ModItems.KATANA, ModItems.KATANA_BLADE, ModItems.KATANA_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "machete", 3, ModItems.MACHETE, ModItems.MACHETE_BLADE, ModItems.MACHETE_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "spear", 1, ModItems.SPEAR, ModItems.SPEAR_TIP, ModItems.SPEAR_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "pickaxe", 3, ModItems.PICKAXE, ModItems.PICKAXE_HEAD, ModItems.PICKAXE_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "shovel", 1, ModItems.SHOVEL, ModItems.SHOVEL_HEAD, ModItems.SHOVEL_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "axe", 3, ModItems.AXE, ModItems.AXE_HEAD, ModItems.AXE_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "paxel", 5, ModItems.PAXEL, ModItems.PAXEL_HEAD, ModItems.PAXEL_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "hammer", 6, ModItems.HAMMER, ModItems.HAMMER_HEAD, ModItems.HAMMER_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "excavator", 5, ModItems.EXCAVATOR, ModItems.EXCAVATOR_HEAD, ModItems.EXCAVATOR_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "lumber_axe", 5, ModItems.LUMBER_AXE, ModItems.LUMBER_AXE_HEAD, ModItems.LUMBER_AXE_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "mattock", 4, ModItems.MATTOCK, ModItems.MATTOCK_HEAD, ModItems.MATTOCK_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "sickle", 3, ModItems.SICKLE, ModItems.SICKLE_BLADE, ModItems.SICKLE_BLUEPRINT.get().getItemTag());
        toolRecipes(consumer, "shears", 2, ModItems.SHEARS, ModItems.SHEARS_BLADES, ModItems.SHEARS_BLUEPRINT.get().getItemTag());
        bowRecipes(consumer, "bow", 3, ModItems.BOW, ModItems.BOW_LIMBS, ModItems.BOW_BLUEPRINT.get().getItemTag());
        bowRecipes(consumer, "crossbow", 3, ModItems.CROSSBOW, ModItems.CROSSBOW_LIMBS, ModItems.CROSSBOW_BLUEPRINT.get().getItemTag());
        bowRecipes(consumer, "slingshot", 2, ModItems.SLINGSHOT, ModItems.SLINGSHOT_LIMBS, ModItems.SLINGSHOT_BLUEPRINT.get().getItemTag());

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.SHIELD)
                .addIngredient(ModItems.SHIELD_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 2)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/shield"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.HELMET)
                .addIngredient(ModItems.HELMET_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 5)
                .build(consumer, SilentGear.getId("gear/helmet"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.CHESTPLATE)
                .addIngredient(ModItems.CHESTPLATE_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 8)
                .build(consumer, SilentGear.getId("gear/chestplate"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.LEGGINGS)
                .addIngredient(ModItems.LEGGINGS_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 7)
                .build(consumer, SilentGear.getId("gear/leggings"));

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.BOOTS)
                .addIngredient(ModItems.BOOTS_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 4)
                .build(consumer, SilentGear.getId("gear/boots"));

        // Rough recipes
        ExtendedShapedRecipeBuilder.builder(ShapedGearRecipe.SERIALIZER, ModItems.SWORD)
                .patternLine("#")
                .patternLine("#")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/sword"));
        ExtendedShapedRecipeBuilder.builder(ShapedGearRecipe.SERIALIZER, ModItems.DAGGER)
                .patternLine("#")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/dagger"));
        ExtendedShapedRecipeBuilder.builder(ShapedGearRecipe.SERIALIZER, ModItems.PICKAXE)
                .patternLine("###")
                .patternLine(" / ")
                .patternLine(" / ")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/pickaxe"));
        ExtendedShapedRecipeBuilder.builder(ShapedGearRecipe.SERIALIZER, ModItems.SHOVEL)
                .patternLine("#")
                .patternLine("/")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/shovel"));
        ExtendedShapedRecipeBuilder.builder(ShapedGearRecipe.SERIALIZER, ModItems.AXE)
                .patternLine("##")
                .patternLine("#/")
                .patternLine(" /")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/axe"));

        // Coonversion recipes
        toolConversion(consumer, ModItems.SWORD, Items.DIAMOND_SWORD, Items.GOLDEN_SWORD, Items.IRON_SWORD, Items.STONE_SWORD, Items.WOODEN_SWORD);
        toolConversion(consumer, ModItems.PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.STONE_PICKAXE, Items.WOODEN_PICKAXE);
        toolConversion(consumer, ModItems.SHOVEL, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.IRON_SHOVEL, Items.STONE_SHOVEL, Items.WOODEN_SHOVEL);
        toolConversion(consumer, ModItems.AXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE, Items.IRON_AXE, Items.STONE_AXE, Items.WOODEN_AXE);
        armorConversion(consumer, ModItems.HELMET, Items.DIAMOND_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.LEATHER_HELMET);
        armorConversion(consumer, ModItems.CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.LEATHER_CHESTPLATE);
        armorConversion(consumer, ModItems.LEGGINGS, Items.DIAMOND_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.LEATHER_LEGGINGS);
        armorConversion(consumer, ModItems.BOOTS, Items.DIAMOND_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.LEATHER_BOOTS);
        //endregion

        // Repair Kits
        ShapedRecipeBuilder.shapedRecipe(ModItems.CRUDE_REPAIR_KIT)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('o', Tags.Items.INGOTS_IRON)
                .patternLine(" / ")
                .patternLine("#o#")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.STURDY_REPAIR_KIT)
                .key('#', Tags.Items.INGOTS_IRON)
                .key('/', ModTags.Items.RODS_IRON)
                .key('o', Tags.Items.GEMS_DIAMOND)
                .patternLine(" / ")
                .patternLine("#o#")
                .patternLine("###")
                .addCriterion("has_item", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.CRIMSON_REPAIR_KIT)
                .key('#', ModTags.Items.INGOTS_CRIMSON_STEEL)
                .key('/', Tags.Items.RODS_BLAZE)
                .key('o', ModTags.Items.INGOTS_BLAZE_GOLD)
                .patternLine(" / ")
                .patternLine("#o#")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_CRIMSON_STEEL))
                .build(consumer);

        //region Sort me, plz
        // TODO: Maybe should organize these better...
        // A
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.ADVANCED_UPGRADE_BASE)
                .key('/', ModTags.Items.NUGGETS_DIAMOND)
                .key('D', Tags.Items.DYES_BLUE)
                .key('U', CraftingItems.UPGRADE_BASE)
                .key('G', Tags.Items.NUGGETS_GOLD)
                .patternLine("///")
                .patternLine("DUD")
                .patternLine("GGG")
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);
        // B
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.BLAZE_GOLD_INGOT)
                .addIngredient(Tags.Items.INGOTS_GOLD)
                .addIngredient(Items.BLAZE_POWDER, 4)
                .addCriterion("has_item", hasItem(Items.BLAZE_POWDER))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.BLAZING_DUST, 4)
                .addIngredient(ModTags.Items.DUSTS_BLAZE_GOLD)
                .addIngredient(Ingredient.fromTag(Tags.Items.DUSTS_GLOWSTONE), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.DUSTS_BLAZE_GOLD))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.BLUEPRINT_PAPER, 4)
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER), 4)
                .addIngredient(Tags.Items.DYES_BLUE)
                .addCriterion("has_paper", hasItem(ModTags.Items.PAPER))
                .build(consumer);
        // C
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.COBBLESTONE)
                .addIngredient(ModItems.PEBBLE, 9)
                .addCriterion("has_pebble", hasItem(ModItems.PEBBLE))
                .build(consumer, SilentGear.getId("cobblestone_from_pebbles"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.CRAFTING_STATION)
                .key('#', ItemTags.PLANKS)
                .key('C', Tags.Items.CHESTS_WOODEN)
                .key('T', Blocks.CRAFTING_TABLE)
                .key('U', CraftingItems.UPGRADE_BASE)
                .patternLine("#T#")
                .patternLine("#U#")
                .patternLine("#C#")
                .addCriterion("has_item", hasItem(Blocks.CRAFTING_TABLE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.CRIMSON_STEEL_INGOT)
                .key('/', Tags.Items.RODS_BLAZE)
                .key('#', ModTags.Items.INGOTS_CRIMSON_IRON)
                .key('C', Items.MAGMA_CREAM)
                .patternLine("/ /")
                .patternLine("#C#")
                .patternLine("# #")
                .addCriterion("has_item", hasItem(CraftingItems.CRIMSON_IRON_INGOT))
                .build(consumer);
        // D
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.DIAMOND_SHARD, 9)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND)
                .key('#', ModTags.Items.NUGGETS_DIAMOND)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer, SilentGear.getId("diamond_from_shards"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.DIAMOND_TIPPED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(CraftingItems.SINEW), CraftingItems.DRIED_SINEW, 0.35f, 200)
                .addCriterion("has_item", hasItem(CraftingItems.SINEW))
                .build(consumer);
        // E
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.EMERALD_SHARD, 9)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.EMERALD)
                .key('#', ModTags.Items.NUGGETS_EMERALD)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_EMERALD))
                .build(consumer, SilentGear.getId("emerald_from_shards"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.EMERALD_TIPPED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_EMERALD))
                .build(consumer);
        // F
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.FLAX_BOWSTRING)
                .key('/', CraftingItems.FLAX_STRING)
                .patternLine("/")
                .patternLine("/")
                .patternLine("/")
                .addCriterion("has_item", hasItem(CraftingItems.FLAX_FIBER))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.FLAX_BOWSTRING)
                .addIngredient(ModTags.Items.BLUEPRINTS_BOWSTRING)
                .addIngredient(CraftingItems.FLAX_STRING, 3)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_BOWSTRING))
                .build(consumer, SilentGear.getId("flax_bowstring2"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.FLAX_STRING)
                .addIngredient(CraftingItems.FLAX_FIBER, 2)
                .addCriterion("has_item", hasItem(CraftingItems.FLAX_FIBER))
                .build(consumer);
        // G
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.GLITTERY_DUST, 8)
                .key('o', Items.POPPED_CHORUS_FRUIT)
                .key('/', ModTags.Items.NUGGETS_EMERALD)
                .key('#', Tags.Items.DUSTS_GLOWSTONE)
                .key('b', ModItems.NETHER_BANANA)
                .patternLine("o/o")
                .patternLine("#b#")
                .patternLine("o/o")
                .addCriterion("has_item", hasItem(ModItems.NETHER_BANANA))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.GLOWSTONE_COATED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Ingredient.fromTag(Tags.Items.DUSTS_GLOWSTONE), 4)
                .addCriterion("has_item", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.GOLD_TIPPED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.INGOTS_GOLD)
                .addCriterion("has_item", hasItem(Tags.Items.INGOTS_GOLD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.GOLDEN_NETHER_BANANA)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('b', ModItems.NETHER_BANANA)
                .patternLine("ggg")
                .patternLine("gbg")
                .patternLine("ggg")
                .addCriterion("has_item", hasItem(ModItems.NETHER_BANANA))
                .build(consumer);
        // I
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.IRON_ROD, 4)
                .key('/', Tags.Items.INGOTS_IRON)
                .patternLine("/")
                .patternLine("/")
                .addCriterion("has_item", hasItem(Items.IRON_INGOT))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.IRON_ROD, 2)
                .addIngredient(ModTags.Items.BLUEPRINTS_ROD)
                .addIngredient(Ingredient.fromTag(Tags.Items.INGOTS_IRON), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_ROD))
                .build(consumer, SilentGear.getId("iron_rod2"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.IRON_TIPPED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.INGOTS_IRON)
                .addCriterion("has_item", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer);
        // L
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.LAPIS_COATED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Ingredient.fromTag(Tags.Items.GEMS_LAPIS), 4)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_LAPIS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER)
                .key('#', CraftingItems.LEATHER_SCRAP)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(CraftingItems.LEATHER_SCRAP))
                .build(consumer, SilentGear.getId("leather_from_scraps"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.LEATHER_SCRAP, 9)
                .addIngredient(Items.LEATHER)
                .addCriterion("has_item", hasItem(CraftingItems.LEATHER_SCRAP))
                .build(consumer);
        // M
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.MATERIAL_GRADER)
                .key('Q', Tags.Items.GEMS_QUARTZ)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('#', CraftingItems.ADVANCED_UPGRADE_BASE)
                .key('G', ModTags.Items.INGOTS_BLAZE_GOLD)
                .patternLine("QIQ")
                .patternLine("I#I")
                .patternLine("GGG")
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_BLAZE_GOLD))
                .build(consumer);
        // N
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_DOOR, 3)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_PLANKS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_TRAPDOOR, 2)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_PLANKS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_FENCE, 3)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine("#/#")
                .patternLine("#/#")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_PLANKS))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.NETHERWOOD_PLANKS, 4)
                .addIngredient(ModBlocks.NETHERWOOD_LOG)
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_PLANKS)
                .key('#', ModBlocks.NETHERWOOD_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer, SilentGear.getId("netherwood_planks_from_slabs"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_PLANKS, 3)
                .key('#', ModBlocks.NETHERWOOD_STAIRS)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer, SilentGear.getId("netherwood_planks_from_stairs"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_SLAB, 6)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_STAIRS, 4)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.NETHERWOOD_STICK, 4)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .patternLine(" #")
                .patternLine("# ")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.NETHERWOOD_STICK, 4)
                .addIngredient(ModTags.Items.BLUEPRINTS_ROD)
                .addIngredient(ModBlocks.NETHERWOOD_PLANKS, 2)
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_LOG))
                .build(consumer, SilentGear.getId("netherwood_stick2"));
        // P
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.PLAIN_BOWSTRING)
                .key('/', Items.STRING)
                .patternLine("/")
                .patternLine("/")
                .patternLine("/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_BOWSTRING))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.PLAIN_BOWSTRING)
                .addIngredient(ModTags.Items.BLUEPRINTS_BOWSTRING)
                .addIngredient(Items.STRING, 3)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_BOWSTRING))
                .build(consumer, SilentGear.getId("plain_bowstring2"));
        // Q
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.QUARTZ_TIPPED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Ingredient.fromTag(Tags.Items.GEMS_QUARTZ), 4)
                .addCriterion("has_item", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(consumer);
        // R
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.RED_CARD_UPGRADE, 4)
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.DYES_RED)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.REDSTONE_COATED_UPGRADE)
                .setGroup("silentgear:tip_upgrades")
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Ingredient.fromTag(Tags.Items.DUSTS_REDSTONE), 4)
                .addCriterion("has_item", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.ROUGH_ROD, 2)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine(" /")
                .patternLine("/ ")
                .addCriterion("has_item", hasItem(Items.STICK))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.ROUGH_ROD, 2)
                .addIngredient(ModTags.Items.BLUEPRINTS_ROD)
                .addIngredient(Ingredient.fromTag(Tags.Items.RODS_WOODEN), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_ROD))
                .build(consumer, SilentGear.getId("rough_rod2"));
        // S
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.SALVAGER)
                .key('P', Blocks.PISTON)
                .key('/', ModTags.Items.INGOTS_CRIMSON_IRON)
                .key('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('#', Tags.Items.OBSIDIAN)
                .patternLine(" P ")
                .patternLine("/I/")
                .patternLine("/#/")
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_CRIMSON_IRON))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.SINEW_BOWSTRING)
                .key('/', CraftingItems.SINEW_FIBER)
                .patternLine("/")
                .patternLine("/")
                .patternLine("/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_BOWSTRING))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.SINEW_BOWSTRING)
                .addIngredient(ModTags.Items.BLUEPRINTS_BOWSTRING)
                .addIngredient(CraftingItems.SINEW_FIBER, 3)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINTS_BOWSTRING))
                .build(consumer, SilentGear.getId("sinew_bowstring2"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.SINEW_FIBER, 3)
                .addIngredient(CraftingItems.DRIED_SINEW)
                .addCriterion("has_item", hasItem(CraftingItems.SINEW))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.SPOON_UPGRADE)
                .addIngredient(CraftingItems.ADVANCED_UPGRADE_BASE)
                .addIngredient(Items.DIAMOND_SHOVEL)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.STONE_ROD, 4)
                .key('#', Tags.Items.COBBLESTONE)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_item", hasItem(Tags.Items.COBBLESTONE))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.STONE_ROD, 4)
                .addIngredient(ModTags.Items.BLUEPRINTS_ROD)
                .addIngredient(Ingredient.fromTag(Tags.Items.COBBLESTONE), 2)
                .addCriterion("has_item", hasItem(Tags.Items.COBBLESTONE))
                .build(consumer, SilentGear.getId("stone_rod2"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.STONE_TORCH, 4)
                .key('#', ItemTags.COALS)
                .key('/', ModTags.Items.RODS_STONE)
                .patternLine("#")
                .patternLine("/")
                .addCriterion("has_item", hasItem(ItemTags.COALS))
                .build(consumer);
        // U
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.UPGRADE_BASE, 4)
                .addIngredient(Ingredient.fromTag(ModTags.Items.PAPER), 2)
                .addIngredient(ItemTags.PLANKS)
                .addIngredient(Tags.Items.STONE)
                .addCriterion("has_item", hasItem(ItemTags.PLANKS))
                .build(consumer);
        //endregion

        //region Salvaging

        Registration.getItems(item -> item instanceof ICoreItem).forEach(item ->
                gearSalvage(consumer, (ICoreItem) item));

        vanillaSalvage(consumer, Items.DIAMOND_SWORD, Items.DIAMOND, 2, 1);
        vanillaSalvage(consumer, Items.DIAMOND_PICKAXE, Items.DIAMOND, 3, 2);
        vanillaSalvage(consumer, Items.DIAMOND_SHOVEL, Items.DIAMOND, 1, 2);
        vanillaSalvage(consumer, Items.DIAMOND_AXE, Items.DIAMOND, 3, 2);
        vanillaSalvage(consumer, Items.DIAMOND_HOE, Items.DIAMOND, 2, 2);
        vanillaSalvage(consumer, Items.DIAMOND_HELMET, Items.DIAMOND, 5, 0);
        vanillaSalvage(consumer, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 8, 0);
        vanillaSalvage(consumer, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 7, 0);
        vanillaSalvage(consumer, Items.DIAMOND_BOOTS, Items.DIAMOND, 4, 0);

        vanillaSalvage(consumer, Items.GOLDEN_SWORD, Items.GOLD_INGOT, 2, 1);
        vanillaSalvage(consumer, Items.GOLDEN_PICKAXE, Items.GOLD_INGOT, 3, 2);
        vanillaSalvage(consumer, Items.GOLDEN_SHOVEL, Items.GOLD_INGOT, 1, 2);
        vanillaSalvage(consumer, Items.GOLDEN_AXE, Items.GOLD_INGOT, 3, 2);
        vanillaSalvage(consumer, Items.GOLDEN_HOE, Items.GOLD_INGOT, 2, 2);
        vanillaSalvage(consumer, Items.GOLDEN_HELMET, Items.GOLD_INGOT, 5, 0);
        vanillaSalvage(consumer, Items.GOLDEN_CHESTPLATE, Items.GOLD_INGOT, 8, 0);
        vanillaSalvage(consumer, Items.GOLDEN_LEGGINGS, Items.GOLD_INGOT, 7, 0);
        vanillaSalvage(consumer, Items.GOLDEN_BOOTS, Items.GOLD_INGOT, 4, 0);

        vanillaSalvage(consumer, Items.IRON_SWORD, Items.IRON_INGOT, 2, 1);
        vanillaSalvage(consumer, Items.IRON_PICKAXE, Items.IRON_INGOT, 3, 2);
        vanillaSalvage(consumer, Items.IRON_SHOVEL, Items.IRON_INGOT, 1, 2);
        vanillaSalvage(consumer, Items.IRON_AXE, Items.IRON_INGOT, 3, 2);
        vanillaSalvage(consumer, Items.IRON_HOE, Items.IRON_INGOT, 2, 2);
        vanillaSalvage(consumer, Items.IRON_HELMET, Items.IRON_INGOT, 5, 0);
        vanillaSalvage(consumer, Items.IRON_CHESTPLATE, Items.IRON_INGOT, 8, 0);
        vanillaSalvage(consumer, Items.IRON_LEGGINGS, Items.IRON_INGOT, 7, 0);
        vanillaSalvage(consumer, Items.IRON_BOOTS, Items.IRON_INGOT, 4, 0);

        vanillaSalvage(consumer, Items.LEATHER_HELMET, Items.LEATHER, 5, 0);
        vanillaSalvage(consumer, Items.LEATHER_CHESTPLATE, Items.LEATHER, 8, 0);
        vanillaSalvage(consumer, Items.LEATHER_LEGGINGS, Items.LEATHER, 7, 0);
        vanillaSalvage(consumer, Items.LEATHER_BOOTS, Items.LEATHER, 4, 0);

        vanillaSalvage(consumer, Items.STONE_SWORD, Items.COBBLESTONE, 2, 1);
        vanillaSalvage(consumer, Items.STONE_PICKAXE, Items.COBBLESTONE, 3, 2);
        vanillaSalvage(consumer, Items.STONE_SHOVEL, Items.COBBLESTONE, 1, 2);
        vanillaSalvage(consumer, Items.STONE_AXE, Items.COBBLESTONE, 3, 2);
        vanillaSalvage(consumer, Items.STONE_HOE, Items.COBBLESTONE, 2, 2);

        //endregion
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    private static void toolRecipes(Consumer<IFinishedRecipe> consumer, String name, int mainCount, IItemProvider tool, IItemProvider toolHead, Tag<Item> blueprintTag) {
        // Tool head
        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, toolHead)
                .addIngredient(blueprintTag)
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .build(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Tool from head and rod
        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, tool)
                .addIngredient(toolHead)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/" + name));
        // Quick tool (mains and rods, skipping head)
        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, tool)
                .addIngredient(blueprintTag)
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    private static void bowRecipes(Consumer<IFinishedRecipe> consumer, String name, int mainCount, IItemProvider tool, IItemProvider toolHead, Tag<Item> blueprintTag) {
        // Tool head
        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, toolHead)
                .addIngredient(blueprintTag)
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .build(consumer, SilentGear.getId("gear/" + name + "_limbs"));
        // Bow from limbs, rod, and bowstring
        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, tool)
                .addIngredient(toolHead)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.BOWSTRING))
                .build(consumer, SilentGear.getId("gear/" + name));
        // Quick tool (mains, rod, and bowstring, skipping limbs)
        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, tool)
                .addIngredient(blueprintTag)
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.BOWSTRING))
                .build(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private void toolBlueprint(Consumer<IFinishedRecipe> consumer, String group, IItemProvider blueprint, IItemProvider template, String... pattern) {
        ShapedRecipeBuilder builderBlueprint = ShapedRecipeBuilder.shapedRecipe(blueprint)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.PAPER_BLUEPRINT)
                .key('/', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT));
        for (String line : pattern) {
            builderBlueprint.patternLine(line);
        }
        builderBlueprint.build(consumer);

        ShapedRecipeBuilder builderTemplate = ShapedRecipeBuilder.shapedRecipe(template)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS));
        for (String line : pattern) {
            builderTemplate.patternLine(line);
        }
        builderTemplate.build(consumer);
    }

    private void armorBlueprint(Consumer<IFinishedRecipe> consumer, String group, IItemProvider blueprint, IItemProvider template, String... pattern) {
        ShapedRecipeBuilder builderBlueprint = ShapedRecipeBuilder.shapedRecipe(blueprint)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.PAPER_BLUEPRINT)
                .addCriterion("has_item", hasItem(ModTags.Items.PAPER_BLUEPRINT));
        for (String line : pattern) {
            builderBlueprint.patternLine(line);
        }
        builderBlueprint.build(consumer);

        ShapedRecipeBuilder builderTemplate = ShapedRecipeBuilder.shapedRecipe(template)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS));
        for (String line : pattern) {
            builderTemplate.patternLine(line);
        }
        builderTemplate.build(consumer);
    }

    private static final Map<IItemTier, ResourceLocation> TOOL_MATERIALS = ImmutableMap.<IItemTier, ResourceLocation>builder()
            .put(ItemTier.DIAMOND, SilentGear.getId("diamond"))
            .put(ItemTier.GOLD, SilentGear.getId("gold"))
            .put(ItemTier.IRON, SilentGear.getId("iron"))
            .put(ItemTier.STONE, SilentGear.getId("stone"))
            .put(ItemTier.WOOD, SilentGear.getId("wood"))
            .build();
    private static final Map<IArmorMaterial,  ResourceLocation> ARMOR_MATERIALS = ImmutableMap.<IArmorMaterial, ResourceLocation>builder()
            .put(ArmorMaterial.DIAMOND, SilentGear.getId("diamond"))
            .put(ArmorMaterial.GOLD, SilentGear.getId("gold"))
            .put(ArmorMaterial.IRON, SilentGear.getId("iron"))
            .put(ArmorMaterial.LEATHER, SilentGear.getId("leather"))
            .build();

    private static void toolConversion(Consumer<IFinishedRecipe> consumer, IItemProvider result, Item... toolItems) {
        for (Item input : toolItems) {
            assert input instanceof TieredItem;
            ExtendedShapelessRecipeBuilder.builder(ConversionRecipe.SERIALIZER, result)
                    .addIngredient(input)
                    .addExtraData(json -> {
                        ResourceLocation material = TOOL_MATERIALS.getOrDefault(((TieredItem) input).getTier(), SilentGear.getId("emerald"));
                        json.getAsJsonObject("result").add("materials", buildMaterials(material, SilentGear.getId("wood")));
                    })
                    .build(consumer, SilentGear.getId("gear/convert/" + NameUtils.from(input).getPath()));
        }
    }

    private static void armorConversion(Consumer<IFinishedRecipe> consumer, IItemProvider result, Item... armorItems) {
        for (Item input : armorItems) {
            assert input instanceof ArmorItem;
            ExtendedShapelessRecipeBuilder.builder(ConversionRecipe.SERIALIZER, result)
                    .addIngredient(input)
                    .addExtraData(json -> {
                        ResourceLocation material = ARMOR_MATERIALS.getOrDefault(((ArmorItem) input).getArmorMaterial(), SilentGear.getId("emerald"));
                        json.getAsJsonObject("result").add("materials", buildMaterials(material, null));
                    })
                    .build(consumer, SilentGear.getId("gear/convert/" + NameUtils.from(input).getPath()));
        }
    }

    private static void gearSalvage(Consumer<IFinishedRecipe> consumer, ICoreItem item) {
        SalvagingRecipeBuilder.gearBuilder(item)
                .build(consumer, SilentGear.getId("salvaging/gear/" + NameUtils.fromItem(item).getPath()));
    }

    private static void vanillaSalvage(Consumer<IFinishedRecipe> consumer, IItemProvider gear, IItemProvider main, int mainCount, int rodCount) {
        SalvagingRecipeBuilder builder = SalvagingRecipeBuilder.builder(gear).addResult(main, mainCount);
        if (rodCount > 0) {
            builder.addResult(Items.STICK, rodCount);
        }
        ResourceLocation inputId = NameUtils.from(gear.asItem());
        builder.build(consumer, SilentGear.getId("salvaging/" + inputId.getPath()));
    }

    private static JsonObject buildMaterials(ResourceLocation main, @Nullable ResourceLocation rod) {
        JsonObject json = new JsonObject();
        json.add("main", LazyMaterialInstance.of(main).serialize());
        if (rod != null) {
            json.add("rod", LazyMaterialInstance.of(rod).serialize());
        }
        return json;
    }

    private void damagingItem(Consumer<IFinishedRecipe> consumer, ShapelessRecipeBuilder builder) {
    }

    private void gear(Consumer<IFinishedRecipe> consumer, GearType gearType, int mainCount, PartType... otherParts) {
    }

    private void metals(Consumer<IFinishedRecipe> consumer, float smeltingXp, Metals metal) {
        if (metal.ore != null) {
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(metal.oreTag), metal.ingot, smeltingXp, 100)
                    .addCriterion("has_item", hasItem(metal.oreTag))
                    .build(consumer, SilentGear.getId(metal.name + "_ore_blasting"));
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(metal.oreTag), metal.ingot, smeltingXp, 200)
                    .addCriterion("has_item", hasItem(metal.oreTag))
                    .build(consumer, SilentGear.getId(metal.name + "_ore_smelting"));
        }

        InventoryChangeTrigger.Instance hasIngot = hasItem(metal.ingotTag);

        if (metal.block != null) {
            ShapelessRecipeBuilder.shapelessRecipe(metal.ingot, 9)
                    .addIngredient(metal.blockTag)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, new ResourceLocation(metal.ingot.asItem().getRegistryName() + "_from_block"));
            ShapedRecipeBuilder.shapedRecipe(metal.block)
                    .key('#', metal.ingotTag)
                    .patternLine("###")
                    .patternLine("###")
                    .patternLine("###")
                    .addCriterion("has_item", hasIngot)
                    .build(consumer);
        }
        if (metal.nugget != null) {
            ShapelessRecipeBuilder.shapelessRecipe(metal.nugget, 9)
                    .addIngredient(metal.ingotTag)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer);
            ShapedRecipeBuilder.shapedRecipe(metal.ingot)
                    .key('#', metal.nuggetTag)
                    .patternLine("###")
                    .patternLine("###")
                    .patternLine("###")
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, new ResourceLocation(metal.ingot.asItem().getRegistryName() + "_from_nuggets"));
        }
        if (metal.dustTag != null) {
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(metal.dustTag), metal.ingot, smeltingXp, 100)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, SilentGear.getId(metal.name + "_dust_blasting"));
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(metal.dustTag), metal.ingot, smeltingXp, 200)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, SilentGear.getId(metal.name + "_dust_smelting"));
        }
    }

    @SuppressWarnings("WeakerAccess")
    private static class Metals {
        private final String name;
        private IItemProvider ore;
        private Tag<Item> oreTag;
        private IItemProvider block;
        private Tag<Item> blockTag;
        private final IItemProvider ingot;
        private final Tag<Item> ingotTag;
        private IItemProvider nugget;
        private Tag<Item> nuggetTag;
        private IItemProvider dust;
        private Tag<Item> dustTag;

        public Metals(String name, IItemProvider ingot, Tag<Item> ingotTag) {
            this.name = name;
            this.ingot = ingot;
            this.ingotTag = ingotTag;
        }

        public Metals ore(IItemProvider item, Tag<Item> tag) {
            this.ore = item;
            this.oreTag = tag;
            return this;
        }

        public Metals block(IItemProvider item, Tag<Item> tag) {
            this.block = item;
            this.blockTag = tag;
            return this;
        }

        public Metals nugget(IItemProvider item, Tag<Item> tag) {
            this.nugget = item;
            this.nuggetTag = tag;
            return this;
        }

        public Metals dust(IItemProvider item, Tag<Item> tag) {
            this.dust = item;
            this.dustTag = tag;
            return this;
        }
    }
}
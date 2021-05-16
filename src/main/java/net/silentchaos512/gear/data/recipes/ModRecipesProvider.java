package net.silentchaos512.gear.data.recipes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.crafting.ingredient.BlueprintIngredient;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.gear.material.LazyMaterialInstance;
import net.silentchaos512.gear.gear.material.MaterialCategories;
import net.silentchaos512.gear.init.*;
import net.silentchaos512.gear.item.CraftingItems;
import net.silentchaos512.gear.item.RepairKitItem;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.gear.item.gear.CoreArmor;
import net.silentchaos512.gear.util.Const;
import net.silentchaos512.gear.util.DataResource;
import net.silentchaos512.lib.data.recipe.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.recipe.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.lib.data.recipe.ExtendedSingleItemRecipeBuilder;
import net.silentchaos512.lib.data.recipe.LibRecipeProvider;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ModRecipesProvider extends LibRecipeProvider {
    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn, SilentGear.MOD_ID);
    }

    @Override
    public String getName() {
        return "Silent Gear - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        metals(consumer, 0.5f, new Metals("blaze_gold", CraftingItems.BLAZE_GOLD_INGOT, ModTags.Items.INGOTS_BLAZE_GOLD)
                .block(ModBlocks.BLAZE_GOLD_BLOCK, ModTags.Items.STORAGE_BLOCKS_BLAZE_GOLD)
                .dust(CraftingItems.BLAZE_GOLD_DUST, ModTags.Items.DUSTS_BLAZE_GOLD)
                .nugget(CraftingItems.BLAZE_GOLD_NUGGET, ModTags.Items.NUGGETS_BLAZE_GOLD));
        metals(consumer, 1.0f, new Metals("crimson_iron", CraftingItems.CRIMSON_IRON_INGOT, ModTags.Items.INGOTS_CRIMSON_IRON)
                .block(ModBlocks.CRIMSON_IRON_BLOCK, ModTags.Items.STORAGE_BLOCKS_CRIMSON_IRON)
                .dust(CraftingItems.CRIMSON_IRON_DUST, ModTags.Items.DUSTS_CRIMSON_IRON)
                .chunks(ModTags.Items.CHUNKS_CRIMSON_IRON)
                .ore(ModBlocks.CRIMSON_IRON_ORE, ModTags.Items.ORES_CRIMSON_IRON, CraftingItems.RAW_CRIMSON_IRON, ModBlocks.RAW_CRIMSON_IRON_BLOCK)
                .nugget(CraftingItems.CRIMSON_IRON_NUGGET, ModTags.Items.NUGGETS_CRIMSON_IRON));
        metals(consumer, 0.5f, new Metals("crimson_steel", CraftingItems.CRIMSON_STEEL_INGOT, ModTags.Items.INGOTS_CRIMSON_STEEL)
                .block(ModBlocks.CRIMSON_STEEL_BLOCK, ModTags.Items.STORAGE_BLOCKS_CRIMSON_STEEL)
                .dust(CraftingItems.CRIMSON_STEEL_DUST, ModTags.Items.DUSTS_CRIMSON_STEEL)
                .nugget(CraftingItems.CRIMSON_STEEL_NUGGET, ModTags.Items.NUGGETS_CRIMSON_STEEL));
        metals(consumer, 1.5f, new Metals("azure_silver", CraftingItems.AZURE_SILVER_INGOT, ModTags.Items.INGOTS_AZURE_SILVER)
                .block(ModBlocks.AZURE_SILVER_BLOCK, ModTags.Items.STORAGE_BLOCKS_AZURE_SILVER)
                .dust(CraftingItems.AZURE_SILVER_DUST, ModTags.Items.DUSTS_AZURE_SILVER)
                .chunks(ModTags.Items.CHUNKS_AZURE_SILVER)
                .ore(ModBlocks.AZURE_SILVER_ORE, ModTags.Items.ORES_AZURE_SILVER, CraftingItems.RAW_AZURE_SILVER, ModBlocks.RAW_AZURE_SILVER_BLOCK)
                .nugget(CraftingItems.AZURE_SILVER_NUGGET, ModTags.Items.NUGGETS_AZURE_SILVER));
        metals(consumer, 0.5f, new Metals("azure_electrum", CraftingItems.AZURE_ELECTRUM_INGOT, ModTags.Items.INGOTS_AZURE_ELECTRUM)
                .block(ModBlocks.AZURE_ELECTRUM_BLOCK, ModTags.Items.STORAGE_BLOCKS_AZURE_ELECTRUM)
                .dust(CraftingItems.AZURE_ELECTRUM_DUST, ModTags.Items.DUSTS_AZURE_ELECTRUM)
                .nugget(CraftingItems.AZURE_ELECTRUM_NUGGET, ModTags.Items.NUGGETS_AZURE_ELECTRUM));
        metals(consumer, 0.75f, new Metals("tyrian_steel", CraftingItems.TYRIAN_STEEL_INGOT, ModTags.Items.INGOTS_TYRIAN_STEEL)
                .block(ModBlocks.TYRIAN_STEEL_BLOCK, ModTags.Items.STORAGE_BLOCKS_TYRIAN_STEEL)
                .dust(CraftingItems.TYRIAN_STEEL_DUST, ModTags.Items.DUSTS_TYRIAN_STEEL)
                .nugget(CraftingItems.TYRIAN_STEEL_NUGGET, ModTags.Items.NUGGETS_TYRIAN_STEEL));
        metals(consumer, 1.0f, new Metals("bort", CraftingItems.BORT, ModTags.Items.GEMS_BORT)
                .block(ModBlocks.BORT_BLOCK, ModTags.Items.STORAGE_BLOCKS_BORT));

        registerSpecialRecipes(consumer);
        registerCraftingItems(consumer);
        registerBlueprints(consumer);
        registerCompoundParts(consumer);
        registerGear(consumer);
        registerModifierKits(consumer);
        registerMachines(consumer);
        registerCompounding(consumer);
        registerPressing(consumer);
        registerSmithing(consumer);
        registerSalvaging(consumer);
    }

    private void registerSpecialRecipes(Consumer<IFinishedRecipe> consumer) {
        special(consumer, (SpecialRecipeSerializer<?>) ModRecipes.FILL_REPAIR_KIT.get());
        special(consumer, (SpecialRecipeSerializer<?>) ModRecipes.SWAP_GEAR_PART.get());
        special(consumer, (SpecialRecipeSerializer<?>) ModRecipes.QUICK_REPAIR.get());
        special(consumer, (SpecialRecipeSerializer<?>) ModRecipes.COMBINE_FRAGMENTS.get());
        special(consumer, (SpecialRecipeSerializer<?>) ModRecipes.MOD_KIT_REMOVE_PART.get());
    }

    private void registerBlueprints(Consumer<IFinishedRecipe> consumer) {
        toolBlueprint(consumer, "sword", ModItems.SWORD_BLUEPRINT, ModItems.SWORD_TEMPLATE, "#", "#", "/");
        toolBlueprint(consumer, "katana", ModItems.KATANA_BLUEPRINT, ModItems.KATANA_TEMPLATE, "##", "# ", "/ ");
        toolBlueprint(consumer, "machete", ModItems.MACHETE_BLUEPRINT, ModItems.MACHETE_TEMPLATE, "  #", " ##", "/  ");
        toolBlueprint(consumer, "spear", ModItems.SPEAR_BLUEPRINT, ModItems.SPEAR_TEMPLATE, "#  ", " / ", "  /");
        toolBlueprint(consumer, "knife", ModItems.KNIFE_BLUEPRINT, ModItems.KNIFE_TEMPLATE, " #", "/ ");
        toolBlueprint(consumer, "dagger", ModItems.DAGGER_BLUEPRINT, ModItems.DAGGER_TEMPLATE, "#", "/");
        toolBlueprint(consumer, "pickaxe", ModItems.PICKAXE_BLUEPRINT, ModItems.PICKAXE_TEMPLATE, "###", " / ", " / ");
        toolBlueprint(consumer, "shovel", ModItems.SHOVEL_BLUEPRINT, ModItems.SHOVEL_TEMPLATE, "#", "/", "/");
        toolBlueprint(consumer, "axe", ModItems.AXE_BLUEPRINT, ModItems.AXE_TEMPLATE, "##", "#/", " /");
        toolBlueprint(consumer, "paxel", ModItems.PAXEL_BLUEPRINT, ModItems.PAXEL_TEMPLATE, "###", "#/#", " /#");
        toolBlueprint(consumer, "hammer", ModItems.HAMMER_BLUEPRINT, ModItems.HAMMER_TEMPLATE, "###", "###", " / ");
        toolBlueprint(consumer, "excavator", ModItems.EXCAVATOR_BLUEPRINT, ModItems.EXCAVATOR_TEMPLATE, "# #", "###", " / ");
        toolBlueprint(consumer, "saw", ModItems.SAW_BLUEPRINT, ModItems.SAW_TEMPLATE, "###", "##/", "  /");
        toolBlueprint(consumer, "mattock", ModItems.MATTOCK_BLUEPRINT, ModItems.MATTOCK_TEMPLATE, "## ", "#/#", " / ");
        toolBlueprint(consumer, "prospector_hammer", ModItems.PROSPECTOR_HAMMER_BLUEPRINT, ModItems.PROSPECTOR_HAMMER_TEMPLATE,
                Ingredient.fromTag(Tags.Items.INGOTS_IRON), "##", " /", " @");
        toolBlueprint(consumer, "sickle", ModItems.SICKLE_BLUEPRINT, ModItems.SICKLE_TEMPLATE, " #", "##", "/ ");
        toolBlueprint(consumer, "shears", ModItems.SHEARS_BLUEPRINT, ModItems.SHEARS_TEMPLATE, " #", "#/");
        toolBlueprint(consumer, "fishing_rod", ModItems.FISHING_ROD_BLUEPRINT, ModItems.FISHING_ROD_TEMPLATE, "  /", " /#", "/ #");
        toolBlueprint(consumer, "bow", ModItems.BOW_BLUEPRINT, ModItems.BOW_TEMPLATE, " #/", "# /", " #/");
        toolBlueprint(consumer, "crossbow", ModItems.CROSSBOW_BLUEPRINT, ModItems.CROSSBOW_TEMPLATE, "/#/", "###", " / ");
        toolBlueprint(consumer, "slingshot", ModItems.SLINGSHOT_BLUEPRINT, ModItems.SLINGSHOT_TEMPLATE, "# #", " / ", " / ");
        toolBlueprint(consumer, "shield", ModItems.SHIELD_BLUEPRINT, ModItems.SHIELD_TEMPLATE, "# #", "///", " # ");
        toolBlueprint(consumer, "arrow", ModItems.ARROW_BLUEPRINT, ModItems.ARROW_TEMPLATE, Ingredient.fromTag(Tags.Items.FEATHERS), "#", "/", "@");
        armorBlueprint(consumer, "helmet", ModItems.HELMET_BLUEPRINT, ModItems.HELMET_TEMPLATE, "###", "# #");
        armorBlueprint(consumer, "chestplate", ModItems.CHESTPLATE_BLUEPRINT, ModItems.CHESTPLATE_TEMPLATE, "# #", "###", "###");
        armorBlueprint(consumer, "leggings", ModItems.LEGGINGS_BLUEPRINT, ModItems.LEGGINGS_TEMPLATE, "###", "# #", "# #");
        armorBlueprint(consumer, "boots", ModItems.BOOTS_BLUEPRINT, ModItems.BOOTS_TEMPLATE, "# #", "# #");

        ShapedRecipeBuilder.shapedRecipe(ModItems.ELYTRA_BLUEPRINT)
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.INGOTS_NETHERITE)
                .key('e', Items.ELYTRA)
                .key('p', Items.PHANTOM_MEMBRANE)
                .patternLine("/e/")
                .patternLine("p#p")
                .patternLine("p p")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ELYTRA_TEMPLATE)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.INGOTS_NETHERITE)
                .key('e', Items.ELYTRA)
                .key('p', Items.PHANTOM_MEMBRANE)
                .patternLine("/e/")
                .patternLine("p#p")
                .patternLine("p p")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        // Curio blueprints
        ShapedRecipeBuilder.shapedRecipe(ModItems.RING_BLUEPRINT)
                .setGroup("silentgear:blueprints/ring")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL))
                .patternLine(" #/")
                .patternLine("# #")
                .patternLine("/# ")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RING_BLUEPRINT)
                .setGroup("silentgear:blueprints/ring")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.INGOTS_GOLD)
                .patternLine(" #/")
                .patternLine("# #")
                .patternLine("/# ")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer, SilentGear.getId("ring_blueprint_alt"));
        ShapedRecipeBuilder.shapedRecipe(ModItems.RING_TEMPLATE)
                .setGroup("silentgear:blueprints/ring")
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL))
                .patternLine(" #/")
                .patternLine("# #")
                .patternLine("/# ")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.BRACELET_BLUEPRINT)
                .setGroup("silentgear:blueprints/bracelet")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL))
                .patternLine("###")
                .patternLine("# #")
                .patternLine("/#/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BRACELET_BLUEPRINT)
                .setGroup("silentgear:blueprints/bracelet")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.INGOTS_GOLD)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("/#/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer, SilentGear.getId("bracelet_blueprint_alt"));
        ShapedRecipeBuilder.shapedRecipe(ModItems.BRACELET_TEMPLATE)
                .setGroup("silentgear:blueprints/bracelet")
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL))
                .patternLine("###")
                .patternLine("# #")
                .patternLine("/#/")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        // Part blueprints
        ShapedRecipeBuilder.shapedRecipe(ModItems.JEWELER_TOOLS)
                .patternLine("  p")
                .patternLine("d#s")
                .patternLine("ips")
                .key('p', ItemTags.PLANKS)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('s', Tags.Items.RODS_WOODEN)
                .key('i', Tags.Items.INGOTS_IRON)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.BINDING_BLUEPRINT)
                .setGroup("silentgear:blueprints/binding")
                .addIngredient(Ingredient.fromTag(ModTags.Items.BLUEPRINT_PAPER), 1)
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING, GearType.TOOL), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.BINDING_BLUEPRINT)
                .setGroup("silentgear:blueprints/binding")
                .addIngredient(ModTags.Items.BLUEPRINT_PAPER)
                .addIngredient(Tags.Items.STRING)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer, SilentGear.getId("binding_blueprint_alt"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.BINDING_TEMPLATE)
                .setGroup("silentgear:blueprints/binding")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 1)
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING, GearType.TOOL), 2)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.BOWSTRING_BLUEPRINT)
                .setGroup("silentgear:blueprints/bowstring")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', PartMaterialIngredient.of(PartType.BOWSTRING, GearType.TOOL))
                .patternLine("#/")
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.BOWSTRING_BLUEPRINT)
                .setGroup("silentgear:blueprints/bowstring")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.STRING)
                .patternLine("#/")
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer, SilentGear.getId("bowstring_blueprint_alt"));
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
                .addIngredient(Ingredient.fromTag(ModTags.Items.BLUEPRINT_PAPER), 2)
                .addIngredient(Tags.Items.FEATHERS)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.FLETCHING_TEMPLATE)
                .setGroup("silentgear:blueprints/fletching")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 2)
                .addIngredient(Tags.Items.FEATHERS)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.GRIP_BLUEPRINT)
                .setGroup("silentgear:blueprints/grip")
                .addIngredient(ModTags.Items.BLUEPRINT_PAPER, 2)
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP, GearType.TOOL))
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.GRIP_BLUEPRINT)
                .setGroup("silentgear:blueprints/grip")
                .addIngredient(ModTags.Items.BLUEPRINT_PAPER, 2)
                .addIngredient(ItemTags.WOOL)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer, SilentGear.getId("grip_blueprint_alt"));
        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.GRIP_TEMPLATE)
                .setGroup("silentgear:blueprints/grip")
                .addIngredient(ModTags.Items.TEMPLATE_BOARDS, 2)
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP, GearType.TOOL))
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.LINING_BLUEPRINT)
                .setGroup("silentgear:blueprints/lining")
                .addIngredient(ModTags.Items.BLUEPRINT_PAPER, 3)
                .addIngredient(ItemTags.WOOL, 2)
                .addIngredient(Tags.Items.STRING, 2)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.LINING_TEMPLATE)
                .setGroup("silentgear:blueprints/lining")
                .addIngredient(ModTags.Items.TEMPLATE_BOARDS, 3)
                .addIngredient(ItemTags.WOOL, 2)
                .addIngredient(Tags.Items.STRING, 2)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.ROD_BLUEPRINT)
                .setGroup("silentgear:blueprints/rod")
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine("#/")
                .patternLine("#/")
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
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
                .addIngredient(Ingredient.fromTag(ModTags.Items.BLUEPRINT_PAPER), 2)
                .addIngredient(ModTags.Items.PAPER)
                .addIngredient(Tags.Items.STONE)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.TIP_TEMPLATE)
                .setGroup("silentgear:blueprints/tip")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 2)
                .addIngredient(ModTags.Items.PAPER)
                .addIngredient(Tags.Items.STONE)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.COATING_BLUEPRINT)
                .setGroup("silentgear:blueprints/coating")
                .addIngredient(Ingredient.fromTag(ModTags.Items.BLUEPRINT_PAPER), 4)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.COATING_TEMPLATE)
                .setGroup("silentgear:blueprints/coating")
                .addIngredient(Ingredient.fromTag(ModTags.Items.TEMPLATE_BOARDS), 4)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.BLUEPRINT_BOOK)
                .addIngredient(Items.BOOK)
                .addIngredient(ItemTags.WOOL)
                .addIngredient(Tags.Items.INGOTS_GOLD)
                .addIngredient(ModTags.Items.TEMPLATE_BOARDS, 3)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);
    }

    private void registerCompoundParts(Consumer<IFinishedRecipe> consumer) {
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.ADORNMENT)
                .addIngredient(BlueprintIngredient.of(ModItems.JEWELER_TOOLS.get()))
                .addIngredient(CraftingItems.BORT)
                .addIngredient(PartMaterialIngredient.of(PartType.ADORNMENT))
                .build(consumer, SilentGear.getId("part/adornment"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.ADORNMENT)
                .addIngredient(BlueprintIngredient.of(ModItems.JEWELER_TOOLS.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.ADORNMENT))
                .build(consumer, SilentGear.getId("part/adornment_old"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.ROD, 4)
                .addIngredient(BlueprintIngredient.of(ModItems.ROD_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.ROD), 2)
                .build(consumer, SilentGear.getId("part/rod"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.LONG_ROD, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.ROD_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.ROD), 3)
                .build(consumer, SilentGear.getId("part/long_rod"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.BINDING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.BINDING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING))
                .build(consumer, SilentGear.getId("part/binding"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.BINDING, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.BINDING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.BINDING), 2)
                .build(consumer, SilentGear.getId("part/binding2"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.BOWSTRING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.BOWSTRING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.BOWSTRING), 3)
                .build(consumer, SilentGear.getId("part/bowstring"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.FLETCHING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.FLETCHING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.FLETCHING), 1)
                .build(consumer, SilentGear.getId("part/fletching"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.GRIP, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.GRIP_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP))
                .build(consumer, SilentGear.getId("part/grip"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.GRIP, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.GRIP_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.GRIP), 2)
                .build(consumer, SilentGear.getId("part/grip2"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.LINING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.LINING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.LINING))
                .build(consumer, SilentGear.getId("part/lining"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.TIP, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.TIP_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.TIP))
                .build(consumer, SilentGear.getId("part/tip"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.TIP, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.TIP_BLUEPRINT.get()))
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(PartMaterialIngredient.of(PartType.TIP))
                .build(consumer, SilentGear.getId("part/tip_alt"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.TIP, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.TIP_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.TIP), 2)
                .build(consumer, SilentGear.getId("part/tip2"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.TIP, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.TIP_BLUEPRINT.get()))
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(PartMaterialIngredient.of(PartType.TIP), 2)
                .build(consumer, SilentGear.getId("part/tip2_alt"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.COATING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.COATING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.COATING))
                .build(consumer, SilentGear.getId("part/coating"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.COATING, 1)
                .addIngredient(BlueprintIngredient.of(ModItems.COATING_BLUEPRINT.get()))
                .addIngredient(Items.GLASS_BOTTLE)
                .addIngredient(PartMaterialIngredient.of(PartType.COATING))
                .build(consumer, SilentGear.getId("part/coating_alt"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.COATING, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.COATING_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.COATING), 2)
                .build(consumer, SilentGear.getId("part/coating2"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.COATING, 2)
                .addIngredient(BlueprintIngredient.of(ModItems.COATING_BLUEPRINT.get()))
                .addIngredient(Items.GLASS_BOTTLE)
                .addIngredient(PartMaterialIngredient.of(PartType.COATING), 2)
                .build(consumer, SilentGear.getId("part/coating2_alt"));
    }

    private void registerGear(Consumer<IFinishedRecipe> consumer) {
        toolRecipes(consumer, "sword", 2, ModItems.SWORD, ModItems.SWORD_BLADE, ModItems.SWORD_BLUEPRINT.get());
        toolRecipes(consumer, "katana", 3, ModItems.KATANA, ModItems.KATANA_BLADE, ModItems.KATANA_BLUEPRINT.get());
        toolRecipes(consumer, "machete", 3, ModItems.MACHETE, ModItems.MACHETE_BLADE, ModItems.MACHETE_BLUEPRINT.get());
        toolRecipes(consumer, "spear", 1, ModItems.SPEAR, ModItems.SPEAR_TIP, ModItems.SPEAR_BLUEPRINT.get());
        toolRecipes(consumer, "knife", 1, ModItems.KNIFE, ModItems.KNIFE_BLADE, ModItems.KNIFE_BLUEPRINT.get());
        toolRecipes(consumer, "dagger", 1, ModItems.DAGGER, ModItems.DAGGER_BLADE, ModItems.DAGGER_BLUEPRINT.get());
        toolRecipes(consumer, "pickaxe", 3, ModItems.PICKAXE, ModItems.PICKAXE_HEAD, ModItems.PICKAXE_BLUEPRINT.get());
        toolRecipes(consumer, "shovel", 1, ModItems.SHOVEL, ModItems.SHOVEL_HEAD, ModItems.SHOVEL_BLUEPRINT.get());
        toolRecipes(consumer, "axe", 3, ModItems.AXE, ModItems.AXE_HEAD, ModItems.AXE_BLUEPRINT.get());
        toolRecipes(consumer, "paxel", 5, ModItems.PAXEL, ModItems.PAXEL_HEAD, ModItems.PAXEL_BLUEPRINT.get());
        toolRecipes(consumer, "hammer", 6, ModItems.HAMMER, ModItems.HAMMER_HEAD, ModItems.HAMMER_BLUEPRINT.get());
        toolRecipes(consumer, "excavator", 5, ModItems.EXCAVATOR, ModItems.EXCAVATOR_HEAD, ModItems.EXCAVATOR_BLUEPRINT.get());
        toolRecipes(consumer, "mattock", 4, ModItems.MATTOCK, ModItems.MATTOCK_HEAD, ModItems.MATTOCK_BLUEPRINT.get());
        toolRecipes(consumer, "prospector_hammer", 2, ModItems.PROSPECTOR_HAMMER, ModItems.PROSPECTOR_HAMMER_HEAD, ModItems.PROSPECTOR_HAMMER_BLUEPRINT.get());
        toolRecipes(consumer, "saw", 5, ModItems.SAW, ModItems.SAW_BLADE, ModItems.SAW_BLUEPRINT.get());
        toolRecipes(consumer, "sickle", 3, ModItems.SICKLE, ModItems.SICKLE_BLADE, ModItems.SICKLE_BLUEPRINT.get());
        toolRecipes(consumer, "shears", 2, ModItems.SHEARS, ModItems.SHEARS_BLADES, ModItems.SHEARS_BLUEPRINT.get());
        bowRecipes(consumer, "fishing_rod", 2, ModItems.FISHING_ROD, ModItems.FISHING_REEL_AND_HOOK, ModItems.FISHING_ROD_BLUEPRINT.get());
        bowRecipes(consumer, "bow", 3, ModItems.BOW, ModItems.BOW_LIMBS, ModItems.BOW_BLUEPRINT.get());
        bowRecipes(consumer, "crossbow", 3, ModItems.CROSSBOW, ModItems.CROSSBOW_LIMBS, ModItems.CROSSBOW_BLUEPRINT.get());
        bowRecipes(consumer, "slingshot", 2, ModItems.SLINGSHOT, ModItems.SLINGSHOT_LIMBS, ModItems.SLINGSHOT_BLUEPRINT.get());
        arrowRecipes(consumer, "arrow", ModItems.ARROW, ModItems.ARROW_HEADS, ModItems.ARROW_BLUEPRINT.get());

        curioRecipes(consumer, "ring", 2, ModItems.RING, ModItems.RING_SHANK, ModItems.RING_BLUEPRINT.get());
        curioRecipes(consumer, "bracelet", 3, ModItems.BRACELET, ModItems.BRACELET_BAND, ModItems.BRACELET_BLUEPRINT.get());

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), ModItems.SHIELD)
                .addIngredient(BlueprintIngredient.of(ModItems.SHIELD_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.ARMOR), 2)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/shield"));

        armorRecipes(consumer, 5, ModItems.HELMET.get(), ModItems.HELMET_PLATES, ModItems.HELMET_BLUEPRINT.get());
        armorRecipes(consumer, 8, ModItems.CHESTPLATE.get(), ModItems.CHESTPLATE_PLATES, ModItems.CHESTPLATE_BLUEPRINT.get());
        armorRecipes(consumer, 7, ModItems.LEGGINGS.get(), ModItems.LEGGING_PLATES, ModItems.LEGGINGS_BLUEPRINT.get());
        armorRecipes(consumer, 4, ModItems.BOOTS.get(), ModItems.BOOT_PLATES, ModItems.BOOTS_BLUEPRINT.get());

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), ModItems.ELYTRA_WINGS)
                .addIngredient(BlueprintIngredient.of(ModItems.ELYTRA_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN,
                        GearType.ELYTRA,
                        MaterialCategories.CLOTH,
                        MaterialCategories.SHEET), 6)
                .build(consumer, SilentGear.getId("gear/elytra_wings"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), ModItems.ELYTRA.get())
                .addIngredient(ModItems.ELYTRA_WINGS)
                .addIngredient(GearPartIngredient.of(PartType.BINDING))
                .build(consumer, SilentGear.getId("gear/elytra"));

        // Rough recipes
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.SWORD)
                .patternLine("#")
                .patternLine("#")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/sword"));
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.DAGGER)
                .patternLine("#")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/dagger"));
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.KNIFE)
                .patternLine(" #")
                .patternLine("/ ")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/knife"));
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.PICKAXE)
                .patternLine("###")
                .patternLine(" / ")
                .patternLine(" / ")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/pickaxe"));
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.SHOVEL)
                .patternLine("#")
                .patternLine("/")
                .patternLine("/")
                .key('#', PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .key('/', ModTags.Items.RODS_ROUGH)
                .build(consumer, SilentGear.getId("gear/rough/shovel"));
        ExtendedShapedRecipeBuilder.builder(ModRecipes.SHAPED_GEAR.get(), ModItems.AXE)
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
    }

    private void registerModifierKits(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.MOD_KIT)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('o', Tags.Items.INGOTS_IRON)
                .patternLine("##o")
                .patternLine("##/")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.VERY_CRUDE_REPAIR_KIT)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('o', Tags.Items.STONE)
                .patternLine(" / ")
                .patternLine("#o#")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

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

        ShapedRecipeBuilder.shapedRecipe(ModItems.AZURE_REPAIR_KIT)
                .key('#', ModTags.Items.INGOTS_AZURE_ELECTRUM)
                .key('/', Items.END_ROD)
                .key('o', Tags.Items.GEMS_EMERALD)
                .patternLine(" / ")
                .patternLine("#o#")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_AZURE_ELECTRUM))
                .build(consumer);

        for (RepairKitItem item : Registration.getItems(RepairKitItem.class)) {
            // Empty repair kit recipes
            ExtendedShapelessRecipeBuilder.vanillaBuilder(item)
                    .addIngredient(item)
                    .addIngredient(Tags.Items.RODS_WOODEN)
                    .build(consumer, SilentGear.getId(NameUtils.from(item).getPath() + "_empty"));
        }
    }

    private void registerMachines(Consumer<IFinishedRecipe> consumer) {
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.METAL_ALLOYER)
                .key('/', ModTags.Items.INGOTS_CRIMSON_STEEL)
                .key('i', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('#', Blocks.BLACKSTONE)
                .patternLine("/#/")
                .patternLine("/ /")
                .patternLine("#i#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.RECRYSTALLIZER)
                .key('/', ModTags.Items.INGOTS_AZURE_ELECTRUM)
                .key('g', Tags.Items.STORAGE_BLOCKS_GOLD)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('#', Blocks.PURPUR_BLOCK)
                .patternLine("/e/")
                .patternLine("/d/")
                .patternLine("#g#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.REFABRICATOR)
                .key('/', Tags.Items.INGOTS_IRON)
                .key('i', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('b', ModTags.Items.GEMS_BORT)
                .key('#', ItemTags.PLANKS)
                .patternLine("/ /")
                .patternLine("dbd")
                .patternLine("#i#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.METAL_PRESS)
                .key('#', Tags.Items.OBSIDIAN)
                .key('t', ModTags.Items.INGOTS_TYRIAN_STEEL)
                .key('/', ModTags.Items.RODS_IRON)
                .patternLine("#t#")
                .patternLine("/ /")
                .patternLine("#t#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.STARLIGHT_CHARGER)
                .key('#', Blocks.POLISHED_BLACKSTONE)
                .key('/', ModTags.Items.STORAGE_BLOCKS_BLAZE_GOLD)
                .key('q', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .key('g', Tags.Items.GLASS_COLORLESS)
                .patternLine("qgq")
                .patternLine("#g#")
                .patternLine("#/#")
                .build(consumer);
    }

    private void registerCompounding(Consumer<IFinishedRecipe> consumer) {
        CompoundingRecipeBuilder.gemBuilder(ModItems.CUSTOM_GEM, 1)
                .withCustomMaterial(Const.Materials.DIMERALD)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .build(consumer);

        CompoundingRecipeBuilder.metalBuilder(ModItems.CUSTOM_INGOT, 1)
                .withCustomMaterial(DataResource.material("high_carbon_steel"))
                .addIngredient(Tags.Items.INGOTS_IRON)
                .addIngredient(ItemTags.COALS, 3)
                .build(consumer);

        CompoundingRecipeBuilder.metalBuilder(CraftingItems.TYRIAN_STEEL_INGOT, 4)
                .addIngredient(ModTags.Items.INGOTS_CRIMSON_STEEL)
                .addIngredient(ModTags.Items.INGOTS_AZURE_ELECTRUM)
                .addIngredient(CraftingItems.CRUSHED_SHULKER_SHELL)
                .addIngredient(Items.NETHERITE_SCRAP)
                .build(consumer);
    }

    private void registerPressing(Consumer<IFinishedRecipe> consumer) {
        ExtendedSingleItemRecipeBuilder.builder(ModRecipes.PRESSING_MATERIAL.get(),
                PartMaterialIngredient.of(PartType.MAIN, MaterialCategories.METAL),
                ModItems.SHEET_METAL, 2)
                .build(consumer);
    }

    private void registerCraftingItems(Consumer<IFinishedRecipe> consumer) {
        shapelessBuilder(ModItems.GUIDE_BOOK)
                .addIngredient(Items.BOOK)
                .addIngredient(ModTags.Items.TEMPLATE_BOARDS)
                .addCriterion("has_template_board", hasItem(ModTags.Items.TEMPLATE_BOARDS))
                .build(consumer);

        damageGear(CraftingItems.GLOWING_DUST, 4, 4)
                .addIngredient(ModTags.Items.HAMMERS)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE, 2)
                .addIngredient(Tags.Items.GEMS_QUARTZ)
                .build(consumer);

        damageGear(ModItems.PEBBLE, 9, 1)
                .addIngredient(ModTags.Items.HAMMERS)
                .addIngredient(Tags.Items.COBBLESTONE)
                .build(consumer);

        damageGear(CraftingItems.TEMPLATE_BOARD, 6, 1)
                .addIngredient(ModTags.Items.KNIVES)
                .addIngredient(ItemTags.LOGS)
                .build(consumer);

        damageGear(CraftingItems.CRUSHED_SHULKER_SHELL, 1, 10)
                .addIngredient(ModTags.Items.HAMMERS)
                .addIngredient(Items.SHULKER_SHELL)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.AZURE_ELECTRUM_INGOT)
                .key('/', Tags.Items.INGOTS_GOLD)
                .key('#', ModTags.Items.INGOTS_AZURE_SILVER)
                .key('o', Tags.Items.ENDER_PEARLS)
                .patternLine("/ /")
                .patternLine("#o#")
                .patternLine("# #")
                .addCriterion("has_item", hasItem(CraftingItems.AZURE_SILVER_INGOT))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE)
                .addIngredient(CraftingItems.FLAX_FLOWERS, 4)
                .addCriterion("has_item", hasItem(CraftingItems.FLAX_FLOWERS))
                .build(consumer, SilentGear.getId("blue_dye_from_flax_flowers"));

        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.ROAD_MAKER_UPGRADE)
                .addIngredient(CraftingItems.ADVANCED_UPGRADE_BASE)
                .addIngredient(Items.IRON_SHOVEL)
                .addIngredient(Tags.Items.DYES_ORANGE)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.SPOON_UPGRADE)
                .addIngredient(CraftingItems.ADVANCED_UPGRADE_BASE)
                .addIngredient(Items.DIAMOND_SHOVEL)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);

        shapelessBuilder(CraftingItems.WIDE_PLATE_UPGRADE)
                .addIngredient(CraftingItems.ADVANCED_UPGRADE_BASE)
                .addIngredient(ModTags.Items.STORAGE_BLOCKS_CRIMSON_IRON)
                .addIngredient(ModTags.Items.INGOTS_CRIMSON_STEEL)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ModTags.Items.NETHERWOOD_LOGS), ModItems.NETHERWOOD_CHARCOAL, 0.15f, 200)
                .addCriterion("has_item", hasItem(ModTags.Items.NETHERWOOD_LOGS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_CHARCOAL_BLOCK)
                .key('#', ModItems.NETHERWOOD_CHARCOAL)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModItems.NETHERWOOD_CHARCOAL))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.NETHERWOOD_CHARCOAL, 9)
                .addIngredient(ModBlocks.NETHERWOOD_CHARCOAL_BLOCK)
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_CHARCOAL_BLOCK))
                .build(consumer, SilentGear.getId("netherwood_charcoal_from_block"));

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.FLUFFY_FABRIC)
                .patternLine("##")
                .patternLine("##")
                .key('#', CraftingItems.FLUFFY_PUFF)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.FLUFFY_PUFF, 4)
                .addIngredient(CraftingItems.FLUFFY_FABRIC)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer, SilentGear.getId("fluffy_puff_from_fabric"));

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.WHITE_FLUFFY_BLOCK)
                .patternLine("##")
                .patternLine("##")
                .key('#', CraftingItems.FLUFFY_FABRIC)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer, SilentGear.getId("fluffy_block_base"));
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.FLUFFY_FABRIC, 4)
                .addIngredient(ModTags.Items.FLUFFY_BLOCKS)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer, SilentGear.getId("fluffy_fabric_from_block"));

        dyeFluffyBlock(consumer, ModBlocks.WHITE_FLUFFY_BLOCK, Tags.Items.DYES_WHITE);
        dyeFluffyBlock(consumer, ModBlocks.ORANGE_FLUFFY_BLOCK, Tags.Items.DYES_ORANGE);
        dyeFluffyBlock(consumer, ModBlocks.MAGENTA_FLUFFY_BLOCK, Tags.Items.DYES_MAGENTA);
        dyeFluffyBlock(consumer, ModBlocks.LIGHT_BLUE_FLUFFY_BLOCK, Tags.Items.DYES_LIGHT_BLUE);
        dyeFluffyBlock(consumer, ModBlocks.YELLOW_FLUFFY_BLOCK, Tags.Items.DYES_YELLOW);
        dyeFluffyBlock(consumer, ModBlocks.LIME_FLUFFY_BLOCK, Tags.Items.DYES_LIME);
        dyeFluffyBlock(consumer, ModBlocks.PINK_FLUFFY_BLOCK, Tags.Items.DYES_PINK);
        dyeFluffyBlock(consumer, ModBlocks.GRAY_FLUFFY_BLOCK, Tags.Items.DYES_GRAY);
        dyeFluffyBlock(consumer, ModBlocks.LIGHT_GRAY_FLUFFY_BLOCK, Tags.Items.DYES_LIGHT_GRAY);
        dyeFluffyBlock(consumer, ModBlocks.CYAN_FLUFFY_BLOCK, Tags.Items.DYES_CYAN);
        dyeFluffyBlock(consumer, ModBlocks.PURPLE_FLUFFY_BLOCK, Tags.Items.DYES_PURPLE);
        dyeFluffyBlock(consumer, ModBlocks.BLUE_FLUFFY_BLOCK, Tags.Items.DYES_BLUE);
        dyeFluffyBlock(consumer, ModBlocks.BROWN_FLUFFY_BLOCK, Tags.Items.DYES_BROWN);
        dyeFluffyBlock(consumer, ModBlocks.GREEN_FLUFFY_BLOCK, Tags.Items.DYES_GREEN);
        dyeFluffyBlock(consumer, ModBlocks.RED_FLUFFY_BLOCK, Tags.Items.DYES_RED);
        dyeFluffyBlock(consumer, ModBlocks.BLACK_FLUFFY_BLOCK, Tags.Items.DYES_BLACK);

        ShapelessRecipeBuilder.shapelessRecipe(ModItems.FLUFFY_SEEDS)
                .addIngredient(CraftingItems.FLUFFY_PUFF)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.FLUFFY_FEATHER)
                .patternLine(" ##")
                .patternLine("## ")
                .patternLine("#  ")
                .key('#', CraftingItems.FLUFFY_PUFF)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(Items.FEATHER)
                .addIngredient(CraftingItems.FLUFFY_FEATHER)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.FLUFFY_STRING)
                .patternLine("###")
                .key('#', CraftingItems.FLUFFY_PUFF)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(Items.STRING)
                .addIngredient(CraftingItems.FLUFFY_STRING)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(Items.WHITE_WOOL)
                .patternLine("###")
                .patternLine("#~#")
                .patternLine("###")
                .key('#', CraftingItems.FLUFFY_PUFF)
                .key('~', Tags.Items.STRING)
                .addCriterion("has_item", hasItem(CraftingItems.FLUFFY_PUFF))
                .build(consumer, SilentGear.getId("fluffy_wool"));

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.FINE_SILK_CLOTH)
                .patternLine("##")
                .patternLine("##")
                .key('#', CraftingItems.FINE_SILK)
                .addCriterion("has_item", hasItem(CraftingItems.FINE_SILK))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.FINE_SILK, 4)
                .addIngredient(CraftingItems.FINE_SILK_CLOTH)
                .addCriterion("has_item", hasItem(CraftingItems.FINE_SILK))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(CraftingItems.NETHER_STAR_FRAGMENT, 9)
                .addIngredient(Items.NETHER_STAR)
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(Items.NETHER_STAR)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', CraftingItems.NETHER_STAR_FRAGMENT)
                .build(consumer, SilentGear.getId("nether_star_from_fragments"));

        ExtendedShapelessRecipeBuilder.vanillaBuilder(CraftingItems.STARMETAL_DUST, 3)
                .addIngredient(ModTags.Items.DUSTS_AZURE_ELECTRUM, 1)
                .addIngredient(ModTags.Items.DUSTS_AZURE_SILVER, 2)
                .addIngredient(ModTags.Items.DUSTS_BLAZE_GOLD, 1)
                .addIngredient(CraftingItems.NETHER_STAR_FRAGMENT)
                .build(consumer);

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
        // F
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
        // L
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
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_FENCE_GATE, 1)
                .key('#', ModBlocks.NETHERWOOD_PLANKS)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine("/#/")
                .patternLine("/#/")
                .addCriterion("has_item", hasItem(ModBlocks.NETHERWOOD_PLANKS))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.NETHERWOOD_PLANKS, 4)
                .addIngredient(ModTags.Items.NETHERWOOD_LOGS)
                .addCriterion("has_item", hasItem(ModTags.Items.NETHERWOOD_LOGS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.NETHERWOOD_WOOD, 3)
                .key('#', ModBlocks.NETHERWOOD_LOG)
                .patternLine("##")
                .patternLine("##")
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
        // R
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.RED_CARD_UPGRADE, 4)
                .addIngredient(CraftingItems.UPGRADE_BASE)
                .addIngredient(Tags.Items.DYES_RED)
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_BASE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.ROUGH_ROD, 2)
                .key('/', Tags.Items.RODS_WOODEN)
                .patternLine(" /")
                .patternLine("/ ")
                .addCriterion("has_item", hasItem(Items.STICK))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.ROUGH_ROD, 2)
                .addIngredient(ModItems.ROD_BLUEPRINT.get().getItemTag())
                .addIngredient(Ingredient.fromTag(Tags.Items.RODS_WOODEN), 2)
                .addCriterion("has_item", hasItem(ModItems.ROD_BLUEPRINT.get().getItemTag()))
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
        ShapelessRecipeBuilder.shapelessRecipe(CraftingItems.SINEW_FIBER, 3)
                .addIngredient(CraftingItems.DRIED_SINEW)
                .addCriterion("has_item", hasItem(CraftingItems.SINEW))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.STONE_ROD, 4)
                .key('#', Tags.Items.COBBLESTONE)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_item", hasItem(Tags.Items.COBBLESTONE))
                .build(consumer);
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
    }

    private void dyeFluffyBlock(Consumer<IFinishedRecipe> consumer, IItemProvider block, ITag<Item> dye) {
//        ShapedRecipeBuilder.shapedRecipe(block, 8)
//                .patternLine("###")
//                .patternLine("#d#")
//                .patternLine("###")
//                .key('#', ModTags.Items.FLUFFY_BLOCKS)
//                .key('d', dye)
//                .addCriterion("has_item", hasItem(ModBlocks.WHITE_FLUFFY_BLOCK))
//                .build(consumer);
    }

    private void registerSmithing(Consumer<IFinishedRecipe> consumer) {
        Registration.getItems(item -> item instanceof ICoreItem).forEach(item -> {
            if (((ICoreItem) item).getGearType() != GearType.ELYTRA) {
                GearSmithingRecipeBuilder.coating(item).build(consumer);
            }
            GearSmithingRecipeBuilder.upgrade(item, PartType.MISC_UPGRADE).build(consumer);
        });
    }

    private void registerSalvaging(Consumer<IFinishedRecipe> consumer) {
        Registration.getItems(item -> item instanceof ICoreItem).forEach(item ->
                gearSalvage(consumer, (ICoreItem) item));

        vanillaSalvage(consumer, Items.NETHERITE_SWORD, Items.DIAMOND, 2, 1, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_PICKAXE, Items.DIAMOND, 3, 2, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_SHOVEL, Items.DIAMOND, 1, 2, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_AXE, Items.DIAMOND, 3, 2, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_HOE, Items.DIAMOND, 2, 2, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_HELMET, Items.DIAMOND, 5, 0, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_CHESTPLATE, Items.DIAMOND, 8, 0, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_LEGGINGS, Items.DIAMOND, 7, 0, Items.NETHERITE_INGOT);
        vanillaSalvage(consumer, Items.NETHERITE_BOOTS, Items.DIAMOND, 4, 0, Items.NETHERITE_INGOT);

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
        vanillaSalvage(consumer, Items.SHEARS, Items.IRON_INGOT, 2, 0);

        vanillaSalvage(consumer, Items.LEATHER_HELMET, Items.LEATHER, 5, 0);
        vanillaSalvage(consumer, Items.LEATHER_CHESTPLATE, Items.LEATHER, 8, 0);
        vanillaSalvage(consumer, Items.LEATHER_LEGGINGS, Items.LEATHER, 7, 0);
        vanillaSalvage(consumer, Items.LEATHER_BOOTS, Items.LEATHER, 4, 0);
        vanillaSalvage(consumer, Items.LEATHER_HORSE_ARMOR, Items.LEATHER, 7, 0);

        vanillaSalvage(consumer, Items.STONE_SWORD, Items.COBBLESTONE, 2, 1);
        vanillaSalvage(consumer, Items.STONE_PICKAXE, Items.COBBLESTONE, 3, 2);
        vanillaSalvage(consumer, Items.STONE_SHOVEL, Items.COBBLESTONE, 1, 2);
        vanillaSalvage(consumer, Items.STONE_AXE, Items.COBBLESTONE, 3, 2);
        vanillaSalvage(consumer, Items.STONE_HOE, Items.COBBLESTONE, 2, 2);

        vanillaSalvage(consumer, Items.BOW, Items.STRING, 3, 3);

        SalvagingRecipeBuilder.builder(Items.DIAMOND_HORSE_ARMOR)
                .addResult(Items.DIAMOND, 6)
                .addResult(Items.LEATHER)
                .build(consumer, SilentGear.getId("salvaging/diamond_horse_armor"));

        SalvagingRecipeBuilder.builder(Items.GOLDEN_HORSE_ARMOR)
                .addResult(Items.GOLD_INGOT, 6)
                .addResult(Items.LEATHER)
                .build(consumer, SilentGear.getId("salvaging/golden_horse_armor"));

        SalvagingRecipeBuilder.builder(Items.IRON_HORSE_ARMOR)
                .addResult(Items.IRON_INGOT, 6)
                .addResult(Items.LEATHER)
                .build(consumer, SilentGear.getId("salvaging/iron_horse_armor"));

        SalvagingRecipeBuilder.builder(Items.CROSSBOW)
                .addResult(Items.STICK, 3)
                .addResult(Items.STRING, 2)
                .addResult(Items.IRON_INGOT)
                .addResult(Items.TRIPWIRE_HOOK)
                .build(consumer, SilentGear.getId("salvaging/crossbow"));

        SalvagingRecipeBuilder.builder(Items.CLOCK)
                .addResult(Items.GOLD_INGOT, 4)
                .addResult(Items.REDSTONE)
                .build(consumer, SilentGear.getId("salvaging/clock"));

        SalvagingRecipeBuilder.builder(Items.COMPASS)
                .addResult(Items.IRON_INGOT, 4)
                .addResult(Items.REDSTONE)
                .build(consumer, SilentGear.getId("salvaging/compass"));
    }

    private void special(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {
        CustomRecipeBuilder.customRecipe(serializer).build(consumer, NameUtils.from(serializer).toString());
    }

    private ExtendedShapelessRecipeBuilder damageGear(IItemProvider result, int count, int damage) {
        return ExtendedShapelessRecipeBuilder.builder(ModRecipes.DAMAGE_ITEM.get(), result, count)
                .addExtraData(json -> json.addProperty("damage", damage));
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    private static void toolRecipes(Consumer<IFinishedRecipe> consumer, String name, int mainCount, IItemProvider tool, IItemProvider toolHead, GearBlueprintItem blueprintItem) {
        // Tool head
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), toolHead)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .build(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Tool from head and rod
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), tool)
                .addIngredient(toolHead)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/" + name));
        // Quick tool (mains and rods, skipping head)
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), tool)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .build(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    private static void bowRecipes(Consumer<IFinishedRecipe> consumer, String name, int mainCount, IItemProvider tool, IItemProvider toolHead, GearBlueprintItem blueprintItem) {
        // Main part
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), toolHead)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .build(consumer, SilentGear.getId("gear/" + name + "_main"));
        // Tool from main, rod, and bowstring
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), tool)
                .addIngredient(toolHead)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.BOWSTRING))
                .build(consumer, SilentGear.getId("gear/" + name));
        // Quick tool (main materials, rod, and bowstring, skipping main part)
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), tool)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL), mainCount)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.BOWSTRING))
                .build(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private static void arrowRecipes(Consumer<IFinishedRecipe> consumer, String name, IItemProvider arrow, IItemProvider arrowHead, GearBlueprintItem blueprintItem) {
        BlueprintIngredient blueprint = BlueprintIngredient.of(blueprintItem);
        // Arrow head
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), arrowHead)
                .addIngredient(blueprint)
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.PROJECTILE))
                .build(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Arrows from head
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), arrow)
                .addIngredient(arrowHead)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.FLETCHING))
                .build(consumer, SilentGear.getId("gear/" + name));
        // Quick arrows
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), arrow)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.TOOL))
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(PartType.FLETCHING))
                .build(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private void armorRecipes(Consumer<IFinishedRecipe> consumer, int mainCount, CoreArmor armor, IItemProvider plates, GearBlueprintItem blueprintItem) {
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), plates)
                .addIngredient(BlueprintIngredient.of(blueprintItem))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, armor.getGearType()), mainCount)
                .build(consumer, SilentGear.getId("gear/" + NameUtils.fromItem(plates).getPath()));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), armor)
                .addIngredient(plates)
                .build(consumer, SilentGear.getId("gear/" + NameUtils.fromItem(armor).getPath()));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), armor)
                .addIngredient(plates)
                .addIngredient(GearPartIngredient.of(PartType.LINING))
                .build(consumer, SilentGear.getId("gear/" + NameUtils.fromItem(armor).getPath() + "_with_lining"));
    }

    private static void curioRecipes(Consumer<IFinishedRecipe> consumer, String name, int mainCount, IItemProvider curioItem, IItemProvider curioMain, GearBlueprintItem blueprint) {
        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), curioMain)
                .addIngredient(BlueprintIngredient.of(blueprint))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL), mainCount)
                .build(consumer, SilentGear.getId("gear/" + name + "_main_only"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), curioItem)
                .addIngredient(BlueprintIngredient.of(ModItems.JEWELER_TOOLS.get()))
                .addIngredient(curioMain)
                .build(consumer, SilentGear.getId("gear/" + name));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), curioItem)
                .addIngredient(BlueprintIngredient.of(ModItems.JEWELER_TOOLS.get()))
                .addIngredient(curioMain)
                .addIngredient(GearPartIngredient.of(PartType.ADORNMENT))
                .build(consumer, SilentGear.getId("gear/" + name + "_with_gem"));

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), curioItem)
                .addIngredient(BlueprintIngredient.of(blueprint))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN, GearType.CURIO, MaterialCategories.METAL), mainCount)
                .addIngredient(GearPartIngredient.of(PartType.ADORNMENT))
                .build(consumer, SilentGear.getId("gear/" + name + "quick"));
    }

    private void toolBlueprint(Consumer<IFinishedRecipe> consumer, String group, IItemProvider blueprint, IItemProvider template, String... pattern) {
        toolBlueprint(consumer, group, blueprint, template, Ingredient.EMPTY, pattern);
    }

    private void toolBlueprint(Consumer<IFinishedRecipe> consumer, String group, IItemProvider blueprint, IItemProvider template, Ingredient extra, String... pattern) {
        ShapedRecipeBuilder builderBlueprint = ShapedRecipeBuilder.shapedRecipe(blueprint)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER));

        ShapedRecipeBuilder builderTemplate = ShapedRecipeBuilder.shapedRecipe(template)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.TEMPLATE_BOARDS)
                .key('/', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(ModTags.Items.TEMPLATE_BOARDS));

        if (extra != Ingredient.EMPTY) {
            builderBlueprint.key('@', extra);
            builderTemplate.key('@', extra);
        }

        for (String line : pattern) {
            builderBlueprint.patternLine(line);
            builderTemplate.patternLine(line);
        }

        builderBlueprint.build(consumer);
        builderTemplate.build(consumer);
    }

    private void armorBlueprint(Consumer<IFinishedRecipe> consumer, String group, IItemProvider blueprint, IItemProvider template, String... pattern) {
        ShapedRecipeBuilder builderBlueprint = ShapedRecipeBuilder.shapedRecipe(blueprint)
                .setGroup("silentgear:blueprints/" + group)
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .addCriterion("has_item", hasItem(ModTags.Items.BLUEPRINT_PAPER));
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
    private static final Map<IArmorMaterial, ResourceLocation> ARMOR_MATERIALS = ImmutableMap.<IArmorMaterial, ResourceLocation>builder()
            .put(ArmorMaterial.DIAMOND, SilentGear.getId("diamond"))
            .put(ArmorMaterial.GOLD, SilentGear.getId("gold"))
            .put(ArmorMaterial.IRON, SilentGear.getId("iron"))
            .put(ArmorMaterial.LEATHER, SilentGear.getId("leather"))
            .build();

    private static void toolConversion(Consumer<IFinishedRecipe> consumer, IItemProvider result, Item... toolItems) {
        for (Item input : toolItems) {
            assert input instanceof TieredItem;
            ExtendedShapelessRecipeBuilder.builder(ModRecipes.CONVERSION.get(), result)
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
            ExtendedShapelessRecipeBuilder.builder(ModRecipes.CONVERSION.get(), result)
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
        vanillaSalvage(consumer, gear, main, mainCount, rodCount, null);
    }

    private static void vanillaSalvage(Consumer<IFinishedRecipe> consumer, IItemProvider gear, IItemProvider main, int mainCount, int rodCount, @Nullable IItemProvider secondary) {
        SalvagingRecipeBuilder builder = SalvagingRecipeBuilder.builder(gear).addResult(main, mainCount);
        if (secondary != null) {
            builder.addResult(secondary);
        }
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

    private void metals(Consumer<IFinishedRecipe> consumer, float smeltingXp, Metals metal) {
        if (metal.ore != null) {
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(metal.oreTag), metal.ingot, smeltingXp, 100)
                    .addCriterion("has_item", hasItem(metal.oreTag))
                    .build(consumer, SilentGear.getId(metal.name + "_ore_blasting"));
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(metal.oreTag), metal.ingot, smeltingXp, 200)
                    .addCriterion("has_item", hasItem(metal.oreTag))
                    .build(consumer, SilentGear.getId(metal.name + "_ore_smelting"));
        }

        if (metal.rawOre != null) {
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(metal.rawOre), metal.ingot, smeltingXp, 100)
                    .addCriterion("has_item", hasItem(metal.rawOre))
                    .build(consumer, SilentGear.getId(metal.name + "_raw_ore_blasting"));
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(metal.rawOre), metal.ingot, smeltingXp, 200)
                    .addCriterion("has_item", hasItem(metal.rawOre))
                    .build(consumer, SilentGear.getId(metal.name + "_raw_ore_smelting"));

            compressionRecipes(consumer, metal.rawOreBlock, metal.rawOre, null);
        }

        InventoryChangeTrigger.Instance hasIngot = hasItem(metal.ingotTag);

        if (metal.block != null) {
            compressionRecipes(consumer, metal.block, metal.ingot, metal.nugget);
        }

        if (metal.dustTag != null) {
            Ingredient dustOrChunks = metal.chunksTag != null
                    ? Ingredient.fromItemListStream(Stream.of(new Ingredient.TagList(metal.chunksTag), new Ingredient.TagList(metal.dustTag)))
                    : Ingredient.fromTag(metal.dustTag);
            CookingRecipeBuilder.blastingRecipe(dustOrChunks, metal.ingot, smeltingXp, 100)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, SilentGear.getId(metal.name + "_dust_blasting"));
            CookingRecipeBuilder.smeltingRecipe(dustOrChunks, metal.ingot, smeltingXp, 200)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer, SilentGear.getId(metal.name + "_dust_smelting"));
        }

        if (metal.dust != null) {
            damageGear(metal.dust, 1, 1)
                    .addIngredient(ModTags.Items.HAMMERS)
                    .addIngredient(metal.ingotTag)
                    .addCriterion("has_item", hasIngot)
                    .build(consumer);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private static class Metals {
        private final String name;
        private IItemProvider ore;
        private ITag<Item> oreTag;
        private IItemProvider rawOre;
        private IItemProvider rawOreBlock;
        private IItemProvider block;
        private ITag<Item> blockTag;
        private final IItemProvider ingot;
        private final ITag<Item> ingotTag;
        private IItemProvider nugget;
        private ITag<Item> nuggetTag;
        private IItemProvider dust;
        private ITag<Item> dustTag;
        private ITag<Item> chunksTag;

        public Metals(String name, IItemProvider ingot, ITag<Item> ingotTag) {
            this.name = name;
            this.ingot = ingot;
            this.ingotTag = ingotTag;
        }

        public Metals ore(IItemProvider item, ITag<Item> tag) {
            this.ore = item;
            this.oreTag = tag;
            return this;
        }

        public Metals ore(IItemProvider oreBlockItem, ITag<Item> oreTag, IItemProvider rawOre, IItemProvider rawOreBlock) {
            this.ore = oreBlockItem;
            this.oreTag = oreTag;
            this.rawOre = rawOre;
            this.rawOreBlock = rawOreBlock;
            return this;
        }

        public Metals block(IItemProvider item, ITag<Item> tag) {
            this.block = item;
            this.blockTag = tag;
            return this;
        }

        public Metals nugget(IItemProvider item, ITag<Item> tag) {
            this.nugget = item;
            this.nuggetTag = tag;
            return this;
        }

        public Metals dust(IItemProvider item, ITag<Item> tag) {
            this.dust = item;
            this.dustTag = tag;
            return this;
        }

        public Metals chunks(ITag<Item> tag) {
            this.chunksTag = tag;
            return this;
        }
    }
}

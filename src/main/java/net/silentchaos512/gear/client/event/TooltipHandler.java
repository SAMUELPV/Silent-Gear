package net.silentchaos512.gear.client.event;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.material.IMaterialCategory;
import net.silentchaos512.gear.api.part.MaterialGrade;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.api.stats.StatInstance;
import net.silentchaos512.gear.api.stats.StatModifierMap;
import net.silentchaos512.gear.api.traits.TraitInstance;
import net.silentchaos512.gear.api.util.StatGearKey;
import net.silentchaos512.gear.block.charger.ChargerTileEntity;
import net.silentchaos512.gear.block.grader.GraderTileEntity;
import net.silentchaos512.gear.client.KeyTracker;
import net.silentchaos512.gear.client.util.TextListBuilder;
import net.silentchaos512.gear.config.Config;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.gear.part.AbstractGearPart;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.gear.init.ModTags;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.gear.util.TextUtil;
import net.silentchaos512.lib.event.ClientTicks;
import net.silentchaos512.lib.util.TagUtils;
import net.silentchaos512.utils.Color;

import java.util.*;
import java.util.stream.Collectors;

public final class TooltipHandler {
    public static final TooltipHandler INSTANCE = new TooltipHandler();

    // Display a single trait and cycling through the list. Main problem with this is it affects
    // JEI's tooltip cache. When disabled, you can search for parts with certain traits.
    private static final boolean TRAIT_DISPLAY_CYCLE = false;

    static {
        assert TextFormatting.DARK_GRAY.getColor() != null;
        assert TextFormatting.GRAY.getColor() != null;
    }

    public static final Color MC_DARK_GRAY = new Color(TextFormatting.DARK_GRAY.getColor());
    public static final Color MC_GRAY = new Color(TextFormatting.GRAY.getColor());

    private TooltipHandler() {}

    @SubscribeEvent(receiveCanceled = true)
    public void onTooltip(ItemTooltipEvent event) {
        /*
        LoaderState state = Loader.instance().getLoaderState();
        if (state == LoaderState.INITIALIZATION || state == LoaderState.SERVER_ABOUT_TO_START || state == LoaderState.SERVER_STOPPING) {
            // Skip tooltips during block/item remapping
            // JEI tooltip caches are done in AVAILABLE, in-game is SERVER_STARTED
            return;
        }
        */

        ItemStack stack = event.getItemStack();

        if (TagUtils.containsSafe(ModTags.Items.GRADER_CATALYSTS, stack)) {
            onGraderCatalystTooltip(event);
        }
        if (TagUtils.containsSafe(ModTags.Items.STARLIGHT_CHARGER_CATALYSTS, stack)) {
            onStarlightChargerCatalystTooltip(event);
        }

        MaterialInstance material = MaterialInstance.from(stack);
        if (material != null) {
            onMaterialTooltip(event, stack, material);
            return;
        }

        PartData part = PartData.from(stack);
        if (part != null /*&& !part.isBlacklisted(stack)*/) {
            onPartTooltip(event, stack, part);
            return;
        }

        // Nerfed gear?
        if (Config.Common.isNerfedItem(stack.getItem())) {
            List<ITextComponent> toolTip = event.getToolTip();
            toolTip.add(Math.min(1, toolTip.size()), new TranslationTextComponent("misc.silentgear.poorlyMade").mergeStyle(TextFormatting.RED));
        }
    }

    private static void onGraderCatalystTooltip(ItemTooltipEvent event) {
        int tier = GraderTileEntity.getCatalystTier(event.getItemStack());
        event.getToolTip().add(TextUtil.withColor(TextUtil.misc("graderCatalyst", tier), Color.DARKORANGE));
    }

    private static void onStarlightChargerCatalystTooltip(ItemTooltipEvent event) {
        int tier = ChargerTileEntity.getStarlightChargerCatalystTier(event.getItemStack());
        event.getToolTip().add(TextUtil.withColor(TextUtil.misc("starlightChargerCataylst", tier), Color.REBECCAPURPLE));
    }

    private static void onMaterialTooltip(ItemTooltipEvent event, ItemStack stack, MaterialInstance material) {
        boolean keyHeld = KeyTracker.isDisplayStatsDown();

        if (keyHeld) {
            event.getToolTip().add(TextUtil.withColor(TextUtil.misc("tooltip.material"), Color.GOLD));
        } else {
            event.getToolTip().add(TextUtil.withColor(TextUtil.misc("tooltip.material"), Color.GOLD)
                    .append(new StringTextComponent(" ")
                            .append(TextUtil.withColor(TextUtil.keyBinding(KeyTracker.DISPLAY_STATS), TextFormatting.GRAY))));
        }

        if (event.getFlags().isAdvanced()) {
            event.getToolTip().add(new StringTextComponent("Material ID: " + material.getId()).mergeStyle(TextFormatting.DARK_GRAY));
            event.getToolTip().add(new StringTextComponent("Material data pack: " + material.get().getPackName()).mergeStyle(TextFormatting.DARK_GRAY));
        }

        if (keyHeld) {
            getGradeLine(event, material.getGrade());

            getMaterialCategoriesLine(material).ifPresent(t -> event.getToolTip().add(t));

            List<PartType> partTypes = new ArrayList<>(material.getPartTypes());
            if (!partTypes.isEmpty()) {
                int index = KeyTracker.getMaterialCycleIndex(partTypes.size());
                PartType partType = partTypes.get(index);
                event.getToolTip().add(buildPartTypeHeader(partTypes, partType));
                event.getToolTip().add(TextUtil.withColor(TextUtil.misc("tier", material.getTier(partType)), Color.DEEPSKYBLUE));

                getMaterialTraitLines(event, partType, material);

                event.getToolTip().add(new TranslationTextComponent("misc.silentgear.tooltip.stats").mergeStyle(TextFormatting.GOLD));
                getMaterialStatLines(event, partType, material);
            }
        } else {
            if (material.getGrade() != MaterialGrade.NONE) {
                getGradeLine(event, material.getGrade());
            }

            if (event.getFlags().isAdvanced()) {
                addJeiSearchTerms(event, material);
            }
        }
    }

    private static void addJeiSearchTerms(ItemTooltipEvent event, MaterialInstance material) {
        // Add search terms to allow advanced filtering in JEI (requires the
        // `SearchAdvancedTooltips` JEI config to be set)

        StringBuilder b = new StringBuilder();

        for (IMaterialCategory category : material.getCategories()) {
            b.append(category.getName()).append(" ");
        }

        Collection<String> traits = new HashSet<>();

        for (PartType partType : material.getPartTypes()) {
            b.append(partType.getDisplayName(0).getString()).append(" ");
            for (TraitInstance trait : material.getTraits(partType)) {
                traits.add(trait.getTrait().getDisplayName(0).getString());
            }
        }

        for (String str : traits) {
            b.append(str).append(" ");
        }

        event.getToolTip().add(new StringTextComponent(b.toString().toLowerCase(Locale.ROOT)).mergeStyle(TextFormatting.DARK_GRAY).mergeStyle(TextFormatting.ITALIC));
    }

    private static Optional<ITextComponent> getMaterialCategoriesLine(MaterialInstance material) {
        Collection<IMaterialCategory> categories = material.getCategories();
        if (!categories.isEmpty()) {
            ITextComponent text = new StringTextComponent(categories.stream().map(IMaterialCategory::getName).collect(Collectors.joining(", ")))
                    .mergeStyle(TextFormatting.ITALIC);
            return Optional.of(TextUtil.misc("materialCategories", text));
        }
        return Optional.empty();
    }

    private static ITextComponent buildPartTypeHeader(Collection<PartType> types, PartType selectedType) {
        IFormattableTextComponent ret = new StringTextComponent("| ").mergeStyle(TextFormatting.GRAY);
        for (PartType type : types) {
            Color color = type == selectedType ? Color.AQUAMARINE : MC_DARK_GRAY;
            ITextComponent text = TextUtil.withColor(type.getDisplayName(-1), color);
            ret.append(text).appendString(" | ");
        }

        ITextComponent keyHint = TextUtil.misc("tooltip.material.keyHint",
                TextUtil.withColor(TextUtil.keyBinding(KeyTracker.CYCLE_BACK), Color.AQUAMARINE),
                TextUtil.withColor(TextUtil.keyBinding(KeyTracker.CYCLE_NEXT), Color.AQUAMARINE));
        return ret.append(keyHint);
    }

    private static void onPartTooltip(ItemTooltipEvent event, ItemStack stack, PartData part) {
        // Type, tier
        event.getToolTip().add(TextUtil.withColor(part.getType().getDisplayName(part.getTier()), Color.AQUAMARINE));

        if (event.getFlags().isAdvanced() && KeyTracker.isControlDown()) {
            event.getToolTip().add(new StringTextComponent("* Part ID: " + part.getId()).mergeStyle(TextFormatting.DARK_GRAY));
            event.getToolTip().add(new StringTextComponent("* Part data pack: " + part.get().getPackName()).mergeStyle(TextFormatting.DARK_GRAY));
        }

        // Traits
        List<TraitInstance> traits = new ArrayList<>();
        for (TraitInstance traitInstance : part.getTraits()) {
            if (traitInstance.getTrait().showInTooltip(event.getFlags())) {
                traits.add(traitInstance);
            }
        }

        int numTraits = traits.size();
        int traitIndex = getTraitDisplayIndex(numTraits);
        int i = 0;
        for (TraitInstance inst : traits) {
            if (traitIndex < 0 || traitIndex == i) {
                inst.addInformation(event.getToolTip(), event.getFlags());
            }
            ++i;
        }

        // Stats
        if (KeyTracker.isControlDown()) {
            event.getToolTip().add(new TranslationTextComponent("misc.silentgear.tooltip.stats")
                    .mergeStyle(TextFormatting.GOLD)
                    .append(new StringTextComponent(" (Silent Gear)")
                            .mergeStyle(TextFormatting.RESET)
                            .mergeStyle(TextFormatting.ITALIC)));
            getPartStatLines(event, stack, part);
        } else {
            event.getToolTip().add(new TranslationTextComponent("misc.silentgear.tooltip.ctrlForStats").mergeStyle(TextFormatting.GOLD));
        }

        // Gear type blacklist?
        if (part.get() instanceof AbstractGearPart) {
            List<GearType> blacklist = ((AbstractGearPart) part.get()).getBlacklistedGearTypes();
            if (!blacklist.isEmpty()) {
                int index = (ClientTicks.ticksInGame() / 20) % blacklist.size();
                GearType gearType = blacklist.get(index);
                ITextComponent blacklistedType = gearType.getDisplayName();
                event.getToolTip().add(new TranslationTextComponent("misc.silentgear.tooltip.blacklist", blacklistedType).mergeStyle(TextFormatting.RED));
            }
        }
    }

    private static int getTraitDisplayIndex(int numTraits) {
        if (!TRAIT_DISPLAY_CYCLE || KeyTracker.isControlDown() || numTraits == 0)
            return -1;
        return ClientTicks.ticksInGame() / 20 % numTraits;
    }

    private static void getGradeLine(ItemTooltipEvent event, MaterialGrade grade) {
        ITextComponent text = TextUtil.withColor(grade.getDisplayName(), Color.DEEPSKYBLUE);
        event.getToolTip().add(new TranslationTextComponent("part.silentgear.gradeOnPart", text));
    }

    private static void getMaterialTraitLines(ItemTooltipEvent event, PartType partType, MaterialInstance material) {
        Collection<TraitInstance> traits = material.getTraits(partType);
        if (traits.isEmpty()) return;

        IFormattableTextComponent header = TextUtil.misc("tooltip.traits").mergeStyle(TextFormatting.GOLD);
        if (!KeyTracker.isDisplayTraitsDown()) {
            IFormattableTextComponent keyHint = TextUtil.withColor(TextUtil.keyBinding(KeyTracker.DISPLAY_TRAITS), Color.AQUAMARINE);
            header.appendString(" ").append(keyHint);
        }
        event.getToolTip().add(header);

        TextListBuilder builder = new TextListBuilder();

        for (TraitInstance trait : traits) {
            builder.add(trait.getDisplayName());

            // Trait description and conditions
            if (event.getFlags().isAdvanced() || KeyTracker.isDisplayTraitsDown()) {
                builder.indent();
                builder.add(trait.getTrait().getDescription(trait.getLevel()).mergeStyle(TextFormatting.DARK_GRAY));
                if (!trait.getConditions().isEmpty()) {
                    builder.add(TextUtil.withColor(trait.getConditionsText(), TextFormatting.DARK_GRAY));
                }
                builder.unindent();
            }
        }

        event.getToolTip().addAll(builder.build());
    }

    private static void getPartStatLines(ItemTooltipEvent event, ItemStack stack, PartData part) {
        GearType gearType = getPartGearType(part);
        TextListBuilder builder = new TextListBuilder();

        List<ItemStat> relevantStats = new ArrayList<>(part.getGearType().getRelevantStats());
        if (part.getGearType().isArmor() && relevantStats.contains(ItemStats.DURABILITY)) {
            int index = relevantStats.indexOf(ItemStats.DURABILITY);
            relevantStats.remove(ItemStats.DURABILITY);
            relevantStats.add(index, ItemStats.ARMOR_DURABILITY);
        }

        for (ItemStat stat : relevantStats) {
            Collection<StatInstance> modifiers = new ArrayList<>();
            for (StatInstance mod : part.getStatModifiers(StatGearKey.of(stat, gearType), ItemStack.EMPTY)) {
                if (mod.getOp() == StatInstance.Operation.AVG) {
                    float computed = stat.compute(Collections.singleton(mod));
                    modifiers.add(StatInstance.of(computed, StatInstance.Operation.AVG, mod.getKey()));
                } else {
                    modifiers.add(mod);
                }
            }
            getStatTooltipLine(event, part.getType(), stat, modifiers).ifPresent(builder::add);
        }
        event.getToolTip().addAll(builder.build());
    }

    private static GearType getPartGearType(PartData part) {
        if (part.getItem().getItem() instanceof CompoundPartItem) {
            GearType gearType = ((CompoundPartItem) part.getItem().getItem()).getGearType();

            if (gearType.isGear()) {
                return gearType;
            }
        }
        return GearType.ALL;
    }

    private static void getMaterialStatLines(ItemTooltipEvent event, PartType partType, MaterialInstance material) {
        TextListBuilder builder = new TextListBuilder();

        for (ItemStat stat : ItemStats.allStatsOrdered()) {
            Collection<StatInstance> modsAll = material.getStatModifiers(partType, StatGearKey.of(stat, GearType.ALL));
            Optional<IFormattableTextComponent> head = getStatTooltipLine(event, partType, stat, modsAll);
            boolean headPresent = head.isPresent();
            builder.add(head.orElseGet(() -> TextUtil.withColor(stat.getDisplayName(), stat.getNameColor())));

            builder.indent();

            int subCount = 0;
            List<StatGearKey> keysForStat = material.get().getStatKeys(material, partType).stream()
                    .filter(key -> key.getStat().equals(stat))
                    .collect(Collectors.toList());

            for (StatGearKey key : keysForStat) {
                if (key.getGearType() != GearType.ALL) {
                    ItemStat stat1 = ItemStats.get(key.getStat());

                    if (stat1 != null) {
                        Collection<StatInstance> mods = material.getStatModifiers(partType, key);
                        Optional<IFormattableTextComponent> line = getSubStatTooltipLine(event, partType, stat1, key.getGearType(), mods);

                        if (line.isPresent()) {
                            builder.add(line.get());
                            ++subCount;
                        }
                    }
                }
            }

            if (subCount == 0 && !headPresent) {
                builder.removeLast();
            }

            builder.unindent();
        }

        event.getToolTip().addAll(builder.build());
    }

    private static Optional<IFormattableTextComponent> getStatTooltipLine(ItemTooltipEvent event, PartType partType, ItemStat stat, Collection<StatInstance> modifiers) {
        if (!modifiers.isEmpty()) {
            StatInstance inst = stat.computeForDisplay(0, GearType.ALL, modifiers);
            if (inst.shouldList(partType, stat, event.getFlags().isAdvanced())) {
                boolean isZero = inst.getValue() == 0;
                Color nameColor = isZero ? MC_DARK_GRAY : stat.getNameColor();
                Color statColor = isZero ? MC_DARK_GRAY : Color.WHITE;

                IFormattableTextComponent nameStr = TextUtil.withColor(stat.getDisplayName(), nameColor);
                int decimalPlaces = stat.isDisplayAsInt() && inst.getOp() != StatInstance.Operation.MUL1 && inst.getOp() != StatInstance.Operation.MUL2 ? 0 : 2;
                IFormattableTextComponent statListText = TextUtil.withColor(StatModifierMap.formatText(modifiers, stat, decimalPlaces), statColor);

                // Harvest level hints
                IFormattableTextComponent textWithAdditions = stat == ItemStats.HARVEST_LEVEL && modifiers.size() == 1
                        ? harvestLevelWithHint(statListText, inst.getValue())
                        : statListText;

                return Optional.of(new TranslationTextComponent("stat.silentgear.displayFormat", nameStr, textWithAdditions));
            }
        }

        return Optional.empty();
    }

    private static Optional<IFormattableTextComponent> getSubStatTooltipLine(ItemTooltipEvent event, PartType partType, ItemStat stat, GearType gearType, Collection<StatInstance> modifiers) {
        if (!modifiers.isEmpty()) {
            StatInstance inst = stat.computeForDisplay(0, gearType, modifiers);
            if (inst.shouldList(partType, stat, event.getFlags().isAdvanced())) {
                boolean isZero = inst.getValue() == 0;
                Color color = isZero ? MC_DARK_GRAY : Color.WHITE;

                IFormattableTextComponent nameStr = TextUtil.withColor(gearType.getDisplayName().deepCopy(), color);
                int decimalPlaces = stat.isDisplayAsInt() && inst.getOp() != StatInstance.Operation.MUL1 && inst.getOp() != StatInstance.Operation.MUL2 ? 0 : 2;
                IFormattableTextComponent statListText = TextUtil.withColor(StatModifierMap.formatText(modifiers, stat, decimalPlaces), color);

                // Harvest level hints
                IFormattableTextComponent textWithAdditions = stat == ItemStats.HARVEST_LEVEL && modifiers.size() == 1
                        ? harvestLevelWithHint(statListText, inst.getValue())
                        : statListText;

                return Optional.of(new TranslationTextComponent("stat.silentgear.displayFormat", nameStr, textWithAdditions));
            }
        }

        return Optional.empty();
    }

    public static IFormattableTextComponent harvestLevelWithHint(IFormattableTextComponent statValueText, float statValue) {
        String key = "misc.silentgear.harvestLevel." + Math.round(statValue);
        if (I18n.hasKey(key)) {
            return statValueText.append(TextUtil.misc("spaceBrackets", new TranslationTextComponent(key)));
        }
        return statValueText;
    }
}

package net.silentchaos512.gear.api.stats;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.lib.util.Lazy;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Stats used by all gear types
 */
public final class ItemStats {
    static final List<ItemStat> STATS_IN_ORDER = new ArrayList<>();

    public static final Lazy<IForgeRegistry<ItemStat>> REGISTRY = Lazy.of(() -> new RegistryBuilder<ItemStat>()
            .setType(ItemStat.class)
            .setName(SilentGear.getId("stat"))
            .create());

    // Generic
    public static final ItemStat DURABILITY = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.STEELBLUE, new ItemStat.Properties()
            .displayAsInt()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat ARMOR_DURABILITY = new ItemStat(0f, 0f, Integer.MAX_VALUE / 16, Color.STEELBLUE, new ItemStat.Properties()
            .displayAsInt()
            .displayFormat(ItemStat.DisplayFormat.MULTIPLIER)
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat REPAIR_EFFICIENCY = new ItemStat(1f, 0f, 1000f, Color.STEELBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.PERCENTAGE)
            .affectedByGrades(false)
    );
    public static final ItemStat REPAIR_VALUE = new ItemStat(1f, 0f, 1000f, Color.STEELBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.PERCENTAGE)
            .affectedByGrades(false)
    );
    public static final ItemStat ENCHANTABILITY = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.STEELBLUE, new ItemStat.Properties()
            .displayAsInt()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat CHARGEABILITY = new ItemStat(1f, 0f, Integer.MAX_VALUE, Color.STEELBLUE, new ItemStat.Properties()
            .affectedByGrades(false)
            .hidden()
    );
    public static final ItemStat RARITY = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.STEELBLUE, new ItemStat.Properties()
            .displayAsInt()
            .affectedByGrades(false)
            .hidden()
    );

    // Harvesting Tools
    public static final ItemStat HARVEST_LEVEL = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.SEAGREEN, new ItemStat.Properties()
            .defaultOp(StatInstance.Operation.MAX)
            .displayAsInt()
            .affectedByGrades(false)
    );
    public static final ItemStat HARVEST_SPEED = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.SEAGREEN, new ItemStat.Properties()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat REACH_DISTANCE = new ItemStat(0f, -100f, 100f, Color.SEAGREEN, new ItemStat.Properties()
            .affectedByGrades(false)
    );

    // Melee Weapons
    public static final ItemStat MELEE_DAMAGE = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.SANDYBROWN, new ItemStat.Properties()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat MAGIC_DAMAGE = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.SANDYBROWN, new ItemStat.Properties()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat ATTACK_SPEED = new ItemStat(0f, -3.9f, 4f, Color.SANDYBROWN, new ItemStat.Properties()
            .affectedByGrades(false)
    );
    public static final ItemStat ATTACK_REACH = new ItemStat(3f, 0f, 100f, Color.SANDYBROWN, new ItemStat.Properties()
            .baseValue(3f)
            .affectedByGrades(false)
    );

    // Ranged Weapons
    public static final ItemStat RANGED_DAMAGE = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.SKYBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.MULTIPLIER)
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat RANGED_SPEED = new ItemStat(0f, -10f, 10f, Color.SKYBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.MULTIPLIER)
            .affectedByGrades(false)
    );
    public static final ItemStat PROJECTILE_SPEED = new ItemStat(1f, 0f, Integer.MAX_VALUE, Color.SKYBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.MULTIPLIER)
            .affectedByGrades(false)
            .synergyApplies()
    );
    public static final ItemStat PROJECTILE_ACCURACY = new ItemStat(1f, 0f, 10000f, Color.SKYBLUE, new ItemStat.Properties()
            .displayFormat(ItemStat.DisplayFormat.PERCENTAGE)
            .affectedByGrades(false)
    );

    // Armor
    public static final ItemStat ARMOR = new SplitItemStat(0f, 0f, Integer.MAX_VALUE, Color.VIOLET,
            ImmutableMap.of(
                    GearType.HELMET, 3f,
                    GearType.CHESTPLATE, 8f,
                    GearType.LEGGINGS, 6f,
                    GearType.BOOTS, 3f
            ),
            new ItemStat.Properties()
                    .affectedByGrades(true)
                    .synergyApplies()
    );
    public static final ItemStat ARMOR_TOUGHNESS = new EvenSplitItemStat(0f, 0f, Integer.MAX_VALUE, Color.VIOLET,
            4,
            new ItemStat.Properties()
                    .affectedByGrades(true)
                    .synergyApplies()
    );
    public static final ItemStat KNOCKBACK_RESISTANCE = new ItemStat(0f, 0f, Integer.MAX_VALUE, Color.VIOLET, new ItemStat.Properties()
            .affectedByGrades(true)
            .synergyApplies()
    );
    public static final ItemStat MAGIC_ARMOR = new EvenSplitItemStat(0f, 0f, Integer.MAX_VALUE, Color.VIOLET,
            4,
            new ItemStat.Properties()
                    .affectedByGrades(true)
                    .synergyApplies()
    );

    private ItemStats() {}

    /**
     * Returns a collection of all created stats in a pre-determined order.
     *
     * @return Ordered stats list
     */
    public static Collection<ItemStat> allStatsOrdered() {
        return Collections.unmodifiableList(STATS_IN_ORDER);
    }

    public static Collection<ItemStat> allStatsOrderedExcluding(Collection<ItemStat> exclude) {
        Collection<ItemStat> ret = new ArrayList<>(STATS_IN_ORDER);
        ret.removeIf(exclude::contains);
        return ret;
    }

    /**
     * Gets a stat by name. If the namespace is omitted, the silentgear namespace is used.
     *
     * @param name The stat name
     * @return The stat, or null if it does not exist
     */
    @Nullable
    public static ItemStat byName(String name) {
        ResourceLocation id = SilentGear.getIdWithDefaultNamespace(name);
        return id != null ? REGISTRY.get().getValue(id) : null;
    }

    @Nullable
    public static ItemStat get(IItemStat stat) {
        return REGISTRY.get().getValue(stat.getStatId());
    }

    // region Registry creation - other mods should not call these methods!

    public static void createRegistry(RegistryEvent.NewRegistry event) {
        REGISTRY.get();
    }

    public static void registerStats(RegistryEvent.Register<ItemStat> event) {
        // TODO: Replace with DeferredRegister?
        register(event.getRegistry(), DURABILITY, "durability");
        register(event.getRegistry(), ARMOR_DURABILITY, "armor_durability");
        register(event.getRegistry(), REPAIR_EFFICIENCY, "repair_efficiency");
        register(event.getRegistry(), REPAIR_VALUE, "repair_value");
        register(event.getRegistry(), ENCHANTABILITY, "enchantability");
        register(event.getRegistry(), CHARGEABILITY, "chargeability");
        register(event.getRegistry(), RARITY, "rarity");
        register(event.getRegistry(), HARVEST_LEVEL, "harvest_level");
        register(event.getRegistry(), HARVEST_SPEED, "harvest_speed");
        register(event.getRegistry(), REACH_DISTANCE, "reach_distance");
        register(event.getRegistry(), MELEE_DAMAGE, "melee_damage");
        register(event.getRegistry(), MAGIC_DAMAGE, "magic_damage");
        register(event.getRegistry(), ATTACK_SPEED, "attack_speed");
        register(event.getRegistry(), ATTACK_REACH, "attack_reach");
        register(event.getRegistry(), RANGED_DAMAGE, "ranged_damage");
        register(event.getRegistry(), RANGED_SPEED, "ranged_speed");
        register(event.getRegistry(), PROJECTILE_ACCURACY, "projectile_accuracy");
        register(event.getRegistry(), PROJECTILE_SPEED, "projectile_speed");
        register(event.getRegistry(), ARMOR, "armor");
        register(event.getRegistry(), ARMOR_TOUGHNESS, "armor_toughness");
        register(event.getRegistry(), KNOCKBACK_RESISTANCE, "knockback_resistance");
        register(event.getRegistry(), MAGIC_ARMOR, "magic_armor");
    }

    private static void register(IForgeRegistry<ItemStat> registry, ItemStat stat, String name) {
        registry.register(stat.setRegistryName(SilentGear.getId(name)));
    }

    //endregion
}

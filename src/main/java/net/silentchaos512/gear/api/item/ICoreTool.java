package net.silentchaos512.gear.api.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface ICoreTool extends ICoreItem {
    Set<ItemStat> RELEVANT_STATS = ImmutableSet.of(
            ItemStats.DURABILITY,
            ItemStats.REPAIR_EFFICIENCY,
            ItemStats.ENCHANTABILITY,
            ItemStats.HARVEST_LEVEL,
            ItemStats.HARVEST_SPEED,
            ItemStats.MELEE_DAMAGE,
            ItemStats.ATTACK_SPEED
    );

    Set<ItemStat> EXCLUDED_STATS = ImmutableSet.of(
            ItemStats.ARMOR_DURABILITY,
            ItemStats.REPAIR_VALUE,
            ItemStats.RANGED_DAMAGE,
            ItemStats.RANGED_SPEED,
            ItemStats.PROJECTILE_ACCURACY,
            ItemStats.PROJECTILE_SPEED,
            ItemStats.ARMOR,
            ItemStats.ARMOR_TOUGHNESS,
            ItemStats.MAGIC_ARMOR,
            ItemStats.KNOCKBACK_RESISTANCE
    );

    @Override
    default Set<ItemStat> getRelevantStats(@Nonnull ItemStack stack) {
        return RELEVANT_STATS;
    }

    @Override
    default Set<ItemStat> getExcludedStats(ItemStack stack) {
        return EXCLUDED_STATS;
    }

    @Override
    default boolean isValidSlot(String slot) {
        return EquipmentSlotType.MAINHAND.getName().equalsIgnoreCase(slot)
                || EquipmentSlotType.OFFHAND.getName().equalsIgnoreCase(slot);
    }

    @Override
    default Collection<PartType> getRequiredParts() {
        return ImmutableList.of(PartType.MAIN, PartType.ROD);
    }

    /**
     * The base damage done to the item when breaking a block, not considering enchantments
     *
     * @param gear  The item
     * @param world The world
     * @param state The block being broken
     * @param pos   The position of the block
     * @return The amount of damage done (durability lost) to the item
     */
    default int getDamageOnBlockBreak(ItemStack gear, World world, BlockState state, BlockPos pos) {
        return state.getMaterial() != Material.LEAVES && state.getBlockHardness(world, pos) > 0 ? 1 : 0;
    }

    /**
     * The base damage done to the item when attacking an entity
     *
     * @param gear     The item
     * @param target   The entity being attacked
     * @param attacker The entity attacking the target
     * @return The amount of damage done (durability lost) to the item
     */
    default int getDamageOnHitEntity(ItemStack gear, LivingEntity target, LivingEntity attacker) {
        return 2;
    }

    @Override
    default boolean hasTexturesFor(PartType partType) {
        return partType != PartType.BOWSTRING
                && partType != PartType.FLETCHING
                && partType != PartType.ADORNMENT
                && partType != PartType.LINING;
    }
}

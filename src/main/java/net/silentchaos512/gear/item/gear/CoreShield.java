package net.silentchaos512.gear.item.gear;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.client.ColorHandlers;
import net.silentchaos512.gear.client.util.GearClientHelper;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CoreShield extends ShieldItem implements ICoreItem {
    private static final float DURABILITY_MULTI = 337f / 15f;

    private static final Set<ItemStat> RELEVANT_STATS = ImmutableSet.of(
            ItemStats.DURABILITY,
            ItemStats.ENCHANTABILITY
    );

    private static final Set<ItemStat> EXCLUDED_STATS = ImmutableSet.of(
            ItemStats.HARVEST_LEVEL,
            ItemStats.HARVEST_SPEED,
            ItemStats.REACH_DISTANCE,
            ItemStats.MELEE_DAMAGE,
            ItemStats.MAGIC_DAMAGE,
            ItemStats.ATTACK_SPEED,
            ItemStats.ATTACK_REACH,
            ItemStats.RANGED_DAMAGE,
            ItemStats.RANGED_SPEED
    );

    public CoreShield() {
        super(GearHelper.getBuilder(null).maxDamage(100));
    }

    @Override
    public GearType getGearType() {
        return GearType.SHIELD;
    }

    @Override
    public boolean isValidSlot(String slot) {
        return EquipmentSlotType.MAINHAND.getName().equalsIgnoreCase(slot)
                || EquipmentSlotType.OFFHAND.getName().equalsIgnoreCase(slot);
    }

    @Override
    public Set<ItemStat> getRelevantStats(ItemStack stack) {
        return RELEVANT_STATS;
    }


    @Override
    public Set<ItemStat> getExcludedStats(ItemStack stack) {
        return EXCLUDED_STATS;
    }

    @Override
    public ItemStat getDurabilityStat() {
        return ItemStats.ARMOR_DURABILITY;
    }

    @Override
    public float getRepairModifier(ItemStack stack) {
        return DURABILITY_MULTI;
    }

    @Override
    public Collection<PartType> getRequiredParts() {
        return ImmutableList.of(PartType.MAIN, PartType.ROD);
    }

    @Override
    public IItemColor getItemColors() {
        return ColorHandlers::getShieldColor;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        GearClientHelper.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return GearHelper.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return GearHelper.getIsRepairable(toRepair, repair);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return GearData.getStatInt(stack, ItemStats.ENCHANTABILITY);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return GearHelper.getDisplayName(stack);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, GearHelper.calcDamageClamped(stack, damage));
        if (GearHelper.isBroken(stack)) {
            GearData.recalculateStats(stack, null);
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Math.round(DURABILITY_MULTI * GearData.getStat(stack, ItemStats.ARMOR_DURABILITY));
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return GearHelper.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return GearHelper.getRarity(stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return GearClientHelper.hasEffect(stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return GearHelper.hitEntity(stack, target, attacker);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        GearHelper.fillItemGroup(this, group, items);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return GearHelper.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        GearHelper.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return GearClientHelper.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return !GearHelper.isBroken(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return GearHelper.isBroken(stack) ? UseAction.NONE : super.getUseAction(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (GearHelper.isBroken(stack)) {
            return ActionResult.resultPass(stack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean hasTexturesFor(PartType partType) {
        // FIXME: Shields not compatible with new model system
        return false;
    }
}

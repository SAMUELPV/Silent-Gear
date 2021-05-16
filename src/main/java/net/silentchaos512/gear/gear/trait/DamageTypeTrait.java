package net.silentchaos512.gear.gear.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.traits.ITraitSerializer;
import net.silentchaos512.gear.api.traits.TraitActionContext;

public final class DamageTypeTrait extends SimpleTrait {
    private static final ResourceLocation SERIALIZER_ID = SilentGear.getId("damage_type_trait");
    public static final ITraitSerializer<DamageTypeTrait> SERIALIZER = new Serializer<>(
            SERIALIZER_ID,
            DamageTypeTrait::new,
            (trait, json) -> {
                trait.damageType = JSONUtils.getString(json, "damage_type", trait.getId().getPath());
                trait.damageBonus = JSONUtils.getFloat(json, "damage_bonus", 0);
            },
            (trait, buffer) -> {
                trait.damageType = buffer.readString();
                trait.damageBonus = buffer.readFloat();
            },
            (trait, buffer) -> {
                buffer.writeString(trait.damageType);
                buffer.writeFloat(trait.damageBonus);
            }
    );

    private String damageType;
    private float damageBonus;

    private DamageTypeTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }

    @Override
    public float onAttackEntity(TraitActionContext context, LivingEntity target, float baseValue) {
        if (target.isEntityUndead() && "holy".equals(damageType)
                || target.isImmuneToFire() && "chilled".equals(damageType)) {
            return baseValue + damageBonus * context.getTraitLevel();
        }

        return baseValue;
    }
}

package com.nitron.ordnance.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.nitron.ordnance.cca.ScytheComponent;
import com.nitron.ordnance.common.entities.projectiles.ScytheProjectileEntity;
import com.nitron.ordnance.common.entities.projectiles.SunflareEntity;
import com.nitron.ordnance.compat.EnchancementCompat;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ScytheItem extends SwordItem implements Vanishable {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage + toolMaterial.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567"), "Weapon modifier", 0.5, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getMainHandStack().isOf(ModItems.SCYTHE)){
            ScytheProjectileEntity scythe = createScythe(world, user, user.getMainHandStack());
            world.spawnEntity(scythe);
            scythe.setStack(user.getMainHandStack());
            world.playSoundFromEntity(null, scythe, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return super.use(world, user, hand);
    }

    public @NotNull ScytheProjectileEntity createScythe(World world, LivingEntity user, ItemStack stack) {
        ScytheProjectileEntity trident = new ScytheProjectileEntity(world, user, stack);
        trident.setOwner(user);
        trident.setVelocity(user, user.getPitch(), user.getYaw(), 10F, 1.5F, 2F);
        trident.updatePosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
        return trident;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}

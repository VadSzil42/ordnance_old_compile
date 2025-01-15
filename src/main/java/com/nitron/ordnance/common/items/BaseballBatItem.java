package com.nitron.ordnance.common.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BaseballBatItem extends SwordItem implements Equipment {
    public BaseballBatItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.MAINHAND;
    }

    /*@Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        entity.addVelocity(0, 1, 0);
        entity.velocityModified = true;
        entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1F, 0.8F);
        return super.useOnEntity(stack, user, entity, hand);
    }*/ //There is no reason to turn this on unless you want to be silly, gay, and fun.
}

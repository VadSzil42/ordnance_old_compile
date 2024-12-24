package com.nitron.ordnance.common.items.common;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
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

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(hand == Hand.MAIN_HAND){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
        else {
            return TypedActionResult.pass(itemStack);
        }
    }
}

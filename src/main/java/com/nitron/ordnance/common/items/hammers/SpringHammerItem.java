package com.nitron.ordnance.common.items.hammers;

import com.nitron.ordnance.Ordnance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;

public class SpringHammerItem extends SwordItem {
    public SpringHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(!attacker.isOnGround()){
            attacker.getWorld().playSound(null, target.getBlockPos(), Ordnance.spring_hammer_squeak, SoundCategory.PLAYERS, 1f, 1f);
        }
        return super.postHit(stack, target, attacker);
    }
}

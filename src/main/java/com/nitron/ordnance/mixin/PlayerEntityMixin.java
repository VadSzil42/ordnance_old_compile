package com.nitron.ordnance.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArgs(method = "disableShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"))
    private void ordnance$axeDisablesBaseballBat(Args args) {
        if (this.getMainHandStack().isOf(ModItems.BASEBALL_BAT) && getActiveItem().isOf(ModItems.BASEBALL_BAT)) {
            args.set(0, ModItems.BASEBALL_BAT);
        }
    }

    /*@ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    public float ordnance$highAirSpeed(float original) {
        if(this.hasStatusEffect(StatusEffects.GLOWING))
            return (float) (original * 3.5); // for future plans... /nefarious

        return original;
    }*/

}

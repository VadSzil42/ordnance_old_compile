package com.nitron.ordnance.mixin;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class EntityMixin {
    /*@Inject(method = "damage", at = @At("HEAD"))
    public void ordnance$silly(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        Entity sourceAttacker = source.getAttacker();
        LivingEntity entity = (LivingEntity) (Object) this;

        if(sourceAttacker instanceof PlayerEntity player){
            ItemStack heldItem = player.getMainHandStack();
            if(heldItem.isOf(ModItems.BASEBALL_BAT) && !entity.isOnGround()){
                Vec3d lookDirection = player.getRotationVec(1.0F).normalize();
                double value = player.isOnGround() ? 3.0 : 6.0;
                entity.setVelocity(lookDirection.x * value, lookDirection.y * value, lookDirection.z * value);
                entity.velocityDirty = true;
                entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1F, 1.25F);
                if(entity.getWorld() instanceof ServerWorld world){
                    world.spawnParticles(Ordnance.HIT_BOOM, entity.getX(), entity.getY() + 0.5, entity.getZ(), 1, 0, 0, 0, 0);
                }
            }
        }
    }*/ //So... every block... every entity... get pelted
}

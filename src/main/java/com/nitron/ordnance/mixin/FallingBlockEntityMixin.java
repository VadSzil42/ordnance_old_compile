package com.nitron.ordnance.mixin;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow private BlockState block;

    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity player){
            ItemStack heldItem = player.getMainHandStack();
            if(heldItem.isOf(ModItems.BASEBALL_BAT)){
                Vec3d lookDirection = player.getRotationVec(1.0F).normalize();
                double value = player.isOnGround() ? 1.0 : 2.0;
                this.setVelocity(lookDirection.x * value, lookDirection.y * value, lookDirection.z * value);
                this.velocityDirty = true;
                this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1F, 1.25F);
                if(this.getWorld() instanceof ServerWorld world){
                    world.spawnParticles(Ordnance.HIT_BOOM, this.getX(), this.getY() + 0.5, this.getZ(), 1, 0, 0, 0, 0);
                }

                return true;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if(this.block.getBlock() == Blocks.ANVIL)
            player.damage(this.getWorld().getDamageSources().fallingAnvil(null), 10);
        super.onPlayerCollision(player);
    }
}

package com.nitron.ordnance.common.entities.projectiles;

import com.google.common.collect.Sets;
import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.client.OrdnanceDamageTypes;
import com.nitron.ordnance.registration.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class BulletEntity extends PersistentProjectileEntity {
    public BulletEntity(EntityType<? extends BulletEntity> entityType, World world) {
        super(ModEntities.BULLET, world);
    }

    public BulletEntity(World world, double x, double y, double z) {
        super(ModEntities.BULLET, x, y, z, world);
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        this.hasNoGravity();
        this.setNoGravity(true);
        if(this.getWorld() instanceof ServerWorld world){
            world.spawnParticles(Ordnance.BEAM, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 1);
        }
        if(this.age >= 200){
            this.discard();
        }
        if(this.isTouchingWater()){
            this.discard();
        }
        super.tick();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
       entityHitResult.getEntity().damage(getDamageSources().arrow(this, null), 8);
       this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.AIR);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_METAL_HIT;
    }
}

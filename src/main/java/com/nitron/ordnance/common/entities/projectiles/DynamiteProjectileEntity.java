package com.nitron.ordnance.common.entities.projectiles;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.registration.ModEntities;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamiteProjectileEntity extends ThrownItemEntity {

    public DynamiteProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.DYNAMITE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DYNAMITE;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.getWorld().isClient()){
            if(this.getWorld() instanceof ServerWorld serverWorld){
                boom();
            }
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    public void tick() {
        int maxAge = 20;
        if(this.getWorld() instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
        if(age > maxAge){
            if(this.getWorld() instanceof ServerWorld world){
                boom();
            }
        }
        super.tick();
    }

    private void boom(){
        this.getWorld().sendEntityStatus(this, (byte)3);
        BlockPos pos = this.getBlockPos();
        this.getWorld().createExplosion(
                null,
                null,
                null,
                pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                3,
                false,
                World.ExplosionSourceType.TNT
        );
        this.discard();
    }
}

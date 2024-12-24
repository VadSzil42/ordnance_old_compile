package com.nitron.ordnance.common.entities.projectiles;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.OrdnanceClient;
import com.nitron.ordnance.registration.ModEntities;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConfettiBombProjectileEntity extends ThrownItemEntity {

    public ConfettiBombProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ConfettiBombProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.CONFETTI_BOMB, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CONFETTI_BOMB;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.getWorld().isClient()){
            if(this.getWorld() instanceof ServerWorld serverWorld){
                confettiTime(serverWorld);
            }
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    public void tick() {
        int maxAge = 10;
        if(this.getWorld() instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
        if(age > maxAge){
            if(this.getWorld() instanceof ServerWorld world){
                confettiTime(world);
            }
        }
        super.tick();
    }

    private void confettiTime(ServerWorld world){
        world.spawnParticles(Ordnance.CONFETTI, this.getX(), this.getY(), this.getZ(), 1000, 0, 0, 0, 0.1);
        world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 2f, 1f);
        this.discard();
    }
}

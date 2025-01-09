package com.nitron.ordnance.common.entities.projectiles;

import com.nitron.ordnance.registration.ModEntities;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PhantasmEntity extends TridentEntity {
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(PhantasmEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private ItemStack phantasmStack;
    private boolean dealtDamage;
    public int returnTimer;

    public PhantasmEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.PHANTASM, world);
        this.phantasmStack = stack;
        this.setOwner(owner);
        if(this.phantasmStack == null){
            this.phantasmStack = new ItemStack(ModItems.PHANTASM);
        }
        this.noClip = true;
    }

    public PhantasmEntity(EntityType<PhantasmEntity> phantasmEntityEntityType, World world) {
        super(phantasmEntityEntityType, world);
        if (this.phantasmStack == null){
            this.phantasmStack = new ItemStack(ModItems.PHANTASM);
        }
        this.noClip = true;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ENCHANTED, false);
    }

    @Override
    public void tick() {
        if(this.getWorld() instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
        if(this.phantasmStack == null){
            this.phantasmStack = new ItemStack(ModItems.PHANTASM);
        }
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        if (age == 140) { // reverses direction after 7 seconds
            Vec3d reversedVelocity = this.getVelocity().multiply(-1);
            this.setVelocity(reversedVelocity.getX(), reversedVelocity.getY(), reversedVelocity.getZ(), 1.5F, 1.0F);
        }
        else if (age >= 280) { // returns after 14 seconds
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if (this.phantasmStack.getNbt() != null && this.phantasmStack.getOrCreateNbt().getBoolean("loyal") &&(this.dealtDamage || this.isNoClip()) && entity != null) {
            if (!this.isOwnerAlive()) {
                if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * 3, this.getZ());
                double d = 0.05 * 3;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                }
                ++this.returnTimer;
            }
        }
        super.tick();
    }


    public void setTridentStack(ItemStack stack){
        this.phantasmStack = stack;
    }


    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return this.phantasmStack.copy();
    }


    public boolean isEnchanted() {
        return (Boolean)this.dataTracker.get(ENCHANTED);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getAttackDamage(this.phantasmStack, livingEntity.getGroup());
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().trident(this, (Entity)(entity2 == null ? this : entity2));
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
        if (!entity.equals(entity2)) {
            if (entity.damage(damageSource, f)) {
                if (entity.getType() == EntityType.ENDERMAN) {
                    return;
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity2 = (LivingEntity) entity;
                    if (entity2 instanceof LivingEntity) {
                        EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
                        EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity2);
                    }

                    this.onHit(livingEntity2);
                }
            }
        }

        this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
        float g = 1.0F;
        if (this.getWorld() instanceof ServerWorld && this.getWorld().isThundering() && this.hasChanneling()) {
            BlockPos blockPos = entity.getBlockPos();
            if (this.getWorld().isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = (LightningEntity)EntityType.LIGHTNING_BOLT.create(this.getWorld());
                if (lightningEntity != null) {
                    lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                    lightningEntity.setChanneler(entity2 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity2 : null);
                    this.getWorld().spawnEntity(lightningEntity);
                    soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
                    g = 5.0F;
                }
            }
        }

        this.playSound(soundEvent, g, 1.0F);
    }


    @Override
    public void age() {
        if (this.pickupType != PickupPermission.ALLOWED) {
            super.age();
        }

    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

}

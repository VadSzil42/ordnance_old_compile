package com.nitron.ordnance.common.entities.projectiles;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.cca.ScytheComponent;
import com.nitron.ordnance.registration.ModEntities;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ScytheProjectileEntity extends PersistentProjectileEntity {
    private static final float DAMAGE = 5.0F;
    private static final TrackedData<Integer> OWNER;
    private static final TrackedData<Byte> FLAGS;
    private PlayerEntity playerOwner;
    private ItemStack stack;

    public ScytheProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.SCYTHE, world);
        this.setOwner(owner);
        this.stack = stack;
        if(this.stack == null){
            this.stack = new ItemStack(ModItems.SCYTHE);
        }
        this.setNoGravity(true);
    }

    public ScytheProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        if(this.stack == null){
            this.stack = new ItemStack(ModItems.SCYTHE);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(!this.hasHit()){
            PlayerEntity owner = this.getPlayerOwner();
            Entity hit = entityHitResult.getEntity();
            if(hit.damage(this.getWorld().getDamageSources().playerAttack(this.getPlayerOwner()), DAMAGE)){
                if(hit.getType() == EntityType.ENDERMAN){
                    return;
                }

                if(this.getPlayerOwner() != null){
                    this.setReturning(true);
                    this.setNoClip(true);
                    this.setHit(true);
                }
            }

            this.setVelocity(this.getVelocity().multiply(-1.0, 1.0, -1.0));
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.isReturning()){
            Direction direction = blockHitResult.getSide();
            this.setPosition(this.getPos().subtract(this.getVelocity()));
            switch (direction.getAxis()){
                case X -> this.setVelocity(this.getVelocity().multiply(-1.0, 1.0, 1.0));
                case Y -> this.setVelocity(this.getVelocity().multiply(1.0, -1.0, 1.0));
                case Z -> this.setVelocity(this.getVelocity().multiply(1.0, 1.0, -1.0));
            }

            //Play a swag sound here
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(hitResult.getType() != HitResult.Type.MISS){
            this.setReturning(true);
        }
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FLAGS, (byte) 0);
        this.dataTracker.startTracking(OWNER, 0);
        super.initDataTracker();
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT;
    }

    @Override
    public void tick() {
        this.inGround = false;
        PlayerEntity owner = this.getPlayerOwner();
        if(owner == null){
            this.discard();
            Ordnance.LOGGER.info("Deleted Scythe : Owner not valid");
        } else {
            Vec3d pos = this.getPos();
            Vec3d towards;
            double d;
            if(this.isReturning()){
                towards = owner.getEyePos();
                d = pos.distanceTo(towards);
                if(d < 2.0){
                    this.discard();
                    //ScytheComponent.get(owner).setThrown(false);
                    return;
                }

                double speed = Math.max(d / 48.0, 0.25);
                Vec3d vec3d = owner.getEyePos().subtract(this.getPos()).normalize().multiply(speed);
                this.setVelocity(this.getVelocity().multiply(0.75).add(vec3d));
            }

            if(this.age > 50 && !this.isReturning()){
                this.setReturning(true);
            }

            if(this.age > 400){
                this.discard();
                Ordnance.LOGGER.info("Deleted Scythe : Bitch too old");
            }
        }

        super.tick();
    }

    @Override
    protected ItemStack asItemStack() {
        return Items.AIR.getDefaultStack();
    }

    public void setStack(ItemStack stack){
        this.stack = stack;
    }

    public boolean isValidTarget(@Nullable Entity target) {
        PlayerEntity owner = this.getPlayerOwner();
        if (target == owner) {
            return false;
        } else {
            if (target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity)target;
                if (target.getType().getSpawnGroup() == SpawnGroup.MONSTER || target instanceof PlayerEntity) {
                    if (livingTarget.isDead()) {
                        return false;
                    } else if (target.isRemoved()) {
                        return false;
                    } else if (owner != null && target.isTeammate(owner)) {
                        return false;
                    } else if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
                        return false;
                    } else {
                        return target.canHit();
                    }
                }
            }
            return false;
        }
    }

    @Override
    protected @Nullable EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        EntityHitResult result = super.getEntityCollision(currentPosition.subtract(1.0, 2.0, 1.0), nextPosition.add(1.0, 2.0, 1.0));
        return result != null && this.isValidTarget(result.getEntity()) ? result : null ;
    }

    public boolean isReturning(){ return this.getScytheFlag(1); }

    public void setReturning(boolean returning){
        this.setScytheFlag(1, returning);
        this.setNoClip(returning);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    public @Nullable PlayerEntity getPlayerOwner(){
        if(this.playerOwner != null){
            return this.playerOwner;
        } else {
            int id = (Integer) this.dataTracker.get(OWNER);
            if(id != -1){
                Entity entity = this.getWorld().getEntityById(id);
                if(entity instanceof PlayerEntity player){
                    this.playerOwner = player;
                    return player;
                }
            }
            Ordnance.LOGGER.info("Deleted Scythe : Couldnt create owner");
            this.discard();
            return null;
        }
    }

    public boolean hasHit() { return this.getScytheFlag(2); }

    public void setHit(boolean hit) { this.setScytheFlag(2, hit); }

    private boolean getScytheFlag(int flag) {
        if (flag >= 0 && flag <= 8) {
            return ((Byte)this.dataTracker.get(FLAGS) >> flag & 1) == 1;
        } else {
            Ordnance.LOGGER.warn("Invalid scythe flags: " + flag + " for scythe " + this);
            return false;
        }
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity instanceof PlayerEntity player) {
            this.dataTracker.set(OWNER, player.getId());
        } else {
            this.dataTracker.set(OWNER, -1);
        }

        super.setOwner(entity);
    }

    private void setScytheFlag(int flag, boolean value) {
        if (flag >= 0 && flag <= 8) {
            if (value) {
                this.dataTracker.set(FLAGS, (byte)((Byte)this.dataTracker.get(FLAGS) | 1 << flag));
            } else {
                this.dataTracker.set(FLAGS, (byte)((Byte)this.dataTracker.get(FLAGS) & ~(1 << flag)));
            }

        } else {
            Ordnance.LOGGER.warn("Invalid scythe flags: " + flag + " for scythe " + this);
        }
    }

    static {
        OWNER = DataTracker.registerData(ScytheProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
        FLAGS = DataTracker.registerData(ScytheProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if(!this.stack.isEmpty()){
            nbt.put("scythe", this.stack.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("scythe")) {
            this.stack = ItemStack.fromNbt(nbt.getCompound("scythe"));
        }
    }
}

package com.nitron.ordnance.common.items;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.common.entities.projectiles.BulletEntity;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SixShooterItem extends Item {
    private static final String VALUE_KEY = "ammo";
    private static final int MAX_VALUE = 6;
    public SixShooterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt = user.getStackInHand(hand).getOrCreateNbt();
        int ammo = nbt.getInt(VALUE_KEY);
        if(ammo <= 0){
            nbt.putInt(VALUE_KEY, MAX_VALUE);
            user.getItemCooldownManager().set(this, 60);
            world.playSound(null, user.getBlockPos(), Ordnance.six_shooter_reload, SoundCategory.PLAYERS, 1f, 1f);
        } else {
            nbt.putInt(VALUE_KEY, ammo - 1);
            user.getItemCooldownManager().set(this, 10);
            BulletEntity dynamiteEntity = new BulletEntity(world, user.getX(), user.getY() + 1.5, user.getZ());
            dynamiteEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3F, 1.0F);
            world.spawnEntity(dynamiteEntity);
            world.playSound(null, user.getBlockPos(), Ordnance.six_shooter_shoot, SoundCategory.PLAYERS, 1f, 1f);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        world.playSoundFromEntity(null, player, Ordnance.freedom, SoundCategory.PLAYERS, 2, 1);
        if(player instanceof ServerPlayerEntity serverPlayer){
            Advancement advancement = serverPlayer.getServer().getAdvancementLoader().get(new Identifier("patriotic_master"));
            for(String criterion : serverPlayer.getAdvancementTracker().getProgress(advancement).getUnobtainedCriteria()){
                serverPlayer.getAdvancementTracker().grantCriterion(advancement, criterion);
            }
        }
        super.onCraft(stack, world, player);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int value = stack.getOrCreateNbt().getInt(VALUE_KEY);
        return (int) Math.round(13.0F * (float) value / MAX_VALUE);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xccde6a;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
}

package com.nitron.ordnance.mixin;

import com.nitron.ordnance.registration.ModItems;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void ordnance$onuse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if(!world.isClient && player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.BASEBALL_BAT)){
            FallingBlockEntity anvil = FallingBlockEntity.spawnFromBlock(world, pos, state);
            anvil.addVelocity(0, 1, 0);
            anvil.velocityModified = true;
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1F, 0.8F);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}

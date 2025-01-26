package com.nitron.ordnance.mixin;

import com.nitron.ordnance.registration.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
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

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "getSlipperiness", at = @At("HEAD"), cancellable = true)
    public void ordnance$use(CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(0.99F);
    } //you want that block of dirt to launch upwards for some reason? now you can. well done.

    @Inject(method = "getJumpVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    public void ordnance$1(CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(2F);
    }
}

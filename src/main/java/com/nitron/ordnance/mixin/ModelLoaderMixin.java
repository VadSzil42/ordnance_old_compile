package com.nitron.ordnance.mixin;

import com.nitron.ordnance.Ordnance;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addOrdnanceItemModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "sunflare_handheld", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "nebulon_handheld", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "spring_hammer_handheld", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "sunflare_handheld_flipped", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "nebulon_handheld_flipped", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "baseball_bat_handheld", "inventory"));
        this.addModel(new ModelIdentifier(Ordnance.MOD_ID, "baseball_bat_blocking", "inventory"));
    }
}
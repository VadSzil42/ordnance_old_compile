package com.nitron.ordnance.mixin;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useSunflareModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.SUNFLARE) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if(MinecraftClient.getInstance().player.isUsingItem()){
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "sunflare_handheld_flipped", "inventory"));
            } else{
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "sunflare_handheld", "inventory"));
            }
        }
        return value;
    }

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useNebularkModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.NEBULON) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if(MinecraftClient.getInstance().player.isUsingItem()){
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "nebulon_handheld_flipped", "inventory"));
            } else{
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "nebulon_handheld", "inventory"));
            }
        }
        return value;
    }

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useSpringHammerModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.SPRING_HAMMER) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "spring_hammer_handheld", "inventory"));
        }
        return value;
    }

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useBaseballBatModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.BASEBALL_BAT) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            if(MinecraftClient.getInstance().player.isUsingItem()){
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "baseball_bat_blocking", "inventory"));
            } else{
                return ((ItemRendererAccessor) this).ordnance$getModels().getModelManager().getModel(new ModelIdentifier(Ordnance.MOD_ID, "baseball_bat_handheld", "inventory"));
            }
        }
        return value;
    }
}
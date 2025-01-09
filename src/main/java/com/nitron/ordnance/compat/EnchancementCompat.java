package com.nitron.ordnance.compat;

import moriyashiine.enchancement.client.util.EnchancementClientUtil;
import moriyashiine.enchancement.common.component.entity.LeechComponent;
import moriyashiine.enchancement.common.component.entity.WarpComponent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EnchancementCompat {
    public static final boolean isEnchancementLoaded = FabricLoader.getInstance().isModLoaded("enchancement");

    public static boolean tryEnableEnchantments(LivingEntity user, ItemStack stack, TridentEntity trident) {
        if (isEnchancementLoaded) {
            LeechComponent.maybeSet(user, stack, trident);
            WarpComponent.maybeSet(user, stack, trident);
        }
        return isEnchancementLoaded;
    }

    public static boolean tryRenderLeechTrident(TridentEntity trident, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Model model, Identifier texture, int light, Runnable runnable) {
        if (isEnchancementLoaded) {
            CallbackInfo ci = new CallbackInfo("render", true); // no idea how this works lmfao
            EnchancementClientUtil.renderLeechTrident(trident, matrices, vertexConsumers, model, texture, light, runnable, ci);
            return ci.isCancelled();
        }
        return false;
    }
}

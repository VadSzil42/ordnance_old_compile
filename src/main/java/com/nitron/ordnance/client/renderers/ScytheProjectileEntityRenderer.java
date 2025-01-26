package com.nitron.ordnance.client.renderers;

import com.nitron.ordnance.common.entities.projectiles.ScytheProjectileEntity;
import com.nitron.ordnance.registration.ModItems;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class ScytheProjectileEntityRenderer extends EntityRenderer<ScytheProjectileEntity> {
    private static final ItemStack stack;
    private final ItemRenderer itemRenderer;

    public ScytheProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    public void render(ScytheProjectileEntity scythe, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(1.2F, 1.2F, 1.2F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F + MathHelper.lerp(tickDelta, scythe.prevYaw, scythe.getYaw())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F + MathHelper.lerp(tickDelta, scythe.prevPitch, scythe.getPitch())));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(((float)scythe.age + tickDelta) * -75.0F));
        this.itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, scythe.getWorld(), scythe.getId());
        matrices.pop();
        super.render(scythe, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ScytheProjectileEntity entity) {
        return null;
    }

    static {
        stack = ModItems.SCYTHE.getDefaultStack();
    }
}

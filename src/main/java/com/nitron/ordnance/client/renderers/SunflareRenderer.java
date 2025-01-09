package com.nitron.ordnance.client.renderers;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.client.models.SunflareModel;
import com.nitron.ordnance.common.entities.projectiles.SunflareEntity;
import com.nitron.ordnance.compat.EnchancementCompat;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.LightDebugRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SunflareRenderer extends EntityRenderer<SunflareEntity> {
    private static final Identifier TEXTURE = new Identifier(Ordnance.MOD_ID, "textures/entity/sunflare.png");
    private final SunflareModel model;

    public SunflareRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new SunflareModel(SunflareModel.getTexturedModelData().createModel());
    }

    @Override
    public Identifier getTexture(SunflareEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SunflareEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (EnchancementCompat.tryRenderLeechTrident(tridentEntity, matrixStack, vertexConsumerProvider, model, getTexture(tridentEntity), i,
                () -> super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i))) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, tridentEntity.prevYaw, tridentEntity.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, tridentEntity.prevPitch, tridentEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer1 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, RenderLayer.getEyes(TEXTURE), true, tridentEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer1, i, 0, 1.0F, 1.0F, 1.0F, 1.0F);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, RenderLayer.getEyes(TEXTURE), false, tridentEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

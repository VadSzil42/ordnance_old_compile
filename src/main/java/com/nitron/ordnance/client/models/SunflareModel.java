package com.nitron.ordnance.client.models;

import com.nitron.ordnance.common.entities.projectiles.SunflareEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SunflareModel extends EntityModel<SunflareEntity> {
	private final ModelPart body;
	private final ModelPart mirror;
	private final ModelPart bone;
	public SunflareModel(ModelPart root) {
		this.body = root.getChild("body");
		this.mirror = this.body.getChild("mirror");
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(4, 0).cuboid(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 31.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 3).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData mirror = body.addChild("mirror", ModelPartBuilder.create().uv(8, 3).cuboid(1.5F, -30.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 27.0F, 0.0F));

		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(4, 8).cuboid(-7.5F, -3.0F, 7.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 8).cuboid(-9.5F, -3.0F, 7.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-7.5F, -5.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-9.5F, -5.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-9.5F, -12.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-7.5F, -12.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-7.5F, -19.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-9.5F, -19.0F, 7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -8.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(SunflareEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
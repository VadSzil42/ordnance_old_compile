package com.nitron.ordnance.client.models;

import com.nitron.ordnance.common.entities.projectiles.PhantasmEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class PhantasmModel extends EntityModel<PhantasmEntity> {
	private final ModelPart body;
	private final ModelPart mirror;
	public PhantasmModel(ModelPart root) {
		this.body = root.getChild("body");
		this.mirror = this.body.getChild("mirror");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(4, 0).cuboid(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-1.5F, 3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 10).cuboid(0.5F, 3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(-1.5F, 25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 2).cuboid(0.5F, 25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 4).cuboid(0.5F, 23.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 6).cuboid(-1.5F, 23.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 3).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 3).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData mirror = body.addChild("mirror", ModelPartBuilder.create().uv(4, 8).cuboid(1.5F, -30.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 27.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(PhantasmEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
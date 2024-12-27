package com.nitron.ordnance.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class BeamParticle extends SpriteBillboardParticle {
    private static final Random RANDOM = Random.create();
    protected BeamParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
        this.maxAge = 50;
        this.scale = 1F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        float j = this.getSize(tickDelta);
        Vector3f Vec3f2 = new Vector3f();
        Vec3f2.rotate(camera.getRotation());
        Vec3f2.normalize(j);
        Vector3f[] vertices = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float particleSize = this.getSize(tickDelta);
        for (int i = 0; i < 4; i++) {
            Vector3f vertex = vertices[i];
            vertex.rotate(camera.getRotation());
            vertex.mul(particleSize);
            vertex.add(f, g, h);
        }
        int brightness = this.getBrightness(tickDelta);
        this.vertex(vertexConsumer, vertices[0], this.getMaxU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vertices[1], this.getMaxU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vertices[2], this.getMinU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vertices[3], this.getMinU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vertices[0], this.getMaxU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vertices[3], this.getMinU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vertices[2], this.getMinU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vertices[1], this.getMaxU(), this.getMinV(), brightness);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f position, float u, float v, int light) {
        vertexConsumer.vertex(position.x(), position.y(), position.z())
                .texture(u, v)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(light)
                .next();
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = this.alpha * (-(1/(float)maxAge) * age + 1);
        this.scale += 0.025F;
    }

    @Environment(EnvType.CLIENT)
    public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DefaultFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BeamParticle particle = new BeamParticle(clientWorld, d, e, f);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}

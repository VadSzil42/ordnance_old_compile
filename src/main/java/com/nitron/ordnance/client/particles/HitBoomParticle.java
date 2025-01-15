package com.nitron.ordnance.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;

public class HitBoomParticle extends SpriteBillboardParticle {
    private static final Random RANDOM = Random.create();
    protected HitBoomParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
        this.maxAge = 100;
        this.scale = 0.5F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = this.alpha * (-(1/(float)maxAge) * age + 1);
        this.scale += 0.025F;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            HitBoomParticle particle = new HitBoomParticle(clientWorld, d, e, f);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}

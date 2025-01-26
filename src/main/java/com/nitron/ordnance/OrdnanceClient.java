package com.nitron.ordnance;

import com.nitron.ordnance.client.particles.BeamParticle;
import com.nitron.ordnance.client.particles.ConfettiParticle;
import com.nitron.ordnance.client.particles.HitBoomParticle;
import com.nitron.ordnance.client.particles.ZeroSweepParticle;
import com.nitron.ordnance.client.renderers.*;
import com.nitron.ordnance.registration.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class OrdnanceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SUNFLARE, SunflareRenderer::new);
        EntityRendererRegistry.register(ModEntities.PHANTASM, PhantasmRenderer::new);
        EntityRendererRegistry.register(ModEntities.NEBULON, NebulonRenderer::new);
        EntityRendererRegistry.register(ModEntities.BULLET, BulletEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CONFETTI_BOMB, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.DYNAMITE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SCYTHE, ScytheProjectileEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(Ordnance.CONFETTI, ConfettiParticle.DefaultFactory::new);
        ParticleFactoryRegistry.getInstance().register(Ordnance.BEAM, BeamParticle.DefaultFactory::new);
        ParticleFactoryRegistry.getInstance().register(Ordnance.HIT_BOOM, HitBoomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Ordnance.SWEEP0, ZeroSweepParticle.Factory::new);
    }
}

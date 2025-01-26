package com.nitron.ordnance.cca;

import com.nitron.ordnance.Ordnance;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class OrdnanceComponents implements EntityComponentInitializer {
    public static final ComponentKey<ScytheComponent> SCYTHE = ComponentRegistry.getOrCreate(Ordnance.id("scythe"), ScytheComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SCYTHE, ScytheComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}

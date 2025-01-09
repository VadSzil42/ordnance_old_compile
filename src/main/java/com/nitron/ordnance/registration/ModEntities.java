package com.nitron.ordnance.registration;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.common.entities.projectiles.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<SunflareEntity> SUNFLARE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "sunflare"),
            FabricEntityTypeBuilder.<SunflareEntity>create(SpawnGroup.MISC, SunflareEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F)) // Adjust dimensions as needed
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .fireImmune()
                    .build()
    );

    public static final EntityType<NebulonEntity> PHANTASM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "phantasm"),
            FabricEntityTypeBuilder.<NebulonEntity>create(SpawnGroup.MISC, NebulonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .fireImmune()
                    .build()
    );

    public static final EntityType<NebulonEntity> NEBULON = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "nebulon"),
            FabricEntityTypeBuilder.<NebulonEntity>create(SpawnGroup.MISC, NebulonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .fireImmune()
                    .build()
    );

    public static final EntityType<BulletEntity> BULLET = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "bullet"),
            FabricEntityTypeBuilder.<BulletEntity>create(SpawnGroup.MISC, BulletEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .fireImmune()
                    .build()
    );

    public static final EntityType<ConfettiBombProjectileEntity> CONFETTI_BOMB = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "confetti_bomb"), FabricEntityTypeBuilder.<ConfettiBombProjectileEntity>create(SpawnGroup.MISC, ConfettiBombProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(.25f, .25f)).build());

    public static final EntityType<DynamiteProjectileEntity> DYNAMITE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Ordnance.MOD_ID, "dynamite"), FabricEntityTypeBuilder.<DynamiteProjectileEntity>create(SpawnGroup.MISC, DynamiteProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(.25f, .25f)).build());


    public static void init(){
    }
}

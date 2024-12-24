package com.nitron.ordnance.client;

import com.nitron.ordnance.Ordnance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class OrdnanceDamageTypes {
    public static final RegistryKey<DamageType> BEAMED = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Ordnance.id("beamed"));

    public static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource((world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key)));
    }
}

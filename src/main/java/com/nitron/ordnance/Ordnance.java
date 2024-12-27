package com.nitron.ordnance;

import com.nitron.ordnance.common.items.hammers.SpringHammerItem;
import com.nitron.ordnance.registration.ModEntities;
import com.nitron.ordnance.registration.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ordnance implements ModInitializer {
	public static final String MOD_ID = "ordnance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Identifier BASTION_TREASURE_CHEST_LOOT_TABLE_ID = new Identifier("minecraft", "chests/bastion_treasure");
	private static final Identifier BASTION_OTHER_CHEST_LOOT_TABLE_ID = new Identifier("minecraft", "chests/bastion_other");

	public static DefaultParticleType CONFETTI;
	public static DefaultParticleType BEAM;

	@Override
	public void onInitialize() {
		ModEntities.init();
		ModItems.init();

		UniformLootNumberProvider lootTableRange = UniformLootNumberProvider.create(1, 1);
		LootCondition chanceLootCondition = RandomChanceLootCondition.builder(60).build();
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
			if (BASTION_TREASURE_CHEST_LOOT_TABLE_ID.equals(identifier)) {
				LootPool lootPool = LootPool.builder()
						.rolls(lootTableRange)
						.conditionally(chanceLootCondition)
						.with(ItemEntry.builder(ModItems.IGNITED_IDOL).build()).build();

				builder.pool(lootPool);
			}
		}));

		UniformLootNumberProvider lootTableRange1 = UniformLootNumberProvider.create(0, 1);
		LootCondition chanceLootCondition1 = RandomChanceLootCondition.builder(10).build();
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
			if (BASTION_OTHER_CHEST_LOOT_TABLE_ID.equals(identifier)) {
				LootPool lootPool = LootPool.builder()
						.rolls(lootTableRange1)
						.conditionally(chanceLootCondition1)
						.with(ItemEntry.builder(ModItems.ANCIENT_UPGRADE_TEMPLATE).build()).build();
				builder.pool(lootPool);
			}
		}));

		CONFETTI = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Ordnance.MOD_ID, "confetti"), FabricParticleTypes.simple(true));
		BEAM = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Ordnance.MOD_ID, "beam"), FabricParticleTypes.simple(true));
	}

	public static final SoundEvent spring_hammer_squeak = registerSound("spring_hammer_squeak");
	public static final SoundEvent six_shooter_reload = registerSound("six_shooter_reload");
	public static final SoundEvent six_shooter_shoot = registerSound("six_shooter_shoot");
	public static final SoundEvent freedom = registerSound("freedom");

	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.of(MOD_ID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

}
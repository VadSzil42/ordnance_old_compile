package com.nitron.ordnance.registration;

import com.nitron.ordnance.Ordnance;
import com.nitron.ordnance.common.items.bombs.ConfettiBomb;
import com.nitron.ordnance.common.items.bombs.Dynamite;
import com.nitron.ordnance.common.items.common.BaseballBatItem;
import com.nitron.ordnance.common.items.firearms.SixShooterItem;
import com.nitron.ordnance.common.items.hammers.SpringHammerItem;
import com.nitron.ordnance.common.items.smithing.AncientUpgradeTemplateItem;
import com.nitron.ordnance.common.items.tridents.NebulonItem;
import com.nitron.ordnance.common.items.tridents.SunflareItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item SUNFLARE = registerItem("sunflare", new SunflareItem(new FabricItemSettings().maxCount(1).maxDamage(250)));
    public static Item NEBULON = registerItem("nebulon", new NebulonItem(new FabricItemSettings().maxCount(1).maxDamage(250)));
    public static Item IGNITED_IDOL = registerItem("ignited_idol", new Item(new FabricItemSettings()));
    public static Item SPRING_HAMMER = registerItem("spring_hammer", new SpringHammerItem(ToolMaterials.DIAMOND, 4, -2.4F, new FabricItemSettings()));
    public static Item ANCIENT_UPGRADE_TEMPLATE = registerItem("ancient_upgrade_template", AncientUpgradeTemplateItem.createAncientUpgrade());
    public static Item SIX_SHOOTER = registerItem("six-shooter", new SixShooterItem(new FabricItemSettings().maxCount(1)));
    public static Item BASEBALL_BAT = registerItem("baseball_bat", new BaseballBatItem(ToolMaterials.WOOD, 5, -2.4F, new FabricItemSettings().maxCount(1)));

    public static Item CONFETTI_BOMB = registerItem("confetti_bomb", new ConfettiBomb(new FabricItemSettings().maxCount(16)));
    public static Item DYNAMITE = registerItem("dynamite", new Dynamite(new FabricItemSettings().maxCount(16)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Ordnance.MOD_ID, name), item);
    }

    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addAfter(Items.TRIDENT, SUNFLARE);
            entries.addAfter(Items.TRIDENT, NEBULON);
            entries.addBefore(Items.SNOWBALL, CONFETTI_BOMB);
            entries.addBefore(Items.SNOWBALL, DYNAMITE);
            entries.addBefore(Items.SHIELD, SPRING_HAMMER);
            entries.addBefore(Items.SHIELD, BASEBALL_BAT);
            entries.addBefore(Items.BOW, SIX_SHOOTER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addBefore(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ANCIENT_UPGRADE_TEMPLATE);
            entries.addBefore(Items.WHEAT, IGNITED_IDOL);
        });
    }
}

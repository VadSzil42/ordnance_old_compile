package com.nitron.ordnance.common.items;

import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class AncientUpgradeTemplateItem extends SmithingTemplateItem {
    private static final String MOD_ID = "ordnance";
    private static final Formatting TITLE_FORMAT = Formatting.GRAY;
    private static final Formatting DESCRIPTION_FORMAT = Formatting.BLUE;
    private static final String TRANSLATION_KEYS = Util.createTranslationKey("item", new Identifier(MOD_ID,"ancient_upgrade_template"));
    private static final Text INGREDIENT_TEXT = Text.translatable(Util.createTranslationKey("item", new Identifier(MOD_ID,"smithing_template.ingredient")))
            .formatted(TITLE_FORMAT);
    private static final Text ANCIENT_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", new Identifier(MOD_ID,"smithing_template.ancient_upgrade.apply_to")))
            .formatted(DESCRIPTION_FORMAT);
    private static final Text ANCIENT_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", new Identifier(MOD_ID,"smithing_template.ancient_upgrade.ingredients")))
            .formatted(DESCRIPTION_FORMAT);
    private static final Text ANCIENT_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade", new Identifier(MOD_ID,"ancient_upgrade")))
            .formatted(TITLE_FORMAT);
    private static final Text ANCIENT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", new Identifier(MOD_ID,"ancient_template.base_slot_description")));
    private static final Text ANCIENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", new Identifier(MOD_ID,"ancient_template.additions_slot_description")));

    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = new Identifier("item/empty_armor_slot_boots");
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE = new Identifier("item/empty_slot_hoe");
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE = new Identifier("item/empty_slot_axe");
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = new Identifier("item/empty_slot_sword");
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = new Identifier("item/empty_slot_shovel");
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = new Identifier("item/empty_slot_pickaxe");
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = new Identifier("item/empty_slot_ingot");

    public AncientUpgradeTemplateItem(
            Text appliesToText,
            Text ingredientsText,
            Text titleText,
            Text baseSlotDescriptionText,
            Text additionsSlotDescriptionText,
            List<Identifier> emptyBaseSlotTextures,
            List<Identifier> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures);
    }

    public static SmithingTemplateItem createAncientUpgrade() {
        return new SmithingTemplateItem(
                ANCIENT_UPGRADE_APPLIES_TO_TEXT,
                ANCIENT_UPGRADE_INGREDIENTS_TEXT,
                ANCIENT_UPGRADE_TEXT,
                ANCIENT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                ANCIENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getAncientUpgradeEmptyBaseSlotTextures(),
                getAncientUpgradeEmptyAdditionsSlotTextures()
        );
    }

    private static List<Identifier> getAncientUpgradeEmptyBaseSlotTextures() {
        return List.of(
                EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
                EMPTY_SLOT_SWORD_TEXTURE,
                EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
                EMPTY_SLOT_PICKAXE_TEXTURE,
                EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
                EMPTY_SLOT_AXE_TEXTURE,
                EMPTY_ARMOR_SLOT_BOOTS_TEXTURE,
                EMPTY_SLOT_HOE_TEXTURE,
                EMPTY_SLOT_SHOVEL_TEXTURE
        );
    }
    private static List<Identifier> getAncientUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE);
    }
}

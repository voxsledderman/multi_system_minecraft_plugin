package com.xdd.serverPlugin.Utils;

import com.xdd.serverPlugin.permissions.CampPerms;
import com.xdd.serverPlugin.records.UuidNick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class SpecificItems {

    public static class FromGUI {

        public static ItemStack closeEqItem() {
            return ItemUtil.addMetaToItemStack(GuiUtils.redCrossIcon.clone(),
                    MiniMessage.miniMessage().deserialize("<red>Zamknij menu"), null);
        }

        public static ItemStack yourCampsItem() {
            return ItemUtil.addMetaToItemStack(GuiUtils.homeIcon.clone(), MiniMessage.miniMessage().deserialize("<green>Menu Twoich Obozów <reset><glyph:home>"), List.of(
                    MiniMessage.miniMessage().deserialize("<gold>Co umożliwia:"),
                    Component.text(" "),
                    MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Teleport do twoich obozów"),
                    MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zobaczenie listy obozów")
            ));
        }
        public static ItemStack upgradeCampItem() {
            return ItemUtil.addMetaToItemStack(GuiUtils.upArrow.clone(), MiniMessage.miniMessage().deserialize("<green>Menu Ulepszeń Obozu <reset><glyph:level_up>"), List.of(
                    MiniMessage.miniMessage().deserialize("<gold>Możliwe ulepszenia:"),
                    Component.text(" "),
                    MiniMessage.miniMessage().deserialize("<glyph:update-14><white>Zwiększenie poziomu wyspy"),
                    MiniMessage.miniMessage().deserialize("<glyph:update-14><white>Zwiększenie limitu farm"),
                    MiniMessage.miniMessage().deserialize("<glyph:update-14><white>Ulepszenie pomocników")

            ));
        }
        public static ItemStack settingsCampItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.settingsIcon, MiniMessage.miniMessage().deserialize("<green>Menu Ustawień Obozu <reset><glyph:settings>"), List.of(
                            MiniMessage.miniMessage().deserialize("<gold>Możliwe ustawienia:"),
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zmiana permisji obozu"),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zmiana permisji członka"),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Dodanie członka obozu")
            ));
        }
        public static ItemStack permTrueItem(String perm, UuidNick uuidNick){
            return ItemUtil.addMetaToItemStack(GuiUtils.checkIcon.clone(), MiniMessage.miniMessage().deserialize("<green>Permisja aktywna <reset><glyph:check>"),
                    CampPerms.getLoreFromPerm(perm, uuidNick));
        }
        public static ItemStack permFalseItem(String perm, UuidNick uuidNick){
            return ItemUtil.addMetaToItemStack(GuiUtils.cross0Icon.clone(), MiniMessage.miniMessage().deserialize("<red>Permisja nieaktywna"),
            CampPerms.getLoreFromPerm(perm, uuidNick));
        }
        public static ItemStack returnItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.roundedLeftArrow.clone(), MiniMessage.miniMessage().deserialize("Powrót"), null);
        }
        public static ItemStack infoItem(List<Component> lore){
            return ItemUtil.addMetaToItemStack(GuiUtils.infoIcon.clone(), MiniMessage.miniMessage().deserialize("<gold>Podpowiadajka <glyph:small-star>"), lore);
        }
        public static ItemStack humanItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.humanIcon.clone(), MiniMessage.miniMessage().deserialize("<blue>Dodaj profil członka <glyph:update-13>"), null);
        }
        public static ItemStack blueXItem(Component displayName, List<Component> lore){
            return ItemUtil.addMetaToItemStack(GuiUtils.blueCrossIcon.clone(), displayName, lore);
        }
        public static ItemStack refreshItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.refreshIcon.clone(), MiniMessage.miniMessage().deserialize("Odśwież liste obozów"), null);
        }
        public static ItemStack nextPageItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.nextPageIcon.clone(), MiniMessage.miniMessage().deserialize("Przejdź na następną strone"),null);
        }
        public static ItemStack prevPageItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.prevPageIcon.clone(), MiniMessage.miniMessage().deserialize("Przejdź na poprzednią strone"),null);
        }
        public static ItemStack farmLvlUpItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.farmLevelUpIcon.clone(), MiniMessage.miniMessage().deserialize("<blue>Zwiększ limity farm"),
                    List.of(
                            Component.text("Ulepszenie pozwala:").color(TextColor.color(153, 102, 204)),
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>zwiększyć limit bloków w farmie"),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>zwiększyć limit żyjących zwierząt")
                            ));
        }
        public static ItemStack campLvlUpItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.campLevelUpIcon.clone(), MiniMessage.miniMessage().deserialize("<blue>Ulepsz swój obóz"),
                    List.of(
                            Component.text("Ulepszenie zapewnia:").color(TextColor.color(153, 102, 204)),
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>zwiększenie granic obozu"),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>odblokowanie nowych funkcji")));
        }
        public static ItemStack helpersLvlUpItem(){
            return ItemUtil.addMetaToItemStack(GuiUtils.helperLevelUpIcon.clone(), MiniMessage.miniMessage().deserialize("<blue>Ulepsz swoich pracowników"),
                    List.of(
                            Component.text("Ulepszenie zapewnia:").color(TextColor.color(153, 102, 204)),
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>wydajniejszych pracowników"),
                            MiniMessage.miniMessage().deserialize("<glyph:thunder><white>tańszych pracowników")
                           ));
        }
    }
}


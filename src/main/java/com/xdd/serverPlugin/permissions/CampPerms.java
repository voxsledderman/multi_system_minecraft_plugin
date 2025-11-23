package com.xdd.serverPlugin.permissions;

import com.xdd.serverPlugin.records.UuidNick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class CampPerms {

    public static class GlobalPerms {
        public static final String SETTINGS_OPEN_DOOR_PERM = "open.door";
        public static final String KILL_MONSTERS = "kill.monsters";
        public static final String KILL_MOBS = "kill.mobs";
        public static final String PICKUP_ITEMS = "pickup.items";
        public static final String SEND_VISIT_REQUEST = "send.visit.request";
        public static final String REDSTONE_BLOCKS_INTERACTION = "allow.redstone.interaction";
    }

    public static class SpecialPerms {
        public static final String BUILD_DESTROY_BLOCKS = "allow.building";
        public static final String OPEN_CHESTS = "allow.open.chest";
        public static final String ALLOW_PLAYING_ALONE = "allow.playing.alone";
    }

    public static List<String> getDefaultGlobalPerms(){
       List<String> perms =  new ArrayList<>();
       perms.add(GlobalPerms.SEND_VISIT_REQUEST);
       perms.add(GlobalPerms.KILL_MONSTERS);

       return perms;
    }
    public static List<Component> getLoreFromPerm(String perm, UuidNick uuidNick){
        switch (perm){
            case GlobalPerms.KILL_MONSTERS-> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zadawanie obrażen potworom"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")
                        );
            }
            case GlobalPerms.SEND_VISIT_REQUEST -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Wysyłanie prośb o wizytę"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")
                );
            }
            case GlobalPerms.SETTINGS_OPEN_DOOR_PERM -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Otwieranie drzwi"),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zamykanie drzwi"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")
                );
            }
            case GlobalPerms.PICKUP_ITEMS -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Wyrzucanie itemów"),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Podnoszenie itemów"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")
                );
            }
            case GlobalPerms.REDSTONE_BLOCKS_INTERACTION -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Interakcje z mechaniznami"),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>(dźwignie, płytki itp.)"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")

                );
            }
            case GlobalPerms.KILL_MOBS -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Zadawanie obrażeń zwierzętą"),
                        MiniMessage.miniMessage().deserialize("<gray>aktywna dla każdego!")
                        );
            }
            case SpecialPerms.ALLOW_PLAYING_ALONE -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Przebywanie na terenie obozu"),
                        MiniMessage.miniMessage().deserialize("<white>gdy właściciel jest offline!"),
                        MiniMessage.miniMessage().deserialize("<gray>permisja gracza: " + "<white>" + uuidNick.nick()
                ));
            }
            case SpecialPerms.OPEN_CHESTS -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Otwieranie różnego typu"),
                        MiniMessage.miniMessage().deserialize("<white>skrzynek i beczek!"),
                        MiniMessage.miniMessage().deserialize("<gray>permisja gracza: " + "<white>" + uuidNick.nick()
                ));
            }
            case SpecialPerms.BUILD_DESTROY_BLOCKS -> {
                return List.of(
                        MiniMessage.miniMessage().deserialize("<gold>Pozwala na:"),
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<glyph:thunder><white>Stawianie i niszczenie bloków"),
                        MiniMessage.miniMessage().deserialize("<gray>permisja gracza: " + "<white>" + uuidNick.nick())
                );
            }

        }
        return new ArrayList<>();
    }
}

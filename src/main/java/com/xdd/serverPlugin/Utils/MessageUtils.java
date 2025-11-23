package com.xdd.serverPlugin.Utils;

import com.xdd.serverPlugin.ServerPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void sendToPlayerAndConsole(Player player, Component component) {
        player.sendMessage(component);
        String plainText = PlainTextComponentSerializer.plainText().serialize(component);
        ServerPlugin.getInstance().getLogger().info(plainText);
    }
}

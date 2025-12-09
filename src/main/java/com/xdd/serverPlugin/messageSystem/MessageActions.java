package com.xdd.serverPlugin.messageSystem;

import org.bukkit.entity.Player;

public class MessageActions {

    public static void sendMessage(Player sender, Player receiver, String[] strings){
        sender.sendMessage(sender.getName() + " → :" + receiver.getName() + buildMessage(strings));
    }

    private static String buildMessage(String[] strings){
        StringBuilder content = new StringBuilder();

        for(String string : strings){
            content.append(" ").append(string);
        }
        return content.toString();
    }
}

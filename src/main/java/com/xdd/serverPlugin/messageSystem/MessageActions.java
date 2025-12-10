package com.xdd.serverPlugin.messageSystem;

import com.xdd.serverPlugin.Utils.Sounds;
import org.bukkit.entity.Player;

public class MessageActions {

    public static void sendMessage(Player sender, Player receiver, String[] strings){
        String msg = buildMessage(strings);
        sender.sendMessage("%s → %s: %s".formatted(sender.getName(), receiver.getName(), msg));
        receiver.sendMessage("%s → %s: %s".formatted(sender.getName(), receiver.getName(), msg));

        Sounds.playMsgSend(sender);
        Sounds.playMsgReceive(receiver);
    }

    private static String buildMessage(String[] strings){
        StringBuilder content = new StringBuilder();

        for(String string : strings){
            content.append(" ").append(string);
        }
        return content.toString();
    }
}

package com.xdd.serverPlugin.tittle;

import com.xdd.serverPlugin.ServerPlugin;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class TitleManager {
    private final Map<UUID, TitleSender> titleSenderMap = new HashMap<>();
    private final ServerPlugin pluginInstance = ServerPlugin.getInstance();


    boolean isHigherPriorityDisplayed(TitleSender titleSender){
        TitleSender curentTitleSender = titleSenderMap.get(titleSender.getReceiverUUID());
        return curentTitleSender != null && curentTitleSender.getPriorityLevel() <= titleSender.getPriorityLevel();
    }
    public void registerTitle(TitleSender titleSender){
        titleSenderMap.put(titleSender.getReceiverUUID(), titleSender);
    }
    public void removeTitle(TitleSender titleSender){
        titleSenderMap.remove(titleSender.getReceiverUUID());
    }
}

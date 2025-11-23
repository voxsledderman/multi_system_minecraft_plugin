package com.xdd.serverPlugin.tittle;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public abstract class TitleSender {

    private final UUID receiverUUID;
    private final int priorityLevel;
    private final Component mainTitle;
    private final Component subTitle;
    private final Title.Times times;
    @Nullable private final Sound sound;
    private final TitleManager manager;

    protected TitleSender(UUID receiverUUID, int priorityLevel, Component mainTitle, Component subTitle, Title.Times times, @Nullable Sound sound, TitleManager manager) {
        if(priorityLevel < 1 || priorityLevel > 10){
            throw new IllegalArgumentException("Ustawiono niewłaściwy priorytet dla tytułu! Wspierany zakres: (1-10)");
        }

        this.receiverUUID = receiverUUID;
        this.priorityLevel = priorityLevel;
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.times = times;
        this.sound = sound;
        this.manager = manager;
    }

    protected TitleSender(UUID receiverUUID, int priorityLevel, Component mainTitle,
                          Title.Times times, @Nullable Sound sound, TitleManager manager) {
        this(receiverUUID, priorityLevel, mainTitle, null, times, sound, manager);
    }


     public void sendTitle(){
       if(manager.isHigherPriorityDisplayed(this)) return;
       Player player =  Bukkit.getPlayer(receiverUUID);
       if(player == null) return;
       if(sound != null) player.playSound(player, sound,0.85f,1f);
       manager.registerTitle(this);

       titleLogic(player);
     }
    protected abstract void titleLogic(Player player);

}

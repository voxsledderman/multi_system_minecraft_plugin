package com.xdd.serverPlugin.tittle.subclasses;

import com.xdd.serverPlugin.tittle.TitleManager;
import com.xdd.serverPlugin.tittle.TitleSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class InfoTitle extends TitleSender {


    public InfoTitle(UUID receiverUUID, int priorityLevel, Component mainTitle, Component subTitle,
                        Title.Times times, @Nullable Sound sound, TitleManager manager) {
        super(receiverUUID, priorityLevel, mainTitle, subTitle, times, sound, manager);
    }

    @Override
    protected void titleLogic(Player player) {
        player.showTitle(Title.title(getMainTitle(), getSubTitle(), getTimes()));
        getManager().removeTitle(this);
    }
}

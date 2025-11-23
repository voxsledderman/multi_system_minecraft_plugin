package com.xdd.serverPlugin.service;

import com.xdd.serverPlugin.cache.StoppableManager;
import com.xdd.serverPlugin.locations.ServerLocations;
import com.xdd.serverPlugin.tittle.TitleManager;
import com.xdd.serverPlugin.tittle.subclasses.CountdownTitle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

public class SpawnTeleportService {
    private final StoppableManager stoppableManager;
    private final TitleManager titleManager;

    public SpawnTeleportService(StoppableManager stoppableManager, TitleManager titleManager) {
        this.stoppableManager = stoppableManager;
        this.titleManager = titleManager;
    }

    public void teleportWithCountdown(Player player, int secondsDelay) {
        UUID uuid = player.getUniqueId();
        CountdownTitle countdownTitle = new CountdownTitle(uuid,
                Component.text("Teleportowanie...").color(TextColor.color(0,255,0)),
                net.kyori.adventure.title.Title.Times.times(Duration.ofMillis(0), Duration.ofSeconds(2), Duration.ofMillis(0)),
                null, secondsDelay, net.kyori.adventure.title.Title.title(Component.text("Przeteleportowano!")
                        .color(TextColor.color(0,255,0)), Component.text(""),
                Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2000), Duration.ofMillis(500))),
                titleManager, () -> {
            if(player.isOnline()) {
                ServerLocations.SPAWN.teleport(player, true);
            }
            stoppableManager.unregisterStoppable(uuid);
        });

        countdownTitle.sendTitle();
        stoppableManager.registerStoppable(uuid, countdownTitle);
    }
}


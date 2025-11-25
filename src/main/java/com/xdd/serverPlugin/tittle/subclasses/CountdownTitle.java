package com.xdd.serverPlugin.tittle.subclasses;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cache.StoppableManager;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.interfaces.Stoppable;
import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.tittle.CountdownTittleRunnable;
import com.xdd.serverPlugin.tittle.TitleManager;
import com.xdd.serverPlugin.tittle.TitleSender;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

@Getter
public class CountdownTitle extends TitleSender implements Stoppable {
    private final int countdownTime;
    private BukkitTask task;
    private final Title endTitle;
    private final Runnable onFinish;
    private static final int COUNTDOWN_PRIORITY = 10;

    public CountdownTitle(UUID receiverUUID, Component mainTitle, Title.Times times, @Nullable Sound sound, int countdownTime,
                         Title endTitle, TitleManager manager, Runnable onFinish) {
        super(receiverUUID, COUNTDOWN_PRIORITY, mainTitle, times, sound, manager);
        this.countdownTime = countdownTime;
        this.endTitle = endTitle;
        this.onFinish = onFinish;


    }

    @Override
    protected void titleLogic(Player player) {
        CountdownTittleRunnable countdownTittleRunnable = new CountdownTittleRunnable(
                player, endTitle, getMainTitle(), countdownTime, getTimes(), this
        );
         task = countdownTittleRunnable.runTaskTimer(getManager().getPluginInstance(), 0, 20);

    }

    @Override
    public void stop(@Nullable Component msg) {
        if(task != null && !task.isCancelled()) {
            task.cancel();
        }
        getManager().removeTitle(this);
        Player player = Bukkit.getPlayer(getReceiverUUID());
        if(player == null) return;
        player.showTitle(Title.title(Component.text(""), Component.text(" "), Title.Times.times(
                Duration.ofMillis(10),Duration.ofMillis(10),Duration.ofMillis(10))));

        if(msg != null) {
            player.sendMessage(msg);
        }
    }

    @Override
    public boolean isStopped() {
        return task.isCancelled();
    }

    public static void doCampCountdown(Player player, Camp camp){
        final ServerPlugin plugin = ServerPlugin.getInstance();
        final StoppableManager stoppableManager = plugin.getStoppableManager();
        int seconds = 3;

        if(player.hasPermission(Perms.BYPASS_TP_COUNTING)) seconds = 0;

        CountdownTitle countdownTitle = new CountdownTitle(player.getUniqueId(), Component.text("Teleportowanie...")
                .color(TextColor.color(0x45FF4A)),
                net.kyori.adventure.title.Title.Times.times(Duration.ofMillis(0), Duration.ofSeconds(2), Duration.ofMillis(0)),
                null, seconds, net.kyori.adventure.title.Title.title(Component.text("Przeteleportowano!").color(TextColor.color(0,255,0)), Component.text(""),
                Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2000), Duration.ofMillis(500))), plugin.getTitleManager(),
                () -> {
                    if(player.isOnline()){
                        player.teleport(camp.getSpawnLocation());
                        stoppableManager.unregisterStoppable(player.getUniqueId());

                        if(player.hasPermission(Perms.IGNORE_CAMP_BORDER)){}
                        camp.recalculateWorldBorder();
                        player.setWorldBorder(camp.getCurrentBorder());
                        camp.spawnNPCs();

                        // TODO: Sprawdzenie przez spawnem czy juz w obozie są jakieś npc
                    }
                });

        countdownTitle.sendTitle();
        stoppableManager.registerStoppable(player.getUniqueId(), countdownTitle);

    }
}

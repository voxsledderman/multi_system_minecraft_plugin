package com.xdd.serverPlugin.tittle;

import com.xdd.serverPlugin.tittle.subclasses.CountdownTitle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTittleRunnable extends BukkitRunnable {
    private final Player player;
    private int countdownTime;
    private final Title endTitle;
    private final Component mainTitle;
    private final Title.Times times;
    private final CountdownTitle countdownTitle;

    boolean isFirst = true;

    public CountdownTittleRunnable(Player player, Title endTitle, Component mainTitle,
                                   int countdownTime, Title.Times times, CountdownTitle countdownTitle) {
        this.player = player;
        this.endTitle = endTitle;
        this.mainTitle = mainTitle;
        this.times = times;
        this.countdownTitle = countdownTitle;
        this.countdownTime = countdownTime;
    }

    @Override
    public void run() {
        if(isFirst){
            isFirst = false;
        }
        if(countdownTime >= 0) player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.85f,1.2f);
        if(countdownTime <= 0){
            countdownTitle.getManager().removeTitle(countdownTitle);
            player.showTitle(endTitle);
            countdownTitle.getOnFinish().run();
            this.cancel();
            return;
        }
        player.showTitle(Title.title(mainTitle, Component.text(countdownTime + "s").color(TextColor.color(255,102,102)), times));
        countdownTime--;
    }
}

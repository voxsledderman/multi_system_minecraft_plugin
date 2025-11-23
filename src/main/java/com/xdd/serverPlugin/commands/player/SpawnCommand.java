package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.service.SpawnTeleportService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "spawn")
public class SpawnCommand {

    private final ServerPlugin plugin = ServerPlugin.getInstance();


    @Execute
    void teleport(@Context Player player){
        int secondsDelay = 3;
        if(player.hasPermission(Perms.BYPASS_TP_COUNTING)){
            secondsDelay = 0;
        }

        SpawnTeleportService spawnTeleportService = new SpawnTeleportService(plugin.getStoppableManager(), plugin.getTitleManager());
        spawnTeleportService.teleportWithCountdown(player, secondsDelay);
    }
}

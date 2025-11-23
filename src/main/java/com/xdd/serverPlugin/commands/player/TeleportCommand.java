package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.permissions.Perms;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "teleport", aliases = "tp")
public class TeleportCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();

    @Execute (name = "camp", aliases = "obóz")
    @Permission(Perms.TELEPORT_TO_ANY_CAMP)
    void teleport(@Context Player player, @Arg int campID){
        Location location = plugin.getLocationSaveManager().getLocation(campID);
        if(location == null){
            player.sendMessage(TextUtils.formatMessage("Nie znaleziono obozu o id %d".formatted(campID),
                    TextColor.color(0xFF7070)));
            return;
        }
        player.teleport(location);

        player.sendMessage(TextUtils.formatMessage("Przeteleportowano na obóz o id %d".formatted(campID),
                TextColor.color(0x45FF4A)));
    }
}

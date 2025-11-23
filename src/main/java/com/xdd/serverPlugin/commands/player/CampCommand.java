package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.AdminActionsManager;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;

import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.service.CampAdminService;
import com.xdd.serverPlugin.service.CampTeleportService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;

import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;


@Command(name = "camp", aliases = {"obóz", "wyspa", "is", "home", "dzialka"})
public class CampCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();
    private final AdminActionsManager adminActionsManager = plugin.getAdminActionsManager();

    @Execute
    void teleport(@Context Player player) {
        Camp camp = campManager.getPlayerCamp(player);

        if (camp == null) {
            camp = campManager.createNewCamp(player);
            campManager.registerPlayerCamp(player, camp);
        }

        CampTeleportService campTeleportService = new CampTeleportService(campManager);
        campTeleportService.teleportPlayerToCamp(player);
    }

    @Execute(name = "setlvl")
    @Permission(Perms.ALLOW_CAMPS_ADMIN_ACTIONS)
    void setLvl(@Context CommandSender sender, @Arg String ownerNick, @Arg  int lvl){

        Camp camp = CampAdminService.findCamp(ownerNick, sender);
        if(camp == null) return;

        int maxLvl = ConstantValues.borderSizeByLevel.length;
        if(maxLvl < lvl){
            sender.sendMessage(Component.text("Podano poziom który nie mieści się w zakresie 1 - %d (nic się nie zmieniło...)".formatted(maxLvl)
            ).color(TextColor.color(0xFF7070)));
            return;
        }
        camp.changeLevel(lvl);
        sender.sendMessage(TextUtils.formatMessage("Zmieniono poziom wyspy id - %d na (%d / %d)"
                .formatted(camp.getCampID(),lvl, maxLvl), TextColor.color(0x70FF70)));
    }
    @Execute(name = "delete")
    @Permission(Perms.ALLOW_CAMPS_ADMIN_ACTIONS)
    void delete(@Context Player player, @Arg int campID){
        if(campID < 0){
            Camp camp = adminActionsManager.getPendingCampDeleteConfirmations().get(campID);
            if(camp == null){
                player.sendMessage(TextUtils.formatMessage("Nie masz nic do usuwania! /camp delete (campID)", TextColor.color(0xFF7070)));
                return;
            }
            camp.deleteCamp(campManager);
            player.sendMessage(TextUtils.formatMessage("Usunięto wyspę o id %d".formatted(camp.getCampID()),
                    TextColor.color(0xFF7070)));
            return;
        }
        Camp camp = CampAdminService.findCamp(campID, player);
        if(camp == null) return;
        Random random = new Random();
        int code = (random.nextInt(500) + 1) * -1;
        adminActionsManager.getPendingCampDeleteConfirmations().put(code, camp);
        player.sendMessage(TextUtils.formatMessage("Aby potwierdzić usuwanie wyspy id - %d wpisz /camp delete %d ".formatted(campID, code),
                TextColor.color(0xFF7070)));
    }
    @Execute(name = "id")
    @Permission(Perms.ALLOW_CAMPS_ADMIN_ACTIONS)
    void getOwner(@Context CommandSender sender, @Arg String ownerName){
        Camp camp = CampAdminService.findCamp(ownerName, sender);
        if(camp == null) return;

        sender.sendMessage(Component.text("Id obozu gracza %s to (id=%d) ".formatted(ownerName, camp.getCampID())
        ).color(TextColor.color(0x45FF4A)));
    }
}

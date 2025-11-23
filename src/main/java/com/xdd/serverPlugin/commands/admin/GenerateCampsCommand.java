package com.xdd.serverPlugin.commands.admin;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.AdminActionsManager;
import com.xdd.serverPlugin.cuboids.camp.generator.CampGenerator;
import com.xdd.serverPlugin.permissions.Perms;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

@Command(name = "generate")
@Permission(Perms.GENERATE_CAMPS)
public class GenerateCampsCommand {
    private static final AdminActionsManager adminActionsManager = ServerPlugin.getInstance().getAdminActionsManager();

    @Execute(name = "camps")
    void generate(@Context Player player, @Arg int amount) {
        adminActionsManager.getPendingConfirmations().put(player.getUniqueId(), amount);
        player.sendMessage(TextUtils.formatMessage(
                "Chcesz wygenerować " + amount + " obozów? Użyj /generate yes lub /generate no",
                TextDecoration.BOLD, TextColor.color(0xFFF620)
        ));
    }

    @Execute(name = "yes")
    void confirm(@Context Player player) {
        if (!adminActionsManager.getPendingConfirmations().containsKey(player.getUniqueId())) {
            player.sendMessage(TextUtils.formatMessage("Nie masz nic do potwierdzenia!", TextColor.color(0xFF7070)));
            return;
        }

        int amount = adminActionsManager.getPendingConfirmations().remove(player.getUniqueId());
        player.sendMessage(TextUtils.formatMessage(
                "Za chwilę zacznię się generowanie obozów, ilość: " + amount,
                TextColor.color(0x00FF00)
        ));
        CampGenerator campGenerator = new CampGenerator(amount, player);
        campGenerator.pasteSchematics();
    }

    @Execute(name = "no")
    void cancel(@Context Player player) {
        if (adminActionsManager.getPendingConfirmations().remove(player.getUniqueId()) != null) {
            player.sendMessage(TextUtils.formatMessage("Anulowano generowanie!", TextColor.color(0xFF7070)));
        } else {
            player.sendMessage(TextUtils.formatMessage("Nie masz nic do anulowania!", TextColor.color(0xFF7070)));
        }
    }
    @Execute (name = "reload")
    void reload(@Context Player player){
        ServerPlugin.getInstance().getLocationSaveManager().reload();
        player.sendMessage(TextUtils.formatMessage("Plik został załadowany ponownie!", TextColor.color(0x45FF4A)));
    }

    @Execute (name = "pause")
    void pause(@Context Player player){
        final CampGenerator campGenerator = adminActionsManager.getRunningCampGeneration(player);

        if(campGenerator == null || campGenerator.getPasteTask().isCancelled() || campGenerator.isPaused()) {
            player.sendMessage(TextUtils.formatMessage("Nie masz nic do zatrzymania!", TextColor.color(0xFF7070)));
            return;
        }
        campGenerator.pause();
    }

    @Execute (name = "resume")
    void resume(@Context Player player){
        final CampGenerator campGenerator = adminActionsManager.getRunningCampGeneration(player);

        if(campGenerator == null || campGenerator.getPasteTask().isCancelled() || !campGenerator.isPaused()){
            player.sendMessage(TextUtils.formatMessage("Nie masz nic do kontynuowania!", TextColor.color(0x45FF4A)));
            return;
        }
        campGenerator.resume();
    }

    @Execute (name = "stop")
    void stop(@Context Player player){
        final CampGenerator campGenerator = adminActionsManager.getRunningCampGeneration(player);

        if(campGenerator == null || campGenerator.getPasteTask().isCancelled()){
            player.sendMessage(TextUtils.formatMessage("Nie masz nic do zakończenia!", TextColor.color(0xFF7070)));
            return;
        }
        campGenerator.stop();
    }
}

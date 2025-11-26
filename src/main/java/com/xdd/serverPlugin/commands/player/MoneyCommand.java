package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.CacheManager;
import com.xdd.serverPlugin.database.dao.PlayerDao;
import com.xdd.serverPlugin.database.data.PlayerData;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.permissions.Perms;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@Command(name = "money", aliases = {"kasa", "hajs", "pieniądze", "pieniadze"})
public class MoneyCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CacheManager cacheManager = plugin.getCacheManager();

    @Execute
    void onCommand(@Context Player player){
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if(playerData == null){
            player.sendMessage(TextUtils.formatMessage("Nie udało się załadować twoich danych", TextColor.color(0xFF7070)));
            return;
        }
        EconomyData economyData = playerData.getEconomyData();
        if(economyData == null){
            player.sendMessage(TextUtils.formatMessage("Nie udało się załadować twoich danych ekonomii", TextColor.color(0xFF7070)));
            return;
        }
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Twój stan konta: <white>%s <glyph:coin>".formatted(economyData.getMoney().formated())));
    }


    private enum Operation{
        ADD(),
        SUBTRACT(),
        SET()
    }

    @Execute(name = "add") @Permission(Perms.MANIPULATE_PLAYER_BALANCE)
    void addMoney(@Context Player admin, @Arg String playerName, @Arg double amount) throws SQLException {
        handleOperation(admin, playerName, amount, Operation.ADD);
    }
    @Execute(name = "subtract") @Permission(Perms.MANIPULATE_PLAYER_BALANCE)
    void subtractMoney(@Context Player admin, @Arg String playerName, @Arg double amount) throws SQLException {
       handleOperation(admin, playerName, amount, Operation.SUBTRACT);
    }
    @Execute(name = "set") @Permission(Perms.MANIPULATE_PLAYER_BALANCE)
    void setMoney(@Context Player admin, @Arg String playerName, @Arg double money) throws SQLException {
      handleOperation(admin, playerName, money, Operation.SET);
    }

    private void handleOperation(Player admin, String playerName, double amount, Operation operation) throws SQLException {

        if(amount < 0){
            admin.sendMessage(TextUtils.formatMessage("Dla tej operacji wymagana jest wartość wieksza od 0", TextColor.color(0xFF7070)));
            return;
        }
        Player player = Bukkit.getPlayer(playerName);
        if(player == null){
            admin.sendMessage(TextUtils.formatMessage("Nie znaleziono gracza o nicku [%s]".formatted(playerName), TextColor.color(0xFF7070)));
            return;
        }
        PlayerData playerData = cacheManager.getPlayerData(player);

        if(playerData == null){
            admin.sendMessage(TextUtils.formatMessage("Nie udało się załadować PlayerData gracza o nicku [%s]".formatted(playerName), TextColor.color(0xFF7070)));
            return;
        }

        EconomyData economyData = playerData.getEconomyData();
        var moneyData = economyData.getMoney();
        switch (operation) {
            case ADD -> moneyData.addMoney(amount);
            case SUBTRACT -> {
                moneyData.subtractMoney(amount);
                if (moneyData.getDouble() < 0) {
                    moneyData.setMoney(0);
                }
            }
            case SET -> moneyData.setMoney(amount);
        }

        final PlayerDao dao = plugin.getPlayerDao();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncTask ->{
            try {
                dao.save(playerData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        admin.sendMessage(TextUtils.formatMessage("Nowy bilans gracza [%s] to -> [%s$]".formatted(playerName, economyData.getMoney().formated()), TextColor.color(0x45FF4A)));
    }
}

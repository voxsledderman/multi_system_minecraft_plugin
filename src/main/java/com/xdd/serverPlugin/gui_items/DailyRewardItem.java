package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.PlayerUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.daily_rewards.DailyRewardMenu;
import com.xdd.serverPlugin.daily_rewards.enums.Rewards;
import com.xdd.serverPlugin.database.data.subdatas.DailyRewardData;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.permissions.Perms;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class DailyRewardItem extends AbstractItem {

    private final Player player;
    private final Rewards rewards;
    private final int dayID;
    private final DailyRewardData dailyRewardData;
    private State state = null;
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final EconomyData economyData;
    private final Menu returnMenu;

    public DailyRewardItem(Player player, Rewards rewards, Menu returnMenu) {
        this.player = player;
        this.rewards = rewards;
        this.dayID = rewards.getDayID();
        dailyRewardData = ServerPlugin.getInstance().getCacheManager().getPlayerData(player).getDailyRewardData();
        economyData = plugin.getCacheManager().getPlayerData(player).getEconomyData();
        this.returnMenu = returnMenu;
    }
    @Override
    public ItemProvider getItemProvider(){
       return checkRewardState(rewards.getDayID(), dailyRewardData.getHotStreak());
    }



    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {

        if(clickType.isLeftClick()) {
            if(player.hasPermission(Perms.PICK_DAILY_REWARDS_WHENEVER)){
                handleRewardGiving();
            }
            switch (state) {
                case null -> Sounds.playGuiClickSound(player);
                case CLAIMED, AWAITING, NOT_READY -> Sounds.playGuiClickSound(player);
                case READY -> handleRewardGiving();
            }
        } else if(clickType.isRightClick()){

        }
    }

    private void handleRewardGiving(){
        if(!giveRewardToPlayer()) return;
        if(dailyRewardData.getClaimedRewardIDs().contains(Rewards.values().length)){
            dailyRewardData.setClaimedRewardIDs(new ArrayList<>());
        } else {
            dailyRewardData.getClaimedRewardIDs().add(dayID);
        }
        if(dayID == Rewards.values().length){
            dailyRewardData.setClaimedRewardIDs(new ArrayList<>());
        }
        dailyRewardData.setLastClaimedRewardData(LocalDate.now());
        dailyRewardData.setHotStreak(dailyRewardData.getHotStreak() + 1);
        saveToDatabase(plugin);

        Menu menu = new DailyRewardMenu(plugin, player);
        menu.openMenu(player);
    }

    private boolean giveRewardToPlayer(){
        ItemStack itemReward = rewards.getItemReward();
        double moneyReward = rewards.getMoneyReward();

        if(itemReward != null && moneyReward > 0){
            if(!PlayerUtils.canPlayerInventoryFit(player.getInventory(), itemReward)){
                player.sendMessage(Component.text("Nie masz wolnego miejsca w ekwipunku!"));
                return false;
            }
            player.getInventory().addItem(itemReward);
            economyData.getMoney().addMoney(moneyReward);

        } else if(itemReward != null){
            if(PlayerUtils.canPlayerInventoryFit(player.getInventory(), itemReward)){
                player.getInventory().addItem(itemReward);
            } else {
                player.sendMessage(Component.text("Nie masz wolnego miejsca w ekwipunku!"));
                return false;
            }
        } else if(moneyReward > 0){
            economyData.getMoney().addMoney(moneyReward);
        }
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Odebrano nagrode za<white> %d <green>dzień!".formatted(dayID)));
        Sounds.playItemClaimSound(player);
        return true;
    }

    private ItemProvider checkRewardState(int dayID, int hotStreak) {

        List<Integer> claimedIDs = dailyRewardData.getClaimedRewardIDs();
        if (claimedIDs == null) claimedIDs = new ArrayList<>();

        LocalDate lastClaimed = dailyRewardData.getLastClaimedRewardData();
        if (hotStreak < 0) hotStreak = 0;

        int nextFromList = claimedIDs.isEmpty() ? 1 : Collections.max(claimedIDs) + 1;
        int nextExpected = Math.max(nextFromList, hotStreak + 1);

        State computedState = State.NOT_READY;
        ItemStack icon = rewards.getRarity().getItemIcon();

        if (lastClaimed != null) {
            long daysBetween = ChronoUnit.DAYS.between(lastClaimed, LocalDate.now());

            if (daysBetween > 1) {
                dailyRewardData.setClaimedRewardIDs(new ArrayList<>());
                hotStreak = 0;
                dailyRewardData.setHotStreak(hotStreak);

                if (dayID == 1) {
                    computedState = State.READY;
                    icon = GuiUtils.rewardsReady;
                }

                ItemBuilder resetBuilder = new ItemBuilder(icon);
                resetBuilder.setDisplayName(new AdventureComponentWrapper(
                        MiniMessage.miniMessage().deserialize("<green>Dzień<gray> - <white>%d".formatted(dayID))
                ));

                List<ComponentWrapper> resetLore = new ArrayList<>();
                resetLore.add(new AdventureComponentWrapper(rewards.getRarity().getFirstLoreLine()));
                resetLore.add(new AdventureComponentWrapper(Component.empty()));
                resetLore.add(new AdventureComponentWrapper(
                        MiniMessage.miniMessage().deserialize("<gray>Loguj się <green>%d dni <gray>z rzędu aby odebrać!".formatted(dayID))
                ));
                resetLore.add(new AdventureComponentWrapper(
                        MiniMessage.miniMessage().deserialize("<glyph:shop_right_click> <gray>aby zobaczyć <green>potencjalne <gray>nagrody")
                ));

                resetBuilder.setLore(resetLore);
                this.state = computedState;
                return resetBuilder;
            }
        }

        if (claimedIDs.contains(dayID)) {
            computedState = State.CLAIMED;
            icon = GuiUtils.rewardsClaimed;

        } else if (lastClaimed == null && dayID == 1) {
            computedState = State.READY;
            icon = GuiUtils.rewardsReady;

        } else if (lastClaimed != null) {
            long daysBetween = ChronoUnit.DAYS.between(lastClaimed, LocalDate.now());

            if (daysBetween == 0 && claimedIDs.contains(dayID-1)) {
                computedState = State.AWAITING;
                icon = GuiUtils.rewardsAwaiting;

            } else if (daysBetween == 1 && dayID == nextExpected) {
                computedState = State.READY;
                icon = GuiUtils.rewardsReady;

            } else if (claimedIDs.contains(dayID)) {
                computedState = State.CLAIMED;
                icon = GuiUtils.rewardsClaimed;
            }

        }

        ItemBuilder builder = new ItemBuilder(icon);

        builder.setDisplayName(new AdventureComponentWrapper(
                MiniMessage.miniMessage().deserialize("<green>Dzień<gray> - <white>%d".formatted(dayID))
        ));

        List<ComponentWrapper> lore = new ArrayList<>();
        lore.add(new AdventureComponentWrapper(rewards.getRarity().getFirstLoreLine()));
        lore.add(new AdventureComponentWrapper(Component.empty()));
        lore.add(new AdventureComponentWrapper(
                MiniMessage.miniMessage().deserialize("<gray>Loguj się <green>%d dni <gray>z rzędu aby odebrać!".formatted(dayID))
        ));
        lore.add(new AdventureComponentWrapper(
                MiniMessage.miniMessage().deserialize("<glyph:shop_right_click> <gray>aby zobaczyć <green>potencjalne <gray>nagrody")
        ));
        lore.add(new AdventureComponentWrapper(Component.empty()));
        lore.add(new AdventureComponentWrapper(
               Component.text("ᴏʙᴇᴄɴʏ sᴛʀᴇᴀᴋ: <white>%d %s" .formatted(hotStreak, hotStreak == 1 ? "ᴅᴢɪᴇɴ" : "ᴅɴɪ"), TextColor.color(0xFFC93A))
        ));

        builder.setLore(lore);
        this.state = computedState;

        return builder;
    }

    private void saveToDatabase(ServerPlugin plugin){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, bukkitTask -> {
            try {
                plugin.getPlayerDao().save(plugin.getCacheManager().getPlayerData(player));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private enum State{
        AWAITING,
        CLAIMED,
        READY,
        NOT_READY
    }
}

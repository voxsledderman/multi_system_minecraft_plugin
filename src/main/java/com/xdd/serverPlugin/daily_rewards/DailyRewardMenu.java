package com.xdd.serverPlugin.daily_rewards;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.daily_rewards.enums.Rewards;
import com.xdd.serverPlugin.gui_items.DailyRewardItem;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;

public class DailyRewardMenu extends Menu {

    private final Player player;
    public DailyRewardMenu(ServerPlugin plugin, Player player) {
        super(plugin);
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return "<shift:-8><glyph:daily_rewards_ui>";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". a b c d e f g .",
                        ". h i j k l m n .",
                        ". o p q r s t u .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new DailyRewardItem(player, Rewards.DAY1, this))
                .addIngredient('b', new DailyRewardItem(player, Rewards.DAY2, this))
                .addIngredient('c', new DailyRewardItem(player, Rewards.DAY3, this))
                .addIngredient('d', new DailyRewardItem(player, Rewards.DAY4, this))
                .addIngredient('e', new DailyRewardItem(player, Rewards.DAY5, this))
                .addIngredient('f', new DailyRewardItem(player, Rewards.DAY6, this))
                .addIngredient('g', new DailyRewardItem(player, Rewards.DAY7, this))

                .addIngredient('h', new DailyRewardItem(player, Rewards.DAY8, this))
                .addIngredient('i', new DailyRewardItem(player, Rewards.DAY9, this))
                .addIngredient('j', new DailyRewardItem(player, Rewards.DAY10, this))
                .addIngredient('k', new DailyRewardItem(player, Rewards.DAY11, this))
                .addIngredient('l', new DailyRewardItem(player, Rewards.DAY12, this))
                .addIngredient('m', new DailyRewardItem(player, Rewards.DAY13, this))
                .addIngredient('n', new DailyRewardItem(player, Rewards.DAY14, this))

                .addIngredient('o', new DailyRewardItem(player, Rewards.DAY15, this))
                .addIngredient('p', new DailyRewardItem(player, Rewards.DAY16, this))
                .addIngredient('q', new DailyRewardItem(player, Rewards.DAY17, this))
                .addIngredient('r', new DailyRewardItem(player, Rewards.DAY18, this))
                .addIngredient('s', new DailyRewardItem(player, Rewards.DAY19, this))
                .addIngredient('t', new DailyRewardItem(player, Rewards.DAY20, this))
                .addIngredient('u', new DailyRewardItem(player, Rewards.DAY21, this))
                .build();
    }

    @Override
    public void playOpenSound() {

    }
}

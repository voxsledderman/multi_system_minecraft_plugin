package com.xdd.serverPlugin.crates;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.MoneyItemUtils;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Crate {

    private final List<CrateReward> rewards;
    private final List<Item> itemRewards = new ArrayList<>();
    private final ServerPlugin plugin = ServerPlugin.getInstance();


    public Crate(List<CrateReward> rewards) {
        this.rewards = rewards;
        convertToItemRewards();

    }
    private void convertToItemRewards(){
        for(CrateReward reward : rewards){
            double moneyAmount = reward.getMoneyReward();
            ItemStack rewardItem = reward.getItemReward();
            ItemStack iconItem = null;

            if(moneyAmount > 0){
               iconItem = MoneyItemUtils.getMoneyItem(moneyAmount).clone();
            } else if(rewardItem != null && !rewardItem.getType().isAir()){
                iconItem = rewardItem.clone();
            }
            if(iconItem == null) continue;
            itemRewards.add(new SimpleItem(GuiUtils.buildItemWithNameLore(iconItem, null, List.of(
                    MiniMessage.miniMessage().deserialize("<gray>szansa na dropa: <dark_green>%.2f".formatted(reward.getPercentage())  + "%")
            ))));
        }
    }
    public abstract void open();
}

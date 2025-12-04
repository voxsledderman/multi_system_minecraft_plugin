package com.xdd.serverPlugin.daily_rewards.enums;

import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.crates.CrateReward;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public enum RewardRarity {

    LEGENDARY(GuiUtils.rewardsLegendary, Component.text("ʟᴇɢᴇɴᴅᴀʀɴᴀ ɴᴀɢʀᴏᴅᴀ", TextColor.color(0xFFC93A)), List.of(new CrateReward(100,10.43))),
    EPIC(GuiUtils.rewardsEpic, Component.text("ᴇᴘɪᴄᴋᴀ ɴᴀɢʀᴏᴅᴀ", TextColor.color(0xB059FF)), List.of(new CrateReward(200,48.20))),
    NORMAL(GuiUtils.rewardsNormal, Component.text("ᴢᴡʏᴋʟᴀ ɴᴀɢʀᴏᴅᴀ", TextColor.color(0xBDBDBD)), List.of(new CrateReward(100, 11.11)));

    private final ItemStack itemIcon;
    private final Component firstLoreLine;
    private final List<CrateReward> possibleRewards;

    RewardRarity(ItemStack itemIcon, Component firstLoreLine, List<CrateReward> possibleRewards) {
        this.itemIcon = itemIcon;
        this.firstLoreLine = firstLoreLine;
        this.possibleRewards = possibleRewards;
    }
}

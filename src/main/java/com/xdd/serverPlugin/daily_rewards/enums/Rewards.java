package com.xdd.serverPlugin.daily_rewards.enums;

import com.xdd.serverPlugin.Utils.GuiUtils;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public enum Rewards {
    DAY1(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL, 1),
    DAY2(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,2),
    DAY3(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,3),
    DAY4(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,4),
    DAY5(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,5),
    DAY6(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,6),
    DAY7(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.LEGENDARY,7),

    DAY8(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,8),
    DAY9(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,9),
    DAY10(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,10),
    DAY11(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,11),
    DAY12(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,12),
    DAY13(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,13),
    DAY14(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.LEGENDARY,14),

    DAY15(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,15),
    DAY16(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,16),
    DAY17(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,17),
    DAY18(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.LEGENDARY,18),
    DAY19(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.EPIC,19),
    DAY20(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.NORMAL,20),
    DAY21(GuiUtils.ShopIcons.buildingBlocks, 1000, RewardRarity.LEGENDARY,21);


    private final ItemStack itemReward;
    private final double moneyReward;
    private final RewardRarity rarity;
    private final int dayID;

    Rewards(ItemStack itemReward, double moneyReward, RewardRarity rarity, int dayID){
        this.itemReward = itemReward;
        this.moneyReward = moneyReward;
        this.rarity = rarity;
        this.dayID = dayID;
    }
}

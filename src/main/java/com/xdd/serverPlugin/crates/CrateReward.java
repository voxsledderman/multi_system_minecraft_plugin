package com.xdd.serverPlugin.crates;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class CrateReward {
    private ItemStack itemReward;
    private double moneyReward;
    private final double percentage;

    public CrateReward(ItemStack itemReward,double percentage) {
        this.itemReward = itemReward;

        if(percentage > 100){
            throw new IllegalArgumentException("[CrateReward] Percentage nie może być większy niż 100");
        }
        this.percentage = percentage;
    }
    public CrateReward(double moneyReward, double percentage) {
        this.moneyReward = moneyReward;

        if(percentage > 100){
            throw new IllegalArgumentException("[CrateReward] Percentage nie może być większy niż 100");
        }
        this.percentage = percentage;
    }
}

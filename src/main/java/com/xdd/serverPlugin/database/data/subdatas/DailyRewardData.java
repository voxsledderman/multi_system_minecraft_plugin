package com.xdd.serverPlugin.database.data.subdatas;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class DailyRewardData {

    private LocalDate lastClaimedRewardData;
    private List<Integer> claimedRewardIDs;
    private int hotStreak;


    public DailyRewardData(LocalDate lastClaimedRewardData, List<Integer> claimedRewardIDs, int hotStreak) {
        this.lastClaimedRewardData = lastClaimedRewardData;
        this.claimedRewardIDs = claimedRewardIDs;
        this.hotStreak = hotStreak;
    }
}

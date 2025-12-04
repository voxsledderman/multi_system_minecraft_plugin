package com.xdd.serverPlugin.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static Duration secondsTillMidnight() {
        LocalTime now = LocalTime.now();
        long secondsTillMidnight = ChronoUnit.SECONDS.between(now, LocalTime.MAX) + 1;

        return Duration.ofSeconds(secondsTillMidnight);
    }
}

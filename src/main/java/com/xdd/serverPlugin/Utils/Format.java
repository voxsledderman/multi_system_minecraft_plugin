package com.xdd.serverPlugin.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Format {

    public static String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    public static String formatMoneyWithK(double money) {
        if (money < 1000) {
            return trimTrailingZeros(Math.floor(money * 100) / 100.0);
        } else if (money < 1_000_000) {
            double value = Math.floor(money / 1000.0 * 100) / 100.0;
            return trimTrailingZeros(value) + "k";
        } else {
            double value = Math.floor(money / 1_000_000.0 * 100) / 100.0;
            return trimTrailingZeros(value) + "kk";
        }
    }

    public static String formatMoney(double balance) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        boolean isWholeNumber = Math.abs(balance % 1) < 0.001;
        String pattern = isWholeNumber ? "#,###" : "#,###.00";

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        return decimalFormat.format(balance);
    }

    private static String trimTrailingZeros(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.valueOf(value);
        }
    }
}

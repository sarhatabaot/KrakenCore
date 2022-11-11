package com.github.sarhatabaot.kraken.core.ticks;

/**
 * @author sarhatabaot
 */
public class TickUtil {
    private TickUtil() {
        throw new UnsupportedOperationException();
    }

    public static long secondsToTicks(int seconds) {
        return 20L * seconds;
    }

    public static long minutesToTicks(int minutes) {
        return secondsToTicks(60 * minutes);
    }

    public static long hoursToTicks(int hours) {
        return minutesToTicks(60 * hours);
    }


}

package com.github.sarhatabaot.kraken.core.ticks;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author sarhatabaot
 */
public class TickUtil {
    private TickUtil() {
        throw new UnsupportedOperationException();
    }

    public static long secondsToTicks(long seconds) {
        return 20L * seconds;
    }

    public static long minutesToTicks(int minutes) {
        return asTicks(minutes, TimeUnit.MINUTES);
    }

    public static long hoursToTicks(int hours) {
        return asTicks(hours, TimeUnit.HOURS);
    }

    public static long asTicks(int units, @NotNull TimeUnit timeUnit) {
        return secondsToTicks(timeUnit.toSeconds(units));
    }
}

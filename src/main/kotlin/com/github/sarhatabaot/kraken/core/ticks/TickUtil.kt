package com.github.sarhatabaot.kraken.core.ticks

import java.util.concurrent.TimeUnit

/**
 *
 * @author sarhatabaot
 */
object TickUtil {
    fun secondsToTicks(seconds: Long): Long {
        return 20L * seconds
    }

    fun minutesToTicks(minutes: Int): Long {
        return asTicks(minutes, TimeUnit.MINUTES)
    }

    fun hoursToTicks(hours: Int): Long {
        return asTicks(hours, TimeUnit.HOURS)
    }

    fun asTicks(units: Int, timeUnit: TimeUnit): Long {
        return secondsToTicks(timeUnit.toSeconds(units.toLong()))
    }
}
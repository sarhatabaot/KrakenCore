package com.github.sarhatabaot.kraken.core.ticks

import com.github.sarhatabaot.kraken.core.ticks.TickUtil.asTicks
import com.github.sarhatabaot.kraken.core.ticks.TickUtil.hoursToTicks
import com.github.sarhatabaot.kraken.core.ticks.TickUtil.minutesToTicks
import com.github.sarhatabaot.kraken.core.ticks.TickUtil.secondsToTicks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

/**
 *
 * @author sarhatabaot
 */
class TickUtilTest {

    @Test
    fun secondsToTicks() {
        assertEquals(20, secondsToTicks(1))
        assertEquals(1200, secondsToTicks(60))
    }

    @Test
    fun minutesToTicks() {
        assertEquals(1200, minutesToTicks(1))
        assertEquals(12000, minutesToTicks(10))
    }

    @Test
    fun hoursToTicks() {
        assertEquals(72000, hoursToTicks(1))
        assertEquals(144000, hoursToTicks(2))
    }

    @Test
    fun asTicks() {
        assertEquals(1728000, asTicks(1, TimeUnit.DAYS))
        //todo account for half a second etc
    }
}
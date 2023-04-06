package com.github.sarhatabaot.kraken.core.ticks;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class TickUtilTest {
    
    @Test
    void secondsToTicks() {
        assertEquals(20,TickUtil.secondsToTicks(1));
        assertEquals(1200, TickUtil.secondsToTicks(60));
    }
    
    @Test
    void minutesToTicks() {
        assertEquals(1200,TickUtil.minutesToTicks(1));
        assertEquals(12000,TickUtil.minutesToTicks(10));
    }
    
    @Test
    void hoursToTicks() {
        assertEquals(72000,TickUtil.hoursToTicks(1));
        assertEquals(144000, TickUtil.hoursToTicks(2));
    }
    
    @Test
    void asTicks() {
        assertEquals(1728000, TickUtil.asTicks(1,TimeUnit.DAYS));
        //todo account for half a second etc
    }
}
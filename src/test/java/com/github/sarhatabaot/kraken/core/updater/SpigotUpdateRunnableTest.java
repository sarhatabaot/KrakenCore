package com.github.sarhatabaot.kraken.core.updater;

import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class SpigotUpdateRunnableTest {
    private static SpigotUpdateRunnable outOfDateRunnable;
    private static SpigotUpdateRunnable upToDateRunnable;
    @BeforeAll
    static void setup() {
        Logger logger = Logger.getLogger(SpigotUpdateRunnableTest.class.getName());
        outOfDateRunnable = new SpigotUpdateRunnable("1.7.0", logger, "63972"); //TODO Change to plugin that doesn't change.
        upToDateRunnable = new SpigotUpdateRunnable("1.7.4", logger,"63972");
    }
    
    @Test
    void run() {
        outOfDateRunnable.run();
        upToDateRunnable.run();
        
        assertTrue(outOfDateRunnable.localVersion.compareTo(outOfDateRunnable.remoteVersion) < 0);
        assertNotEquals(0, outOfDateRunnable.localVersion.compareTo(outOfDateRunnable.remoteVersion));
        
        assertEquals(0, upToDateRunnable.localVersion.compareTo(upToDateRunnable.remoteVersion));
        assertFalse(upToDateRunnable.localVersion.compareTo(upToDateRunnable.remoteVersion) < 0);
    }
    
    @Test
    void getVersionFromRemote() {
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/spigot-enchantgui-correct.json").getFile()))) {
            assertEquals("1.7.4", outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/spigot-enchantgui-correct.json").getFile()))) {
            assertNotEquals("1.7.3", outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/spigot-enchantgui-malformed.json").getFile()))) {
            assertEquals("0.0.0", outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void getApiEndpoint() {
        try {
            assertEquals(new URL("https://api.spiget.org/v2/resources/%s/versions/latest".formatted("63972")), outOfDateRunnable.getApiEndpoint());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void getNewVersionUrl() {
        assertEquals("https://api.spiget.org/v2/resources/%s/versions/latest/download".formatted("63972"), outOfDateRunnable.getNewVersionUrl());
        assertNotEquals("https://api.spiget.org/v2/resources/%s/versions/latest/download".formatted("26543"), outOfDateRunnable.getNewVersionUrl());
        assertNotEquals("https://incorrect.url.org/%s".formatted("63972"), outOfDateRunnable.getNewVersionUrl());
    }
}
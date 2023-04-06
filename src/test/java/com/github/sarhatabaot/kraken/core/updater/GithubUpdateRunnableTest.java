package com.github.sarhatabaot.kraken.core.updater;

import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class GithubUpdateRunnableTest {
    private static final String OUT_OF_DATE = "v1.7.0";
    private static final String UP_TO_DATE = "v1.7.4";
    private static final String USER = "sarhatabaot";
    private static final String REPO = "EnchantGUI";  //TODO Change to plugin that doesn't change.
    private static GithubUpdateRunnable outOfDateRunnable;
    private static GithubUpdateRunnable upToDateRunnable;
    
    @BeforeAll
    static void setUp() {
        Logger logger = Logger.getLogger(SpigotUpdateRunnableTest.class.getName());
        outOfDateRunnable = new GithubUpdateRunnable(OUT_OF_DATE, logger, USER, REPO);
    upToDateRunnable = new GithubUpdateRunnable(UP_TO_DATE, logger,USER, REPO);
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
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/github-enchantgui-correct.json").getFile()))) {
            assertEquals(UP_TO_DATE, outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/github-enchantgui-correct.json").getFile()))) {
            assertNotEquals(OUT_OF_DATE, outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        try(JsonReader reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("updater/github-enchantgui-malformed.json").getFile()))) {
            assertEquals("0.0.0", outOfDateRunnable.getVersionFromRemote(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void getApiEndpoint() {
        try {
            assertEquals(new URL("https://api.github.com/repos/%s/%s/releases/latest".formatted(USER, REPO)), outOfDateRunnable.getApiEndpoint());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void getNewVersionUrl() {
        outOfDateRunnable.run();
        upToDateRunnable.run();
        
        assertEquals("https://github.com/%s/%s/releases/tag/%s".formatted(USER, REPO, UP_TO_DATE), outOfDateRunnable.getNewVersionUrl());
        assertNotEquals("https://github.com/%s/%s/releases/tag/%s".formatted(USER, REPO, OUT_OF_DATE), outOfDateRunnable.getNewVersionUrl());
        assertNotEquals("https://incorrect.url.org/%s/%s".formatted(USER,REPO), outOfDateRunnable.getNewVersionUrl());
        
        assertEquals("https://github.com/%s/%s/releases/tag/%s".formatted(USER, REPO, UP_TO_DATE),upToDateRunnable.getNewVersionUrl());
    }
}
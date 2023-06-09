package com.github.sarhatabaot.kraken.core.updater

import com.google.gson.stream.JsonReader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 *
 * @author sarhatabaot
 */
class SpigotUpdateRunnableTest {
    private var outOfDateRunnable: SpigotUpdateRunnable? = null
    private var upToDateRunnable: SpigotUpdateRunnable? = null

    @BeforeAll
    fun setup() {
        val logger = LoggerFactory.getLogger(SpigotUpdateRunnableTest::class.java.name)
        outOfDateRunnable = SpigotUpdateRunnable("1.7.0", logger, "63972") //TODO Change to plugin that doesn't change.
        upToDateRunnable = SpigotUpdateRunnable("1.7.4", logger, "63972")
    }

    @Test
    fun run() {
        outOfDateRunnable!!.run()
        upToDateRunnable!!.run()
        assertTrue(outOfDateRunnable!!.localVersion < outOfDateRunnable!!.remoteVersion)
        assertNotEquals(0, outOfDateRunnable!!.localVersion.compareTo(outOfDateRunnable!!.remoteVersion))
        assertEquals(0, upToDateRunnable!!.localVersion.compareTo(upToDateRunnable!!.remoteVersion))
        assertFalse(upToDateRunnable!!.localVersion < upToDateRunnable!!.remoteVersion)
    }

    @Test
    fun getVersionFromRemote() {
        try {
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/spigot-enchantgui-correct.json").file)).use { reader ->
                assertEquals(
                    "1.7.4",
                    outOfDateRunnable!!.getVersionFromRemote(reader)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/spigot-enchantgui-correct.json").file)).use { reader ->
                assertNotEquals(
                    "1.7.3",
                    outOfDateRunnable!!.getVersionFromRemote(reader)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/spigot-enchantgui-malformed.json").file)).use { reader ->
                assertEquals(
                    "0.0.0",
                    outOfDateRunnable!!.getVersionFromRemote(reader)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun getApiEndpoint() {
        try {
            assertEquals(
                URL("https://api.spiget.org/v2/resources/%s/versions/latest".formatted("63972")),
                outOfDateRunnable!!.getApiEndpoint()
            )
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }

    @Test
    fun getNewVersionUrl() {
        assertEquals(
            "https://api.spiget.org/v2/resources/%s/versions/latest/download".formatted("63972"),
            outOfDateRunnable!!.getNewVersionUrl()
        )
        assertNotEquals(
            "https://api.spiget.org/v2/resources/%s/versions/latest/download".formatted("26543"),
            outOfDateRunnable!!.getNewVersionUrl()
        )
        assertNotEquals("https://incorrect.url.org/%s".formatted("63972"), outOfDateRunnable!!.getNewVersionUrl())
    }
}
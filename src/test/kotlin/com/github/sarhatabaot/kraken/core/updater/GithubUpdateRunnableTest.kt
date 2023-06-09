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
class GithubUpdateRunnableTest {

    private val OUT_OF_DATE = "v1.7.0"
    private val UP_TO_DATE = "v1.7.4"
    private val USER = "sarhatabaot"
    private val REPO = "EnchantGUI" //TODO Change to plugin that doesn't change.

    private var outOfDateRunnable: GithubUpdateRunnable? = null
    private var upToDateRunnable: GithubUpdateRunnable? = null

    @BeforeAll
    fun setUp() {
        val logger = LoggerFactory.getLogger(SpigotUpdateRunnableTest::class.java.name)
        outOfDateRunnable = GithubUpdateRunnable(OUT_OF_DATE, logger, USER, REPO)
        upToDateRunnable = GithubUpdateRunnable(UP_TO_DATE, logger, USER, REPO)
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
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/github-enchantgui-correct.json").file)).use { reader ->
                assertEquals(
                    UP_TO_DATE,
                    outOfDateRunnable!!.getVersionFromRemote(reader)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/github-enchantgui-correct.json").file)).use { reader ->
                assertNotEquals(
                    OUT_OF_DATE,
                    outOfDateRunnable!!.getVersionFromRemote(reader)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            JsonReader(FileReader(javaClass.classLoader.getResource("updater/github-enchantgui-malformed.json").file)).use { reader ->
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
                URL("https://api.github.com/repos/%s/%s/releases/latest".format(USER, REPO)),
                outOfDateRunnable!!.getApiEndpoint()
            )
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }

    @Test
    fun getNewVersionUrl() {
        outOfDateRunnable!!.run()
        upToDateRunnable!!.run()
        assertEquals(
            "https://github.com/%s/%s/releases/tag/%s".format(USER, REPO, UP_TO_DATE),
            outOfDateRunnable!!.getNewVersionUrl()
        )
        assertNotEquals(
            "https://github.com/%s/%s/releases/tag/%s".format(USER, REPO, OUT_OF_DATE),
            outOfDateRunnable!!.getNewVersionUrl()
        )
        assertNotEquals("https://incorrect.url.org/%s/%s".format(USER, REPO), outOfDateRunnable!!.getNewVersionUrl())
        assertEquals(
            "https://github.com/%s/%s/releases/tag/%s".format(USER, REPO, UP_TO_DATE),
            upToDateRunnable!!.getNewVersionUrl()
        )
    }
}
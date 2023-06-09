package com.github.sarhatabaot.kraken.core.updater

import com.github.sarhatabaot.kraken.core.extensions.info
import com.github.sarhatabaot.kraken.core.extensions.logWarningException
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import org.bukkit.scheduler.BukkitRunnable
import org.slf4j.Logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection

/**
 *
 * @author sarhatabaot
 */
abstract class CheckForUpdateRunnable(pluginVersion: String,private var logger: Logger): BukkitRunnable(){
    val localVersion: DefaultArtifactVersion = DefaultArtifactVersion(pluginVersion)
    var remoteVersion: DefaultArtifactVersion? = null

    override fun run() {
        try {
            val urlConnection: URLConnection? = getApiEndpoint()?.openConnection()
            if (urlConnection != null) {
                urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0")
                BufferedReader(InputStreamReader(urlConnection.getInputStream())).use { `in` ->
                    val gson = Gson()
                    gson.newJsonReader(`in`).use { reader ->
                        this.remoteVersion = DefaultArtifactVersion(getVersionFromRemote(reader))
                        if (this.localVersion < remoteVersion) {
                            logNewVersion()
                        }
                    }
                }
            }
        } catch (e: IOException) {
            logger.logWarningException(e)
        }
    }

    abstract fun getVersionFromRemote(reader: JsonReader?): String?

    //API Endpoint
    @Throws(MalformedURLException::class)
    abstract fun getApiEndpoint(): URL?

    abstract fun getNewVersionUrl(): String?

    open fun logNewVersion() {
        logger.info {
            "New version available: %s, Current version: %s".format(
                remoteVersion.toString(),
                localVersion.toString()
            )
        }
        logger.info { "Get it at: %s".format(getNewVersionUrl()) }
    }
}
package com.github.sarhatabaot.kraken.core.updater

import com.google.gson.stream.JsonReader
import org.slf4j.Logger
import java.io.IOException
import java.net.URL

/**
 *
 * @author sarhatabaot
 */
class SpigotUpdateRunnable(pluginVersion: String, logger: Logger, private var resourceId: String): CheckForUpdateRunnable(pluginVersion, logger) {
    override fun getVersionFromRemote(reader: JsonReader?): String? {
        var version: String? = "0.0.0"
        try {
            reader!!.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                if (name == "name") {
                    version = reader.nextString()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
        } catch (e: IOException) {
            return version
        }
        return version
    }

    override fun getApiEndpoint(): URL = URL("https://api.spiget.org/v2/resources/%s/versions/latest".format(this.resourceId))

    override fun getNewVersionUrl(): String = "https://api.spiget.org/v2/resources/%s/versions/latest/download".format(this.resourceId)
}
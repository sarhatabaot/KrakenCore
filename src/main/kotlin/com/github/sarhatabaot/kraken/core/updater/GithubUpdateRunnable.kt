package com.github.sarhatabaot.kraken.core.updater

import com.google.gson.stream.JsonReader
import org.slf4j.Logger
import java.io.IOException
import java.net.URL

/**
 *
 * @author sarhatabaot
 */
class GithubUpdateRunnable(pluginVersion: String, logger: Logger,private var user: String,private var repo: String): CheckForUpdateRunnable(pluginVersion, logger) {
    override fun getVersionFromRemote(reader: JsonReader?): String? {
        var version: String? = "0.0.0"
        try {
            reader!!.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                if (name == "tag_name") {
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

    override fun getApiEndpoint(): URL = URL("https://api.github.com/repos/%s/%s/releases/latest".format(this.user, this.repo))


    override fun getNewVersionUrl(): String = "https://github.com/%s/%s/releases/tag/%s".format(this.user, this.repo, this.remoteVersion.toString());

}
package com.github.sarhatabaot.kraken.core.updater;

import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author sarhatabaot
 */
public class GithubUpdateRunnable extends CheckForUpdateRunnable{
    private final String user;
    private final String repo;
    
    public GithubUpdateRunnable(String pluginVersion, Logger logger, String user, String repo) {
        super(pluginVersion, logger);
        this.user = user;
        this.repo = repo;
    }
    
    @Override
    public String getVersionFromRemote(final @NotNull JsonReader reader) {
        String version = "0.0.0";
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if(name.equals("tag_name")) {
                    version = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            return version;
        }
        return version;
    }

    @Override
    public URL getApiEndpoint() throws MalformedURLException {
        return new URL("https://api.github.com/repos/%s/%s/releases/latest".formatted(this.user,this.repo));
    }

    @Override
    public @Nullable String getNewVersionUrl() {
        return "https://github.com/%s/%s/releases/tag/%s".formatted(this.user, this.repo, this.remoteVersion.toString());
    }
}

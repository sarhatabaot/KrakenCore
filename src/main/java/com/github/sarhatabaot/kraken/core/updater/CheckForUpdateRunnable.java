package com.github.sarhatabaot.kraken.core.updater;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 * @author sarhatabaot
 */
//github, spigot variants
public abstract class CheckForUpdateRunnable extends BukkitRunnable {
    private final Logger logger;
    protected final DefaultArtifactVersion localVersion;
    protected DefaultArtifactVersion remoteVersion;

    protected CheckForUpdateRunnable(final String pluginVersion, final Logger logger) {
        this.logger = logger;
        this.localVersion = new DefaultArtifactVersion(pluginVersion);
    }

    @Override
    public void run() {
        try {
            URLConnection urlConnection = getApiEndpoint().openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0"); //not sure about this. for now
            try(BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                Gson gson = new Gson();
                try(JsonReader reader = gson.newJsonReader(in)) {
                    this.remoteVersion = new DefaultArtifactVersion(getVersionFromRemote(reader));
                    if (this.localVersion.compareTo(remoteVersion) < 0) {
                        logNewVersion();
                    }
                }
            }
        } catch (IOException e) {
            LoggerUtil.logWarningException(e);
        }
    }

    // Will return the version from the remote server
    public abstract String getVersionFromRemote(JsonReader reader);

    //API Endpoint
    public abstract URL getApiEndpoint() throws MalformedURLException;

    @Nullable
    public abstract String getNewVersionUrl();

    public void logNewVersion() {
        logger.info(() -> "New version available: %s, Current version: %s".formatted(remoteVersion.toString(), localVersion.toString()));
        logger.info(() -> "Get it at: %s".formatted(getNewVersionUrl()));
    }
}

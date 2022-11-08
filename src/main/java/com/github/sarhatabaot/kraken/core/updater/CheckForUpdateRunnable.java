package com.github.sarhatabaot.kraken.core.updater;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author sarhatabaot
 */
//github, spigot variants
public abstract class CheckForUpdateRunnable extends BukkitRunnable {
    private final JavaPlugin plugin;
    protected final DefaultArtifactVersion localVersion;
    protected DefaultArtifactVersion remoteVersion;

    protected CheckForUpdateRunnable(final @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.localVersion = new DefaultArtifactVersion(plugin.getDescription().getVersion());
    }

    @Override
    public void run() {
        try {
            URLConnection urlConnection = getApiEndpoint().openConnection();
            try(BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                Gson gson = new Gson();
                JsonReader reader = gson.newJsonReader(in);
                this.remoteVersion = new DefaultArtifactVersion(getVersionFromRemote(reader));
                if (this.localVersion.compareTo(remoteVersion) < 0) {
                    logNewVersion();
                }
            }
        } catch (IOException e) {
            LoggerUtil.logWarningException(e);
        }
    }

    // Will return the version from the remote server
    public abstract String getVersionFromRemote(JsonReader reader);

    //API Endpoint
    public abstract URL getApiEndpoint();

    @Nullable
    public abstract String getNewVersionUrl();

    public void logNewVersion() {
        plugin.getLogger().info(() -> "New version available: %s, Current version: %s".formatted(remoteVersion.toString(), localVersion.toString()));
        plugin.getLogger().info(() -> "Get it at: %s".formatted(getNewVersionUrl()));
    }
}

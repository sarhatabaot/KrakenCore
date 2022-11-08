package com.github.sarhatabaot.kraken.core.updater;

import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

/**
 * @author sarhatabaot
 */
public class SpigotUpdateRunnable extends CheckForUpdateRunnable{
    public SpigotUpdateRunnable(final @NotNull JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getVersionFromRemote(final JsonReader reader) {
        return null;
    }

    @Override
    public URL getApiEndpoint() {
        return null;
    }

    @Override
    public @Nullable String getNewVersionUrl() {
        return null;
    }
}

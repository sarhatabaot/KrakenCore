package com.github.sarhatabaot.kraken.core.updater;

import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author sarhatabaot
 */
public class SpigotUpdateRunnable extends CheckForUpdateRunnable {
    private final String resourceId;

    public SpigotUpdateRunnable(final @NotNull JavaPlugin plugin, final String resourceId) {
        super(plugin);
        this.resourceId = resourceId;
    }

    @Override
    public String getVersionFromRemote(final @NotNull JsonReader reader) {
        String version = "0.0.0";
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name")) {
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
        return new URL("https://api.spiget.org/v2/resources/%s/versions/latest".formatted(this.resourceId));
    }

    @Override
    public @Nullable String getNewVersionUrl() {
        return "https://api.spiget.org/v2/resources/%s/versions/latest/download".formatted(this.resourceId);
    }
}

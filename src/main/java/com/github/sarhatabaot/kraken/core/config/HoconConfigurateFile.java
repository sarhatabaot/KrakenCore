package com.github.sarhatabaot.kraken.core.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author sarhatabaot
 */
public abstract class HoconConfigurateFile <T extends JavaPlugin> extends CommentedConfigurateFile<T, HoconConfigurationLoader, HoconConfigurationLoader.Builder> {
    protected HoconConfigurateFile(@NotNull final T plugin, final String resourcePath, final String fileName, final String folder)  throws ConfigurateException {
        super(plugin, resourcePath, fileName, folder);
    }

    @Override
    protected HoconConfigurationLoader.Builder loadBuilder() {
        return HoconConfigurationLoader.builder().
                path(Paths.get(folder+ File.separator+fileName));
    }
}

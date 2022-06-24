package com.github.sarhatabaot.kraken.core.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author sarhatabaot
 */
public abstract class YamlConfigurateFile<T extends JavaPlugin> extends CommentedConfigurateFile<T, YamlConfigurationLoader, YamlConfigurationLoader.Builder> {
    public YamlConfigurateFile(@NotNull final T plugin, final String resourcePath, final String fileName, final String folder) throws ConfigurateException {
        super(plugin, resourcePath, fileName, folder);
    }

    @Override
    protected YamlConfigurationLoader.Builder loadBuilder() {
        return YamlConfigurationLoader.builder()
                .path(Paths.get(folder+ File.separator+fileName)).nodeStyle(NodeStyle.BLOCK);
    }
}

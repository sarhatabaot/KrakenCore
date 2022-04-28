package com.github.sarhatabaot.kraken.core.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Paths;

/**
 * @author sarhatabaot
 */
public abstract class ConfigurateFile<T extends JavaPlugin> extends ConfigFile<T>{
    protected final YamlConfigurationLoader.Builder loaderBuilder = YamlConfigurationLoader.builder().
            path(Paths.get(folder+"/"+fileName));
    protected final YamlConfigurationLoader loader;
    protected CommentedConfigurationNode rootNode;

    protected ConfigurateFile(@NotNull final T plugin, final String resourcePath, final String fileName, final String folder)  throws ConfigurateException {
        super(plugin, resourcePath, fileName, folder);
        preLoaderBuild();
        this.loader = loaderBuilder.build();
        this.rootNode = loader.load();

        this.saveDefaultConfig();
        initValues();
        plugin.getLogger().info(() -> "Loading "+fileName);
    }

    protected abstract void initValues() throws ConfigurateException;

    protected abstract void preLoaderBuild();

    @Override
    public void reloadConfig()  {
        try {
            this.rootNode = loader.load();
            initValues();
        } catch (ConfigurateException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

}

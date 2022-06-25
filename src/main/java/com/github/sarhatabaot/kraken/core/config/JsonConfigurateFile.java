package com.github.sarhatabaot.kraken.core.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author sarhatabaot
 */
public abstract class JsonConfigurateFile<T extends JavaPlugin> extends ConfigFile<T>{

    protected final GsonConfigurationLoader loader;
    protected final GsonConfigurationLoader.Builder loaderBuilder;

    protected BasicConfigurationNode rootNode;
    protected Transformation transformation;

    protected JsonConfigurateFile(@NotNull final T plugin, final String resourcePath, final String fileName, final String folder) throws ConfigurateException {
        super(plugin, resourcePath, fileName, folder);
        this.loaderBuilder = GsonConfigurationLoader.builder();

        builderOptions();
        this.loader = loaderBuilder.path(Paths.get(folder + File.separator + file)).build();
        this.rootNode = loader.load();

        this.saveDefaultConfig();
        this.transformation = getTransformation();
        if (this.transformation != null) {
            loader.save(this.transformation.updateNode(rootNode));
            this.rootNode = loader.load();
        }

        initValues();
        plugin.getLogger().info(() -> "Loading " + fileName);
    }

    protected abstract void initValues() throws ConfigurateException;

    protected abstract void builderOptions();

    protected abstract Transformation getTransformation();

    @Override
    public void reloadConfig() {
        try {
            this.rootNode = loader.load();
            initValues();
        } catch (ConfigurateException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
}

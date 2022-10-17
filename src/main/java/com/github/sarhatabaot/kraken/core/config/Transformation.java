package com.github.sarhatabaot.kraken.core.config;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;

import static org.spongepowered.configurate.NodePath.path;

//TODO we want this file to have a java 8 version for farmassist
public abstract class Transformation {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //Adds an initial config version
    public ConfigurationTransformation initialTransformation() {
        return ConfigurationTransformation.builder()
                .addAction(path("", ConfigurationTransformation.WILDCARD_OBJECT), (path, value) -> {
                    value.node("config-version").set(0);

                    return null; // don't move the value
                })
                .build();
    }

    public abstract int getLatestVersion();

    protected abstract ConfigurationTransformation.Versioned create();

    /**
     * Apply the transformations to a node.
     *
     * <p>This method also prints information about the version update that
     * occurred</p>
     *
     * @param node the node to transform
     * @param <N> node type
     * @return provided node, after transformation
     */
    public <N extends ConfigurationNode> N updateNode(final @NotNull N node) throws ConfigurateException {
        if (!node.virtual()) { // we only want to migrate existing data
            final ConfigurationTransformation.Versioned transformation = create();
            final int startVersion = transformation.version(node);
            transformation.apply(node);
            final int endVersion = transformation.version(node);
            if (startVersion != endVersion) { // we might not have made any changes
                logger.info(String.format("Updated config schema from %d to %d",startVersion, endVersion));
            }
        }
        return node;
    }
    
}
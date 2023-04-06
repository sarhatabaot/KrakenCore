package com.github.sarhatabaot.kraken.core.file;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author sarhatabaot
 */
public class FileUtil {
    private FileUtil() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * plugin.getClass().getProtectionDomain().getCodeSource()
     */
    public static @NotNull List<String> getFileNamesInJar(@NotNull CodeSource source, Predicate<ZipEntry> fileCondition) {
        List<String> fileNames = new ArrayList<>();
        URL jar = source.getLocation();
        try (ZipInputStream zip = new ZipInputStream(jar.openStream())) {
            while (true) {
                ZipEntry entry = zip.getNextEntry();
                if (entry == null)
                    break;
                if (fileCondition.test(entry)) {
                    fileNames.add(entry.getName());
                }
            }
        } catch (IOException e) {
            LoggerUtil.logSevereException(e);
        }
        return fileNames;
    }
    
    public static void saveFileFromJar(JavaPlugin plugin, final String resourcePath, final String fileName, final File folder) {
        File file = new File(folder, fileName);
        
        if (!file.exists()) {
            plugin.saveResource(resourcePath + fileName, false);
        }
        
    }
}

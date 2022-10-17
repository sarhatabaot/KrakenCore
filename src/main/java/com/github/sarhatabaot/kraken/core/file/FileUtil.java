package com.github.sarhatabaot.kraken.core.file;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author sarhatabaot
 */
public class FileUtil {
    public static void saveFileFromJar(JavaPlugin plugin, final String destinationPath, final String fileName, final File folder, final boolean replace) {
        File file = new File(folder, fileName);

        if (!file.exists()) {
            plugin.saveResource(destinationPath + fileName, replace);
        }
    }


    /**
     * @param plugin The Plugin
     * @param resourcePath Resource path inside the path, if any.
     * @param filenameFilter File name filter, .conf or .yml etc
     * @return The list of files.
     */
    public static List<String> getFileNamesFromJar(JavaPlugin plugin, final String resourcePath, FilenameFilter filenameFilter) {
        List<String> fileNames = new ArrayList<>();
        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            try(ZipInputStream zip = new ZipInputStream(jar.openStream())) {
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    //todo filename filter here
                    if (name.startsWith("menus/") && name.endsWith(".conf")) {
                        fileNames.add(name);
                    }
                }
            } catch (IOException e) {
                //
            }
        }
        return fileNames;
    }
}

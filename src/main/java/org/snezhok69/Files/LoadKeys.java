package org.snezhok69.Files;

import org.bukkit.configuration.file.FileConfiguration;

public class LoadKeys {

    public static String language;


    public static void loadKeys(FileConfiguration config) {
        language = config.getString("Language");
    }
}

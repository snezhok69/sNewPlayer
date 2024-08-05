package org.snezhok69.Files;

import org.bukkit.configuration.file.YamlConfiguration;
import org.snezhok69.DifferentMethods.Variables;

import java.io.File;

public class LoadLanguageFile {
    public YamlConfiguration langConfig;
    public void loadLanguageFile() {
        String language = LoadKeys.language;
        File langFile = new File(Variables.instance.getDataFolder() + "/Lang", language + ".yml");
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
    public YamlConfiguration getLangFile() {
        return langConfig;
    }
}
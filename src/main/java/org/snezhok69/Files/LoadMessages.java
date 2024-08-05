package org.snezhok69.Files;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class LoadMessages {
    public static List<String> newplayerserver;
    public static List<String> newVersionMessage;
    public static List<String> PluginEnabledMessage;
    public static List<String> PluginDisabledMessage;

    public static void loadMessages(YamlConfiguration langFile) {
        newplayerserver = langFile.getStringList("messages.newplayerserver");
        PluginEnabledMessage = langFile.getStringList("messages.Plugin-Enabled-Message");
        PluginDisabledMessage = langFile.getStringList("messages.Plugin-Disabled-Message");
    }
}

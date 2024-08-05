package org.snezhok69.Version;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.snezhok69.DifferentMethods.LoggerUtility;
import org.snezhok69.DifferentMethods.TranslateRGBColors;
import org.snezhok69.DifferentMethods.Variables;
import org.snezhok69.Files.LoadMessages;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StartPluginCheckingNewVersion {
    public static void startPluginCheckingNewVersion() {
        try {
            CompletableFuture.runAsync(() -> {
                try {
                    URL url = new URL("https://gitlab.com/snezh0k69/sRandomRTP/-/raw/main/VERSION");
                    Variables.connectionVersion = (HttpURLConnection) url.openConnection();
                    Variables.connectionVersion.setConnectTimeout(10000);
                    Variables.connectionVersion.setRequestMethod("GET");
                    int responseCode = Variables.connectionVersion.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String latestVersion = reader.readLine();
                        reader.close();
                        if (latestVersion != null) {
                            latestVersion = latestVersion.trim();
                            PluginDescriptionFile description = Variables.getInstance().getDescription();
                            String pluginVersion = description.getVersion();
                            if (!latestVersion.equals(pluginVersion)) {
                                List<String> formattedMessage = LoadMessages.newVersionMessage;
                                for (String line : formattedMessage) {
                                    line = line.replace("%new-CommandVersion%", latestVersion);
                                    line = line.replace("%old-CommandVersion%", pluginVersion);
                                    line = TranslateRGBColors.translateRGBColors(line);
                                    String formattedLine = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', line);
                                    Bukkit.getConsoleSender().sendMessage(formattedLine);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage("§cError when checking new plugin version: §6" + e.getMessage());
                }
            });
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String callingClassName = stackTrace[2].getClassName();
            LoggerUtility.loggerUtility(callingClassName, e);
        }
    }
}
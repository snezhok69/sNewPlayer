package org.snezhok69;

import com.tcoded.folialib.FoliaLib;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.snezhok69.Commands.CommandArgs;
import org.snezhok69.Commands.onTabCompletes;
import org.snezhok69.DataBase.DatabaseManager;
import org.snezhok69.DifferentMethods.LoggerUtility;
import org.snezhok69.DifferentMethods.PlayerJoinListener;
import org.snezhok69.DifferentMethods.TranslateRGBColors;
import org.snezhok69.DifferentMethods.Variables;
import org.snezhok69.Files.*;
import org.snezhok69.Version.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            //
            Variables.instance = this;
            Variables.foliaLib = new FoliaLib(this);
            long startTime = System.currentTimeMillis();
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §c>==========================================<");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §ePlugin initialization started...");
            //
            Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eVersion check...");
            if (CheckingServerVersion.checkingServerVersion()) {
                return;
            }
            if (Bukkit.getServer().getName().equalsIgnoreCase("Folia")) {
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §cUsed Folia there may be errors or bugs!...");
                //
                // if (Bukkit.getServer().getName().equalsIgnoreCase("Folia")) {
                //     Variables.pluginToggle = true;
                //     Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §cFolia core is not supported, plugin is disabled!");
                //     Bukkit.getPluginManager().disablePlugin(this);
                //     return;
                // } else {
                // }
                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eChecking installed PlaceHolderAPI...");
                if (CheckingInstalledPlaceHolderAPI.checkingInstalledPlaceHolderAPI()) {
                }
                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eLoading database...");
                // Настройка подключения к базе данных SQLite
                String url = "jdbc:sqlite:" + getDataFolder() + "/playerdata.db";

                try {
                    Variables.connection = DriverManager.getConnection(url);
                    getLogger().info("Подключение к базе данных установлено.");
                    // Создание таблицы, если ее нет
                    new DatabaseManager(Variables.connection).createTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                    getLogger().severe("Не удалось подключиться к базе данных.");
                }

                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eLoading events...");
                getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eCreating files...");
                FilesCreate filesCreate = new FilesCreate();
                List<String> createdFiles = filesCreate.filesCreate();
                long createTime = System.currentTimeMillis();
                for (String message : createdFiles) {
                    if (message != null && !createdFiles.contains(message)) {
                        long elapsedTime = System.currentTimeMillis() - createTime;
                        String formattedLine = String.format(Variables.pluginName + " §8- §aFile %s successfully created §6(%d ms)", message, elapsedTime);
                        Bukkit.getConsoleSender().sendMessage(formattedLine);
                    }
                }
                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eUpdating files...");
                FilesUpdate filesUpdate = new FilesUpdate();
                List<String> filesUpdates = filesUpdate.filesUpdate();
                long updateTime = System.currentTimeMillis();
                for (String message : filesUpdates) {
                    long elapsedTime = System.currentTimeMillis() - updateTime;
                    String formattedLine = String.format(Variables.pluginName + " §8- §aFile %s successfully updated §6(%d ms)", message, elapsedTime);
                    Bukkit.getConsoleSender().sendMessage(formattedLine);
                }
                //
                File configFile = new File(getDataFolder(), "config.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                LoadKeys.loadKeys(config);
                //
                //CheckingFile checkingFile = new CheckingFile();
                //checkingFile.compareLanguageFiles("ar_sa.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("custom_messages.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("de_de.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("en_us.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("es_es.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("fr_fr.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("it_it.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("ja_jp.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("ko_kr.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("pl_pl.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("pt_br.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("uk_ua.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("vi_vn.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("zh_cn.yml", "ru_ru.yml");
                //checkingFile.compareLanguageFiles("tr_tr.yml", "ru_ru.yml");
                //
                LoadLanguageFile loadLanguageFile = new LoadLanguageFile();
                loadLanguageFile.loadLanguageFile();
                YamlConfiguration langFile = loadLanguageFile.getLangFile();
                LoadMessages.loadMessages(langFile);
                //;
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eLoading commands...");
                Map<String, Map<String, Object>> commands = getDescription().getCommands();
                if (commands != null) {
                    for (String commandName : commands.keySet()) {
                        getCommand(commandName).setExecutor(new CommandArgs());
                        getCommand(commandName).setTabCompleter(new onTabCompletes());
                    }
                }
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eRunning tasks...");
                StartPluginCheckingNewVersion.startPluginCheckingNewVersion();
                IsOutdatedByMultipleVersionsTask.isOutdatedByMultipleVersionsTask();
                AutoCheckingVersion.autoCheckingVersion();
                //
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §eSending anonymous statistics...");
                try {
                    Metrics metrics = new Metrics(this, 21603);
                    metrics.addCustomChart(new Metrics.DrilldownPie("lang", () -> {
                        Map<String, Map<String, Integer>> map = new HashMap<>();
                        String language = Variables.getInstance().getConfig().getString("Language");

                        if (language != null && !language.trim().isEmpty()) {
                            // Извлекаем основной язык из формата ru_ru
                            String[] parts = language.split("_");
                            String mainLanguage = parts[0];

                            Map<String, Integer> entry = new HashMap<>();
                            entry.put(mainLanguage, 1);
                            map.put(mainLanguage, entry);
                        }
                        return map;
                    }));
                } catch (Throwable e) {
                    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                    String callingClassName = stackTrace[2].getClassName();
                    LoggerUtility.loggerUtility(callingClassName, e);
                }
                //
                for (String line : LoadMessages.PluginEnabledMessage) {
                    String serverVersions = Bukkit.getServer().getVersion();
                    String osName = System.getProperty("os.name");
                    String osVersion = System.getProperty("os.version");
                    String osArch = System.getProperty("os.arch");
                    String javaVersion = System.getProperty("java.version");
                    long endTime = System.currentTimeMillis();
                    long enabledPluginTime = endTime - startTime;
                    line = line.replace("%mc%", enabledPluginTime + "")
                            .replace("%server-version%", serverVersions)
                            .replace("%os-version%", osName + " " + osVersion + " (" + osArch + ")")
                            .replace("%java-version-server%", javaVersion);
                    String formattedLine = TranslateRGBColors.translateRGBColors(line);
                    Bukkit.getConsoleSender().sendMessage(formattedLine);
                }
                Bukkit.getConsoleSender().sendMessage(Variables.pluginName + " §8- §c>==========================================<");
                Bukkit.getConsoleSender().sendMessage("");
                //
            }
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String callingClassName = stackTrace[2].getClassName();
            LoggerUtility.loggerUtility(callingClassName, e);
        }
    }

    @Override
    public void onDisable() {
        if (!Variables.pluginToggle) {
            try {
                for (String line : LoadMessages.PluginDisabledMessage) {
                    long endTime = System.currentTimeMillis();
                    long startTime = System.currentTimeMillis();
                    long disabledPluginTime = endTime - startTime;
                    line = line.replace("%mc%", disabledPluginTime + "");
                    String formattedLines = TranslateRGBColors.translateRGBColors(line);
                    Bukkit.getConsoleSender().sendMessage(formattedLines);
                }
                if (Variables.connection != null) {
                    try {
                        Variables.connection.close();
                        getLogger().info("Соединение с базой данных закрыто.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable e) {
            }
        }
    }
    public static Connection getConnection() {
        return Variables.connection;
    }
}





package org.snezhok69.DifferentMethods;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.snezhok69.Main;

import java.net.HttpURLConnection;
import java.util.regex.Pattern;

public class Variables {
    //
    public static Main instance;
    public static Main getInstance() {
        return instance;
    }
    //
    public static Pattern RGB_PATTERN = Pattern.compile("&#([0-9a-fA-F]{6})");
    public static String pluginName = "§a[sRandomRTP]";
    //
    public static boolean pluginToggle = false;
    //
    public static WrappedTask autoCheckVersionTask;
    //
    public static FoliaLib foliaLib;
    public static FoliaLib getFoliaLib() {
        return foliaLib;
    }
    //
    public static HttpURLConnection connection = null;
    public static boolean isReloaded = false;
}

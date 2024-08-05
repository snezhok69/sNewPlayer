package org.snezhok69.Files;



import org.snezhok69.DifferentMethods.LoggerUtility;
import org.snezhok69.DifferentMethods.Variables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUpdate {
    public List<String> filesUpdate() {
        try {
        List<String> updatedFiles = new ArrayList<>();
        String[] filePaths = {
                "config.yml",
                "Lang/en.yml",
                "Lang/ru.yml",
        };
        File dataFolder = Variables.getInstance().getDataFolder();
        for (String filePath : filePaths) {
            File file = new File(dataFolder, filePath);
            try {
                boolean updated = ConfigUpdater.update(filePath, file);
                if (updated) {
                    updatedFiles.add(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updatedFiles;
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String callingClassName = stackTrace[2].getClassName();
            LoggerUtility.loggerUtility(callingClassName, e);
        }
        return java.util.Collections.emptyList();
    }
}
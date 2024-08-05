package org.snezhok69.Files;


import org.snezhok69.DifferentMethods.LoggerUtility;
import org.snezhok69.DifferentMethods.Variables;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesCreate {
    public List<String> filesCreate() {
        try {
        List<String> createdFiles = new ArrayList<>();
        File dataFolder = Variables.getInstance().getDataFolder();
        String[] fileNames = {
                "config.yml",
                "Lang/en.yml",
                "Lang/ru.yml",
        };
        for (String fileName : fileNames) {
            File file = new File(dataFolder, fileName);
            if (!file.exists()) {
                Variables.getInstance().saveResource(fileName, false);
                createdFiles.add(fileName);
            }
        }
        return createdFiles;
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String callingClassName = stackTrace[2].getClassName();
            LoggerUtility.loggerUtility(callingClassName, e);
        }
        return java.util.Collections.emptyList();
    }
}
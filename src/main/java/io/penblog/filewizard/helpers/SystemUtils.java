package io.penblog.filewizard.helpers;

import java.io.File;
import java.io.IOException;

public class SystemUtils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().contains("WIN");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toUpperCase().contains("MAC");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toUpperCase().contains("LINUX");
    }

    public static File getUserDir() {
        return new File(System.getProperty("user.home"));
    }

    public static File getAppDataDir() throws IOException {
        File appDataDir;
        if (SystemUtils.isWindows()) {
            String appDir = System.getenv("AppData");
            appDataDir = new File(appDir + File.separator + ".FileWizard");
        } else {
            appDataDir = new File(System.getProperty("user.home") + File.separator + ".FileWizard");
        }
        if (!appDataDir.exists()) {
            boolean result = appDataDir.mkdir();
            if (!result) throw new IOException("Cannot create data directory.");
        }
        return appDataDir;
    }
}

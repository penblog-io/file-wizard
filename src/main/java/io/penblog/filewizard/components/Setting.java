package io.penblog.filewizard.components;

import io.penblog.filewizard.helpers.SystemUtils;

import java.io.*;

public class Setting {
    public static final String LAST_OPEN_LOCATION = "last_open_location";
    public static final String NOTIFY_AVAILABLE_UPDATE = "notify_available_update";
    public static final String THEME_MODE = "theme_mode";

    protected File lastOpenDirectory;
    protected boolean notifyAvailableUpdate = true;
    protected String themeMode = "dark";

    public File getLastOpenDirectory() {
        return lastOpenDirectory;
    }

    public void setLastOpenDirectory(File lastOpenDirectory) {
        this.lastOpenDirectory = lastOpenDirectory;
    }

    public void setNotifyAvailableUpdate(boolean notify) {
        notifyAvailableUpdate = notify;
    }

    public boolean isNotifyAvailableUpdate() {
        return notifyAvailableUpdate;
    }


    public void setThemeMode(String mode) {
        themeMode = mode;
    }

    public String getThemeMode() {
        return themeMode;
    }

    @Override
    public String toString() {
        return LAST_OPEN_LOCATION + "=" + getLastOpenDirectory() + newLine() +
                NOTIFY_AVAILABLE_UPDATE + "=" + (isNotifyAvailableUpdate() ? "1" : "0") + newLine() +
                THEME_MODE + "=" + getThemeMode();
    }

    public String[] toArray() {
        return new String[]{
                LAST_OPEN_LOCATION + "=" + getLastOpenDirectory(),
                NOTIFY_AVAILABLE_UPDATE + "=" + (isNotifyAvailableUpdate() ? "1" : "0"),
                THEME_MODE + "=" + getThemeMode()
        };
    }

    protected String newLine() {
        return SystemUtils.isWindows() ? "\\r\\n" : "\\n";
    }
}

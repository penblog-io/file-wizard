package io.penblog.filewizard.components;

import io.penblog.filewizard.helpers.SystemUtils;

import java.io.*;

/**
 * Setting class, after the application loaded, the setting file will also be loaded and store in Setting object.
 */
public class Setting {
    public static final String LAST_OPEN_LOCATION = "last_open_location";
    public static final String NOTIFY_AVAILABLE_UPDATE = "notify_available_update";
    public static final String THEME_MODE = "theme_mode";
    public static final String THEME_MODE_LIGHT = "light";
    public static final String THEME_MODE_DARK = "dark";

    protected File lastOpenDirectory;
    protected boolean notifyAvailableUpdate = true;
    protected String themeMode = THEME_MODE_DARK;

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


    /**
     * TODO: implement Dark Theme
     */
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

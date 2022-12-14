package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Setting;
import io.penblog.filewizard.helpers.SystemUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingService extends BaseService {

    protected final Setting setting;

    public SettingService(Logger logger) {
        super(logger);

        // set default setting
        setting = new Setting();
        setting.setLastOpenDirectory(SystemUtils.getUserDir());

        // update setting from settings.ini file
        try {
            File settingFile = getSettingFile();

            if (!settingFile.exists()) {
                if (!settingFile.createNewFile()) throw new IOException("Cannot create settings.ini file.");
            }
            BufferedReader reader = new BufferedReader(new FileReader(settingFile));

            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null && lineCount < 100) {
                String[] pair = line.split("=");
                if (pair.length >= 2) {
                    String key = pair[0];
                    StringBuilder valueBuilder = new StringBuilder();
                    for (int i = 1; i < pair.length; i++) {
                        if (i > 1) valueBuilder.append("=");
                        valueBuilder.append(pair[i]);
                    }
                    String value = valueBuilder.toString();

                    switch (key) {
                        case Setting.LAST_OPEN_LOCATION -> {
                            File lastOpenDirectory = new File(value);
                            if (lastOpenDirectory.exists()) setting.setLastOpenDirectory(lastOpenDirectory);
                        }
                        case Setting.NOTIFY_AVAILABLE_UPDATE -> setting.setNotifyAvailableUpdate(value.equals("1"));
                    }

                }
                lineCount++;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastOpenDirectory(File directory) {
        setting.setLastOpenDirectory(directory);
    }

    public void setNotifyAvailableUpdate(boolean notify) {
        setting.setNotifyAvailableUpdate(notify);
    }

    /**
     * Write setting to settings.ini file
     */
    public void write() {
        try {
            File settingFile = getSettingFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(settingFile));
            writer.write("");
            for (String s : setting.toArray()) {
                writer.append(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Setting setting() {
        return setting;
    }


    /**
     * Loading settings.ini file, create if not exists
     */
    protected File getSettingFile() throws IOException {
        File settingFile = new File(SystemUtils.getAppDataDir().getAbsolutePath() +
                File.separator + "settings.ini");
        if (!settingFile.exists()) {
            if (settingFile.createNewFile()) {
                getLogger().log(Level.WARNING, "Cannot create settings.ini file.");
            }
        }
        return settingFile;
    }
}

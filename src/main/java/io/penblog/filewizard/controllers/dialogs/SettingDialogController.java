package io.penblog.filewizard.controllers.dialogs;

import io.penblog.filewizard.components.Setting;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;


public class SettingDialogController {

    @FXML private RadioButton rdoLight;
    @FXML private RadioButton rdoDark;

    private Setting setting;

    @FXML
    public void onLightClick() {
        setting.setThemeMode(Setting.THEME_MODE_LIGHT);
    }

    @FXML
    public void onDarkClick() {
        setting.setThemeMode(Setting.THEME_MODE_DARK);
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
        if(Setting.THEME_MODE_LIGHT.equals(setting.getThemeMode())) {
            rdoLight.setSelected(true);
        }
        else {
            rdoDark.setSelected(true);
        }
    }

    public Setting getSetting() {
        return setting;
    }

}

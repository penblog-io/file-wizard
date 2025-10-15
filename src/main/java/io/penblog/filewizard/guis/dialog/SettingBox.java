package io.penblog.filewizard.guis.dialog;

import io.penblog.filewizard.components.Setting;
import io.penblog.filewizard.controllers.dialogs.SettingDialogController;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.services.SettingService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import jfxtras.styles.jmetro.JMetro;

import java.io.IOException;
import java.util.Optional;

public class SettingBox {
    private final Dialog<Setting> dialog;
    private static SettingBox settingBox;
    private final SettingService settingService;

    private SettingBox() {
        dialog = new Dialog<>();
        settingService = ServiceContainer.getSettingService();
        JMetro jMetro = new JMetro(dialog.getDialogPane().getScene(), settingService.getTheme());
        settingService.setSettingJMetro(jMetro);

        // Load FXML content
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/dialogs/setting.fxml"));
        try {
            Node content = loader.load();
            SettingDialogController controller = loader.getController();
            controller.setSetting(settingService.getSetting());

            // Add buttons
            ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            // Set FXML content into dialog
            dialog.getDialogPane().setContent(content);

            // Convert result when OK is pressed
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return controller.getSetting();
                }
                return null;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setTitle("Settings");
    }

    public static SettingBox getInstance() {
        if (settingBox == null) settingBox = new SettingBox();
        return settingBox;
    }

    public void show() {
        // Show dialog and capture result
        Optional<Setting> result = dialog.showAndWait();
        result.ifPresent(settingService::save);

        settingService.reApplyTheme();
    }

}

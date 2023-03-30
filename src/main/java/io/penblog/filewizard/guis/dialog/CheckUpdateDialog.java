package io.penblog.filewizard.guis.dialog;

import io.penblog.filewizard.services.SettingService;
import io.penblog.filewizard.services.WebService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.util.Optional;
import java.util.Properties;


public class CheckUpdateDialog {

    private final Dialog<Boolean> dialog = new Dialog<>();
    private final ButtonType downloadButtonType;
    private final SettingService settingService;

    public CheckUpdateDialog(Properties properties, SettingService settingService, WebService webService) {

        new JMetro(dialog.getDialogPane().getScene(), Style.LIGHT);

        this.settingService = settingService;

        webService.hasUpdate();
        downloadButtonType = new ButtonType("Download", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(downloadButtonType, ButtonType.CANCEL);

        dialog.setTitle("New Update");
        VBox layout = new VBox();
        layout.setPrefHeight(60);
        layout.setPrefWidth(400);

        Label message = new Label("A new version of File Wizard is now available.\n" +
                "\nCurrent version: " + properties.getProperty("version") +
                "\nNew version: " + webService.version());
        message.setMinHeight(70);
        message.setPadding(new Insets(5, 0, 0, 0));
        layout.getChildren().add(message);

        CheckBox doNotShowCheckBox = new CheckBox("Do not show this message again.");
        doNotShowCheckBox.setPadding(new Insets(10, 0, 0, 0));
        layout.getChildren().add(doNotShowCheckBox);

        dialog.getDialogPane().setContent(layout);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == downloadButtonType) {
                webService.open(properties.getProperty("downloadUrl"));
            }
            return doNotShowCheckBox.isSelected();
        });
    }

    public void show() {
        Optional<Boolean> result = dialog.showAndWait();
        result.ifPresent(doNotShow -> {
            if (doNotShow) {
                settingService.setNotifyAvailableUpdate(false);
                settingService.write();
            }
        });
    }
}

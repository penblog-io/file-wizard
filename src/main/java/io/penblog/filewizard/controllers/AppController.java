package io.penblog.filewizard.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.services.WebService;

import java.util.Properties;

public class AppController {

    @FXML
    Hyperlink hlDownload;

    @FXML
    public void initialize() {
        Properties properties = ServiceContainer.getProperties();
        WebService webService = ServiceContainer.getWebService();

        hlDownload.setVisible(false);

        if (webService.hasUpdate()) {
            hlDownload.setText("New version is available " + webService.version());
            hlDownload.setVisible(true);
            hlDownload.setOnAction(e -> webService.open(properties.getProperty("downloadUrl")));
        }
    }
}

package io.penblog.filewizard.guis.dialog;

import javafx.scene.control.Alert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class InfoBox {
    private final Alert alertBox;
    private static InfoBox infoBox;

    private InfoBox() {
        alertBox = new Alert(Alert.AlertType.INFORMATION);
        new JMetro(alertBox.getDialogPane().getScene(), Style.LIGHT);
    }

    public static InfoBox getInstance() {
        if (infoBox == null) infoBox = new InfoBox();
        return infoBox;
    }

    public void show(String title, String header, String body) {
        alertBox.setTitle(title);
        alertBox.setHeaderText(header);
        alertBox.setContentText(body);
        alertBox.show();
    }

}

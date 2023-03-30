package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.RenamerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;

/**
 * Sequence class controller manages "Sequence" option on the UI
 */
public class Sequence {

    @FXML
    private AnchorPane paneSequence;
    @FXML
    private TextField txtStart, txtInterval, txtMask;

    private RenamerService renamerService;
    private RenameState renameState;
    private String type;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupSequencePane();
        setupStartText();
        setupIntervalText();
        setupMaskText();
    }

    private void setupSequencePane() {
        paneSequence.setVisible(false);
        RenameState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneSequence.setVisible(RenameMethod.SEQUENCE == evt.getNewValue());
            }
        });
    }

    private void setupStartText() {
        txtStart.textProperty().addListener((observable, oldValue, newValue) -> preview());
    }

    private void setupIntervalText() {
        txtInterval.setText("1");
        txtInterval.textProperty().addListener((observable, oldValue, newValue) -> preview());
    }

    private void setupMaskText() {
        txtMask.textProperty().addListener((observable, oldValue, newValue) -> preview());
    }

    @FXML
    public void onTypeNumberClick() {
        type = "number";
        txtStart.setText("1");
        preview();
    }

    @FXML
    public void onTypeLetterClick() {
        type = "letter";
        txtStart.setText("A");
        preview();
    }

    private void preview() {
        String attribute = "";
        if (type != null) {
            attribute = "{sequence:" + type + "," + txtStart.getText() + "," + txtInterval.getText() + ",1";
            if (!txtMask.getText().isEmpty()) attribute += "," + txtMask.getText();
            attribute += "}";
        }

        renamerService.setOption(Option.RENAME_SEQUENCE_TEXT, attribute);
        renamerService.preview(RenameMethod.SEQUENCE);
        renameState.invalidateTableData();
    }

}

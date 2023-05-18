package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.RenamerService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;

/**
 * Append class controller manages "Append" option on the UI
 */
public class Append {

    @FXML
    private AnchorPane paneAppend;
    @FXML
    private TextField txtAppend;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupAppendPane();
        setupAppendText();
        setupAttributePane();
    }

    private void setupAppendPane() {
        paneAppend.setVisible(false);
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneAppend.setVisible(RenameMethod.APPEND == evt.getNewValue());
            }
        });
    }

    private void setupAppendText() {
        txtAppend.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtAppend.setText(oldValue);
                value = oldValue;
            }
            preview(value);
        });
    }

    private void setupAttributePane() {
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedAttributeFormat".equals(evt.getPropertyName())
                    && renameState.getSelectedMethod() == RenameMethod.APPEND) {
                txtAppend.setText(evt.getNewValue() == null ? "" : evt.getNewValue().toString());
            }
        });
    }

    private void preview(String value) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                renamerService.setOption(Option.RENAME_APPEND_TEXT, value);
                renamerService.preview(RenameMethod.APPEND);
                renameState.invalidateTableData();

                return null;
            }
        }).start();
    }
}

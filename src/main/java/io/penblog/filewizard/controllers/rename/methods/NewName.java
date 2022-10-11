package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.RenamerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;


public class NewName {

    @FXML
    private AnchorPane paneNewName;
    @FXML
    private TextField txtNewName;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupNewNamePane();
        setupNewNameText();
        setupAttributePane();
    }

    private void setupNewNamePane() {
        paneNewName.setVisible(false);
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneNewName.setVisible(RenameMethod.NEW_NAME == evt.getNewValue());
            }
        });
    }

    private void setupNewNameText() {
        txtNewName.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtNewName.setText(oldValue);
                value = oldValue;
            }
            preview(value);
        });
    }

    protected void setupAttributePane() {
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedAttributeFormat".equals(evt.getPropertyName())
                    && renameState.getSelectedMethod() == RenameMethod.NEW_NAME) {
                txtNewName.setText(evt.getNewValue() == null ? "" : evt.getNewValue().toString());
                preview(txtNewName.getText());
            }
        });
    }

    private void preview(String value) {
        renamerService.setOption(Option.RENAME_NEW_NAME_TEXT, value);
        renamerService.preview(RenameMethod.NEW_NAME);
        renameState.invalidateTableData();
    }
}

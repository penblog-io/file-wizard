package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.helpers.Files;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.RenamerService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;

public class Remove {

    @FXML
    private AnchorPane paneRemove;
    @FXML
    private TextField txtRemove;
    @FXML
    private CheckBox cboCaseInsensitive, cboRegex;
    private boolean isRegex = false, isCaseInsensitive = false;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupRemovePane();
        setupRemoveText();
        setupRegexComboBox();
        setupCaseInsensitiveComboBox();
    }

    private void setupRemovePane() {
        paneRemove.setVisible(false);
        RenameState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneRemove.setVisible(RenameMethod.REMOVE == evt.getNewValue());
            }
        });
    }

    private void setupRemoveText() {
        txtRemove.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtRemove.setText(oldValue);
                value = oldValue;
            }
            preview(value);
        });
    }

    private void setupRegexComboBox() {
        cboRegex.selectedProperty().addListener((observable, oldValue, newValue) -> {
            isRegex = cboRegex.isSelected();
            preview(txtRemove.getText());
        });
    }

    private void setupCaseInsensitiveComboBox() {
        cboCaseInsensitive.selectedProperty().addListener((observable, oldValue, newValue) -> {
            isCaseInsensitive = cboCaseInsensitive.isSelected();
            preview(txtRemove.getText());
        });
    }

    private void preview(String remove) {
        renamerService.setOption(Option.RENAME_REMOVE_TEXT, remove);
        renamerService.setOption(Option.RENAME_REMOVE_REGEX, isRegex);
        renamerService.setOption(Option.RENAME_REMOVE_CASE_INSENSITIVE, isCaseInsensitive);
        renamerService.preview(RenameMethod.REMOVE);
        renameState.invalidateTableData();
    }
}

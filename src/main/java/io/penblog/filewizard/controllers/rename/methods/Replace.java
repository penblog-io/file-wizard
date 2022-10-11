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

public class Replace {

    @FXML
    private AnchorPane paneReplace;
    @FXML
    private TextField txtFind, txtReplace;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupReplacePane();
        setupFindText();
        setupFindReplace();
        setupAttributePane();
    }

    private void setupReplacePane() {
        paneReplace.setVisible(false);
        RenameState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneReplace.setVisible(RenameMethod.REPLACE == evt.getNewValue());
            }
        });
    }

    private void setupFindText() {
        txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtFind.setText(oldValue);
                value = oldValue;
            }
            preview(value, txtReplace.getText());
        });
    }

    private void setupFindReplace() {
        txtReplace.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtReplace.setText(oldValue);
                value = oldValue;
            }
            preview(txtFind.getText(), value);
        });
    }

    private void setupAttributePane() {
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedAttributeFormat".equals(evt.getPropertyName())
                    && renameState.getSelectedMethod() == RenameMethod.REPLACE) {
                txtReplace.setText(evt.getNewValue() == null ? "" : evt.getNewValue().toString());
                preview(txtFind.getText(), txtReplace.getText());
            }
        });
    }


    private void preview(String find, String replace) {
        renamerService.setOption(Option.RENAME_REPLACE_SEARCH, find);
        renamerService.setOption(Option.RENAME_REPLACE_TEXT, replace);
        renamerService.preview(RenameMethod.REPLACE);
        renameState.invalidateTableData();
    }


}

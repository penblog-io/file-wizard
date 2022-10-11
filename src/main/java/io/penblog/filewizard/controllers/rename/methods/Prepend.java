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

public class Prepend {

    @FXML
    private AnchorPane panePrepend;
    @FXML
    private TextField txtPrepend;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupPrependPane();
        setupPrependText();
        setupAttributePane();
    }

    private void setupPrependPane() {
        panePrepend.setVisible(false);
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                panePrepend.setVisible(RenameMethod.PREPEND == evt.getNewValue());
            }
        });
    }

    private void setupPrependText() {
        txtPrepend.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (Files.isInvalidFilename(newValue)) {
                txtPrepend.setText(oldValue);
                value = oldValue;
            }
            preview(value);
        });
    }

    private void setupAttributePane() {
        renameState.addPropertyChangeListener(evt -> {
            if ("selectedAttributeFormat".equals(evt.getPropertyName())
                    && renameState.getSelectedMethod() == RenameMethod.PREPEND) {
                txtPrepend.setText(evt.getNewValue() == null ? "" : evt.getNewValue().toString());
                preview(txtPrepend.getText());
            }
        });
    }

    private void preview(String value) {
        renamerService.setOption(Option.RENAME_PREPEND_TEXT, value);
        renamerService.preview(RenameMethod.PREPEND);
        renameState.invalidateTableData();
    }
}

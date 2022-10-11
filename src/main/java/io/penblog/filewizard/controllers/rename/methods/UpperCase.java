package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.enums.options.UpperCaseOption;
import io.penblog.filewizard.services.RenamerService;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;

public class UpperCase {

    @FXML
    private AnchorPane paneUpperCase;
    @FXML
    private RadioButton rdoAllCharacters, rdoSpecificCharacters;
    @FXML
    private TextField txtUpperCaseCharacters;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupUpperCasePane();
        setupAllCharactersRadioButton();
        setupSpecificCharactersRadioButton();
        setupSpecificCharactersText();
    }

    private void setupUpperCasePane() {
        paneUpperCase.setVisible(false);
        RenameState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneUpperCase.setVisible(RenameMethod.UPPERCASE == evt.getNewValue());
            }
        });
    }

    private void setupAllCharactersRadioButton() {
        rdoAllCharacters.selectedProperty().addListener((observable, oldValue, newValue) -> {
            txtUpperCaseCharacters.setDisable(true);
            renamerService.setOption(Option.RENAME_UPPERCASE_OPTION, UpperCaseOption.ALL_CHARACTERS);
            preview();
        });
    }

    private void setupSpecificCharactersRadioButton() {
        rdoSpecificCharacters.selectedProperty().addListener((observable, oldValue, newValue) -> {
            txtUpperCaseCharacters.setDisable(false);
            renamerService.setOption(Option.RENAME_UPPERCASE_OPTION, UpperCaseOption.SPECIFIC_CHARACTERS);
            preview();
        });
    }

    private void setupSpecificCharactersText() {
        txtUpperCaseCharacters.setDisable(true);
        txtUpperCaseCharacters.textProperty().addListener((observable, oldValue, newValue) -> preview());
    }

    private void preview() {
        renamerService.setOption(Option.RENAME_UPPERCASE_SPECIFIC_CHARACTERS_TEXT, txtUpperCaseCharacters.getText());
        renamerService.preview(RenameMethod.UPPERCASE);
        renameState.invalidateTableData();
    }
}

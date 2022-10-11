package io.penblog.filewizard.controllers.rename.methods;

import io.penblog.filewizard.enums.options.LowerCaseOption;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.RenamerService;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.RenameState;

public class LowerCase {

    @FXML
    private AnchorPane paneLowerCase;
    @FXML
    private RadioButton rdoAllCharacters, rdoSpecificCharacters;
    @FXML
    private TextField txtLowerCaseCharacters;
    private RenamerService renamerService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupLowerCasePane();
        setupAllCharactersRadioButton();
        setupSpecificCharactersRadioButton();
        setupSpecificCharactersText();
    }

    private void setupLowerCasePane() {
        paneLowerCase.setVisible(false);
        RenameState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneLowerCase.setVisible(RenameMethod.LOWERCASE == evt.getNewValue());
            }
        });
    }

    private void setupAllCharactersRadioButton() {
        rdoAllCharacters.selectedProperty().addListener((observable, oldValue, newValue) -> {
            txtLowerCaseCharacters.setDisable(true);
            renamerService.setOption(Option.RENAME_LOWERCASE_OPTION, LowerCaseOption.ALL_CHARACTERS);
            preview();
        });
    }

    private void setupSpecificCharactersRadioButton() {
        rdoSpecificCharacters.selectedProperty().addListener((observable, oldValue, newValue) -> {
            txtLowerCaseCharacters.setDisable(false);
            renamerService.setOption(Option.RENAME_LOWERCASE_OPTION, LowerCaseOption.SPECIFIC_CHARACTERS);
            preview();
        });
    }

    private void setupSpecificCharactersText() {
        txtLowerCaseCharacters.setDisable(true);
        txtLowerCaseCharacters.textProperty().addListener((observable, oldValue, newValue) -> preview());
    }

    private void preview() {
        renamerService.setOption(Option.RENAME_LOWERCASE_SPECIFIC_CHARACTERS_TEXT, txtLowerCaseCharacters.getText());
        renamerService.preview(RenameMethod.LOWERCASE);
        renameState.invalidateTableData();
    }
}

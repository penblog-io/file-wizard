package io.penblog.filewizard.controllers.move.methods;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.helpers.Control;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.MoverService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.MoveState;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.MoveMethod;

/**
 * NewName controller helps control of MoveController in managing NewName method
 */
public class NewName {

    @FXML
    private AnchorPane paneNewName;
    @FXML
    private TextField txtNewName;
    private MoverService moverService;
    private MoveState moveState;

    @FXML
    public void initialize() {
        moverService = ServiceContainer.getMoverService();
        moveState = MoveState.getInstance();

        setupNewNamePane();
        setupNewNameText();
        setupAttributePane();
    }

    private void setupNewNamePane() {
        paneNewName.setVisible(false);
        moveState.addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneNewName.setVisible(MoveMethod.NEW_NAME == evt.getNewValue());
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
        moveState.addPropertyChangeListener(evt -> {
            if ("selectedAttributeFormat".equals(evt.getPropertyName())
                    && moveState.getSelectedMethod() == MoveMethod.NEW_NAME) {
                Control.insertAtCaret(txtNewName, evt.getNewValue() == null ? "" : evt.getNewValue().toString());
            }
        });
    }

    /**
     * Preview method displays destination folders for files in the table where they will be moved to.
     */
    private void preview(String value) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                moverService.setOption(Option.MOVE_NEW_NAME_TEXT, value);
                moverService.preview(MoveMethod.NEW_NAME);
                moveState.invalidateTableData();
                return null;
            }
        }).start();
    }
}

package io.penblog.filewizard.controllers.move.methods;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.MoverService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.MoveState;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import io.penblog.filewizard.enums.MoveMethod;

public class Sequence {

    @FXML
    private AnchorPane paneSequence;
    @FXML
    private TextField txtStart, txtInterval, txtMask, txtLimit;

    private MoverService moverService;
    private MoveState moveState;
    private String type;

    @FXML
    public void initialize() {
        moverService = ServiceContainer.getMoverService();
        moveState = MoveState.getInstance();

        setupSequencePane();
        setupStartText();
        setupIntervalText();
        setupMaskText();
        setupLimitText();
    }


    private void setupSequencePane() {
        paneSequence.setVisible(false);
        MoveState.getInstance().addPropertyChangeListener(evt -> {
            if ("selectedMethod".equals(evt.getPropertyName())) {
                paneSequence.setVisible(MoveMethod.SEQUENCE == evt.getNewValue());
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

    private void setupLimitText() {
        txtInterval.setText("1");
        txtLimit.textProperty().addListener((observable, oldValue, newValue) -> preview());
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
            attribute = "{sequence:" + type + "," + txtStart.getText() + "," + txtInterval.getText();
            attribute += "," + (txtLimit.getText().isEmpty() ? "1" : txtLimit.getText());

            if (!txtMask.getText().isEmpty()) attribute += "," + txtMask.getText();
            attribute += "}";
        }

        moverService.setOption(Option.MOVE_SEQUENCE_TEXT, attribute);
        moverService.preview(MoveMethod.SEQUENCE);
        moveState.invalidateTableData();
    }
}

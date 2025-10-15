package io.penblog.filewizard.helpers;

import javafx.scene.control.TextField;

public class Control {
    public static void insertAtCaret(TextField textField, String text) {
        int caretPosition = textField.getCaretPosition();
        String currentText = textField.getText();
        String newText = currentText.substring(0, caretPosition) +
                text +
                currentText.substring(caretPosition);
        textField.setText(newText);
        textField.positionCaret(caretPosition + text.length());
    }
}

package io.penblog.filewizard.guis.dialog;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class FileDialog {
    private File initialDirectory;

    public FileDialog() {
    }

    public FileDialog(File initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public List<File> openMultipleSelect() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDirectory);
        return fileChooser.showOpenMultipleDialog(null);
    }
}

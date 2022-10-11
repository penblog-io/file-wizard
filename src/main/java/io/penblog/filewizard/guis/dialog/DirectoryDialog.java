package io.penblog.filewizard.guis.dialog;

import javafx.stage.DirectoryChooser;

import java.io.File;

public class DirectoryDialog {
    private File initialDirectory;

    public DirectoryDialog() {
    }

    public DirectoryDialog(File initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public File openSingleSelect() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(initialDirectory);
        return directoryChooser.showDialog(null);
    }
}

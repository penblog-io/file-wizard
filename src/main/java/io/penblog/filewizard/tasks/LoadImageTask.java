package io.penblog.filewizard.tasks;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;

public class LoadImageTask extends Task<Image> {
    private final File file;
    public LoadImageTask(File file) {
        this.file = file;
    }
    @Override
    protected Image call() throws Exception {
        return new Image(new FileInputStream(file));
    }
}

package io.penblog.filewizard.components;

import io.penblog.filewizard.helpers.SystemUtils;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

public class Item {

    public Item(File file) {
        this.file = file;
        originalFilename = new SimpleStringProperty(file.getName());
        absolutePath = new SimpleStringProperty(file.getAbsolutePath());
        type = new SimpleStringProperty(file.isDirectory() ? Item.TYPE_DIRECTORY : Item.TYPE_FILE);
    }

    public static final String TYPE_FILE = "File";
    public static final String TYPE_DIRECTORY = "Directory";

    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleStringProperty originalFilename = new SimpleStringProperty();
    private SimpleStringProperty newFilename = new SimpleStringProperty();
    private SimpleStringProperty absolutePath = new SimpleStringProperty();
    private final SimpleStringProperty destFolderName = new SimpleStringProperty();
    private File file;
    private boolean isError = false;
    private final SimpleStringProperty errorMessage = new SimpleStringProperty();
    /**
     * This property keeps track of item index listed on ui table
     */
    private int index = 0;


    public String getOriginalFilename() {
        return originalFilename.get();
    }

    public String getType() {
        return type.get();
    }

    public void setNewFilename(String newFilename) {
        this.newFilename = new SimpleStringProperty(newFilename);
    }

    public String getNewFilename() {
        return this.newFilename.get();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        originalFilename = new SimpleStringProperty(file.getName());
        absolutePath = new SimpleStringProperty(file.getAbsolutePath());
    }

    public String getDestFolderName() {
        return destFolderName.get();
    }

    public void setDestFolderName(String destFolderName) {
        this.destFolderName.set(destFolderName);
    }

    public String getDestFullFilename() {
        return getDestFolderName() + (SystemUtils.isWindows() ? "\\" : "/") + getOriginalFilename();
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setError(boolean error, String message) {
        isError = error;
        errorMessage.set(message);
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

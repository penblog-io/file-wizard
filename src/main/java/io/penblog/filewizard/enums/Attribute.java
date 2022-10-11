package io.penblog.filewizard.enums;

import io.penblog.filewizard.interfaces.BaseEnumInterface;

public enum Attribute implements BaseEnumInterface {

    DATE_ACCESSED("dateAccessed", "Date Accessed"),
    DATE_CREATED("dateCreated", "Date Created"),
    DATE_MODIFIED("dateModified", "Date Modified"),
    DATE_TAKEN("dateTaken", "Date Taken"),

    FILE_NAME("fileName", "Filename"),
    FILE_EXTENSION("fileExtension", "File Extension"),
    FILE_SIZE("fileSize", "File Size"),

    IMAGE_MAKE("imageMake", "Image Make"),
    IMAGE_MODEL("imageModel", "Image Model"),

    SEQUENCE("sequence", "Sequence");


    private final String key;
    private final String value;

    Attribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String value() {
        return value;
    }

    public String toString() {
        return value;
    }
}

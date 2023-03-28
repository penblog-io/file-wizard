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
    FILE_TYPE("fileType", "File Type"),
    MIME_TYPE("mimeType", "Mime Type"),

    CAMERA_MAKE("cameraMake", "Camera Make"),
    CAMERA_MODEL("cameraModel", "Camera Model"),
    IMAGE_WIDTH("imageWidth", "Image Width"),
    IMAGE_HEIGHT("imageHeight", "Image Height"),
    LENS_MODEL("lensModel", "Lens Model"),
    SHUTTER_SPEED("shutterSpeed", "Shutter Speed"),
    F_NUMBER("fNumber", "F-Number"),
    APERTURE_VALUE("apertureValue", "Aperture Value"),
    ISO("iso", "ISO"),
    FOCAL_LENGTH("focalLength", "Focal Length"),
    AUTHOR("author", "Author"),
    COPYRIGHT("copyright", "Copyright"),

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

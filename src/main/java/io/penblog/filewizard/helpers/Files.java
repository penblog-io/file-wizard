package io.penblog.filewizard.helpers;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Files {


    /**
     * DATE
     */
    public static String getDateCreated(File file, String format) throws IOException {
        return getDateAsString(file, format, "creationTime");
    }

    public static String getDateModified(File file, String format) throws IOException {
        return getDateAsString(file, format, "lastModifiedTime");
    }

    public static String getDateAccessed(File file, String format) throws IOException {
        return getDateAsString(file, format, "lastAccessTime");
    }

    public static String getDateTaken(File file, String format) throws IOException, AttributeNotFoundException {
        String value = metadata(file, "0x9003");
        if (value.length() == 0) return "";

        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"))
                .format(DateTimeFormatter.ofPattern(format));
    }


    /**
     * FILE
     */
    public static String getName(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex < 0) dotIndex = name.length();
        return name.substring(0, dotIndex);
    }


    public static String getExtension(File file) {
        if (file.isDirectory()) return "";
        int dotIndex = file.getName().lastIndexOf('.');
        if (dotIndex < 0) return "";
        return file.getName().substring(dotIndex + 1);
    }

    public static Long getFileSize(File file) throws IOException {
        return (Long) getFileAttribute(file, "size");
    }


    /**
     * IMAGE
     */
    public static String getImageMake(File file) throws IOException, AttributeNotFoundException {
        return metadata(file, "0x010f");
    }

    public static String getImageModel(File file) throws IOException, AttributeNotFoundException {
        return metadata(file, "0x0110");
    }


    private static String getDateAsString(File file, String format, String fileAttribute) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        FileTime fileTime = (FileTime) getFileAttribute(file, fileAttribute);
        return formatter.format(new java.util.Date(fileTime.toMillis()));
    }

    private static Object getFileAttribute(File file, String attribute) throws IOException {
        return java.nio.file.Files.getAttribute(file.toPath(), attribute);
    }

    private static String metadata(File file, String hex) throws IOException, AttributeNotFoundException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            for (Directory directory : metadata.getDirectories()) {
                for (com.drew.metadata.Tag tag : directory.getTags()) {
//                    System.out.format(
//                            "[%s] - [%s] %s = %s",
//                            directory.getName(),
//                            tag.getTagTypeHex(),
//                            tag.getTagName(),
//                            tag.getDescription()
//                    );
//                    System.out.println();

                    if (tag.getTagTypeHex().equals(hex)) {
                        return tag.getDescription();
                    }
                }

//                if (directory.hasErrors()) {
//                    for (String error : directory.getErrors()) {
//                        System.err.format("ERROR: %s", error);
//                    }
//                }
            }
        } catch (ImageProcessingException ignored) {
        }

        throw new AttributeNotFoundException();
    }

    public static boolean isInvalidFilename(String value) {
        return value.matches("[|\\\\/:*?]+");
    }

}

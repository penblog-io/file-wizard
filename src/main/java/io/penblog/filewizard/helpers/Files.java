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
        String value = metadata(file, "Exif SubIFD", 0x9003);
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

    public static String getMimeType(File file) throws IOException {
        return java.nio.file.Files.probeContentType(file.toPath());
    }

    /**
     * IMAGE
     */
    public static boolean isImage(File file) throws IOException {
        String mimeType = getMimeType(file);
        if (mimeType != null && !mimeType.isEmpty()) {
            String[] tokens = mimeType.split("/");
            return tokens.length > 0 && tokens[0].equals("image");
        }
        return false;
    }

    public static String getCameraMake(File file) throws IOException, AttributeNotFoundException {
        return metadata(file, "Exif IFD0", 0x010f);
    }

    public static String getCameraModel(File file) throws IOException, AttributeNotFoundException {
        return metadata(file, "Exif IFD0", 0x0110);
    }

    public static String getFileType(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "File Type", 0x0001);
    }

    public static Integer getImageWidth(File file) throws AttributeNotFoundException, IOException {
        String fileType = getFileType(file);
        String width = "";
        switch (fileType) {
            case "JPEG" -> width = metadata(file, "JPEG", 0x0003)
                    .replace(" pixels", "");
            case "PNG" -> width = metadata(file, "PNG-IHDR", 0x0001);
            case "GIF" -> width = metadata(file, "GIF Image", 0x0003);
            case "BMP" -> width = metadata(file, "BMP Header", 0x0002);
            case "HEIF" -> width = metadata(file, "HEIF", 0x0004)
                    .replace(" pixels", "");
            case "PSD" -> width = metadata(file, "PSD Header", 0x0003);
            case "TIFF" -> width = metadata(file, "Exif IFD0", 0x0100);
        }
        if (width.isEmpty()) return null;
        return Integer.parseInt(width);
    }

    public static Integer getImageHeight(File file) throws AttributeNotFoundException, IOException {
        String fileType = getFileType(file);
        String width = "";
        switch (fileType) {
            case "JPEG" -> width = metadata(file, "JPEG", 0x0001)
                    .replace(" pixels", "");
            case "PNG" -> width = metadata(file, "PNG-IHDR", 0x0002);
            case "GIF" -> width = metadata(file, "GIF Image", 0x0004);
            case "BMP" -> width = metadata(file, "BMP Header", 0x0001);
            case "HEIF" -> width = metadata(file, "HEIF", 0x0005)
                    .replace(" pixels", "");
            case "PSD" -> width = metadata(file, "PSD Header", 0x0002);
            case "TIFF" -> width = metadata(file, "Exif IFD0", 0x0101);
        }
        if (width.isEmpty()) return null;
        return Integer.parseInt(width);
    }

    public static String getLensModel(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif SubIFD", 0xa434);
    }

    public static String getShutterSpeed(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif SubIFD", 0x9201).replace(" sec", "");
    }

    public static String getFNumber(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif SubIFD", 0x829d);
    }

    public static String getApertureValue(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif SubIFD", 0x9202);
    }

    public static Integer getISO(File file) throws AttributeNotFoundException, IOException {
        return Integer.parseInt(metadata(file, "Exif SubIFD", 0x8827));
    }
    public static String getFocalLength(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif SubIFD", 0x920a);
    }

    public static String getAuthor(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif IFD0", 0x013b);
    }

    public static String getCopyright(File file) throws AttributeNotFoundException, IOException {
        return metadata(file, "Exif IFD0", 0x8298);
    }



    private static String getDateAsString(File file, String format, String fileAttribute) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        FileTime fileTime = (FileTime) getFileAttribute(file, fileAttribute);
        return formatter.format(new java.util.Date(fileTime.toMillis()));
    }

    private static Object getFileAttribute(File file, String attribute) throws IOException {
        return java.nio.file.Files.getAttribute(file.toPath(), attribute);
    }

    private static String metadata(File file, String directoryName, int hex)
            throws IOException, AttributeNotFoundException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                if (directory.getName().equals(directoryName) && directory.containsTag(hex)) {
                    return directory.getString(hex);
                }
            }
        } catch (ImageProcessingException ignored) {
        }

        throw new AttributeNotFoundException();
    }

    public static boolean isInvalidFilename(String value) {
        return value.matches("[|\\\\/:*?]+");
    }

    public static void debugMetadata(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (com.drew.metadata.Tag tag : directory.getTags()) {
                    System.out.format(
                            "[%s] - [%s] %s = %s",
                            directory.getName(),
                            tag.getTagTypeHex(),
                            tag.getTagName(),
                            tag.getDescription()
                    );
                    System.out.println();
                }

//                if (directory.hasErrors()) {
//                    for (String error : directory.getErrors()) {
//                        System.err.format("ERROR: %s", error);
//                    }
//                }
            }
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
    }
}

package io.penblog.filewizard.services;


import io.penblog.filewizard.attributes.*;
import io.penblog.filewizard.helpers.SystemUtils;
import io.penblog.filewizard.movers.NewName;
import io.penblog.filewizard.renamers.*;
import io.penblog.filewizard.enums.Attribute;
import io.penblog.filewizard.enums.MoveMethod;
import io.penblog.filewizard.enums.RenameMethod;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ServiceContainer {

    private static SettingService settingService;
    private static RenamerService renamerService;
    private static MoverService moverService;
    private static AttributeService attributeService;
    private static OptionService optionService;
    private static WebService webService;
    private static Logger logger;
    private static FileHandler fileHandler;
    private static Properties properties;

    public static SettingService getSettingService() {
        if (settingService == null) settingService = new SettingService(getLogger());
        return settingService;
    }


    public static RenamerService getRenamerService() {
        if (renamerService == null) {
            renamerService = new RenamerService(getOptionService(), getAttributeService());

            renamerService.registerRenamer(RenameMethod.NEW_NAME, new io.penblog.filewizard.renamers.NewName());
            renamerService.registerRenamer(RenameMethod.SEQUENCE, new io.penblog.filewizard.renamers.Sequence());
            renamerService.registerRenamer(RenameMethod.PREPEND, new Prepend());
            renamerService.registerRenamer(RenameMethod.APPEND, new Append());
            renamerService.registerRenamer(RenameMethod.REPLACE, new Replace());
            renamerService.registerRenamer(RenameMethod.REMOVE, new Remove());
            renamerService.registerRenamer(RenameMethod.LOWERCASE, new LowerCase());
            renamerService.registerRenamer(RenameMethod.UPPERCASE, new UpperCase());
        }
        return renamerService;
    }

    public static MoverService getMoverService() {
        if (moverService == null) {
            moverService = new MoverService(getOptionService(), getAttributeService());
            moverService.registerMover(MoveMethod.NEW_NAME, new NewName());
            moverService.registerMover(MoveMethod.SEQUENCE, new io.penblog.filewizard.movers.Sequence());
        }
        return moverService;
    }

    public static OptionService getOptionService() {
        if (optionService == null) optionService = new OptionService();
        return optionService;
    }

    public static AttributeService getAttributeService() {
        if (attributeService == null) {
            attributeService = new AttributeService();
            attributeService.registerAttributeGenerator(Attribute.DATE_ACCESSED, new DateAccessed());
            attributeService.registerAttributeGenerator(Attribute.DATE_CREATED, new DateCreated());
            attributeService.registerAttributeGenerator(Attribute.DATE_MODIFIED, new DateModified());
            attributeService.registerAttributeGenerator(Attribute.DATE_TAKEN, new DateTaken());

            attributeService.registerAttributeGenerator(Attribute.FILE_NAME, new FileName());
            attributeService.registerAttributeGenerator(Attribute.FILE_EXTENSION, new FileExtension());
            attributeService.registerAttributeGenerator(Attribute.FILE_SIZE, new FileSize());
            attributeService.registerAttributeGenerator(Attribute.FILE_TYPE, new FileType());
            attributeService.registerAttributeGenerator(Attribute.MIME_TYPE, new MimeType());

            attributeService.registerAttributeGenerator(Attribute.CAMERA_MAKE, new CameraMake());
            attributeService.registerAttributeGenerator(Attribute.CAMERA_MODEL, new CameraModel());
            attributeService.registerAttributeGenerator(Attribute.IMAGE_WIDTH, new ImageWidth());
            attributeService.registerAttributeGenerator(Attribute.IMAGE_HEIGHT, new ImageHeight());
            attributeService.registerAttributeGenerator(Attribute.LENS_MODEL, new LensModel());
            attributeService.registerAttributeGenerator(Attribute.SHUTTER_SPEED, new ShutterSpeed());
            attributeService.registerAttributeGenerator(Attribute.F_NUMBER, new FNumber());
            attributeService.registerAttributeGenerator(Attribute.APERTURE_VALUE, new ApertureValue());
            attributeService.registerAttributeGenerator(Attribute.ISO, new ISO());
            attributeService.registerAttributeGenerator(Attribute.FOCAL_LENGTH, new FocalLength());
            attributeService.registerAttributeGenerator(Attribute.AUTHOR, new Author());
            attributeService.registerAttributeGenerator(Attribute.COPYRIGHT, new Copyright());

            attributeService.registerAttributeGenerator(Attribute.SEQUENCE, new io.penblog.filewizard.attributes.Sequence());
        }
        return attributeService;
    }


    public static WebService getWebService() {
        if (webService == null) webService = new WebService(getLogger());
        return webService;
    }


    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("filewizard");
            String tempDir = System.getProperty("java.io.tmpdir");

            if (!SystemUtils.isWindows() && tempDir.charAt(tempDir.length() - 1) != '/') {
                tempDir += "/";
            } else if (SystemUtils.isWindows() && tempDir.charAt(tempDir.length() - 1) != '\\') {
                tempDir += "\\";
            }
            String tempFile = tempDir + "RenameWizard.log";
            try {
                fileHandler = new FileHandler(tempFile);
                logger.addHandler(fileHandler);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    public static void closeFileHandler() {
        if (fileHandler != null) fileHandler.close();
    }

    public static Properties getProperties() {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("config.properties"));
            } catch (IOException ignored) {
            }
        }
        return properties;
    }
}

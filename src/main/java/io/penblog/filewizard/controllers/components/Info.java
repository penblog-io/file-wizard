package io.penblog.filewizard.controllers.components;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.guis.dialog.InfoBox;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.AttributeService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.states.InfoState;
import io.penblog.filewizard.tasks.LoadImageTask;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Info controller class responsible for Info FXML component, it displays general information as well as
 * specific info based on type of file.
 */
public class Info {

    // define property format
    private static final String FILE_SIZE = "{fileSize}";
    private static final String DATE_CREATED = "{dateCreated:yyyy-MM-dd hh:ss a}";
    private static final String DATE_MODIFIED = "{dateModified:yyyy-MM-dd hh:ss a}";
    private static final String DATE_ACCESSED = "{dateAccessed:yyyy-MM-dd hh:ss a}";
    private static final String DATE_TAKEN = "{dateTaken:yyyy-MM-dd hh:ss a}";
    private static final String FILE_TYPE = "{fileType}";
    private static final String IMAGE_WIDTH = "{imageWidth}";
    private static final String IMAGE_HEIGHT = "{imageHeight}";
    private static final String CAMERA_MAKER = "{imageMake}";
    private static final String CAMERA_MODEL = "{imageModel}";
    private static final String LENS_MODEL = "{lensModel}";
    private static final String SHUTTER_SPEED = "{shutterSpeed}";
    private static final String F_NUMBER = "{fNumber}";
    private static final String APERTURE_VALUE = "{apertureValue}";
    private static final String ISO = "{iso}";
    private static final String FOCAL_LENGTH = "{focalLength}";
    private static final String AUTHOR = "{author}";
    private static final String COPYRIGHT = "{copyright}";


    @FXML
    private VBox boxInfo;

    // "General" section
    @FXML
    private VBox boxGeneral;
    @FXML
    private Label lbFilename, lbFileSize, lbCreatedDate, lbModifiedDate, lbAccessedDate;
    @FXML
    private Hyperlink lnkLocation;

    // "Image" section
    @FXML
    private VBox boxImage;
    @FXML
    private Label lbImageTakenDate, lbImageFileType, lbImageWidth, lbImageHeight, lbImageCameraMaker,
            lbImageCameraModel, lbImageLensModel, lbImageFNumber, lbImageShutterSpeed, lbImageApertureValue,
            lbImageISO, lbImageFocalLength, lbImageAuthor, lbImageCopyright;
    @FXML
    private ImageView imgThumbnail;

    private Item item;
    private InfoState infoState;
    private AttributeService attributeService;

    @FXML
    public void initialize() {
        infoState = InfoState.getInstance();
        attributeService = ServiceContainer.getAttributeService();
        setup();
    }

    private void setup() {
        boxGeneral.setVisible(false);
        showImageSection(false);

        infoState.addPropertyChangeListener(evt -> {
            if ("itemSelected".equals(evt.getPropertyName())) {
                item = infoState.getSelectedItem();
                if(item == null) {
                    boxGeneral.setVisible(false);
                }
                else {
                    boxGeneral.setVisible(true);
                    setupGeneralSection();
                    setupImageSection();
                }
            }
        });
    }

    private void setupGeneralSection() {
        File file = item.getFile();

        lbFilename.setText(file.getName());

        lnkLocation.setText(file.getParent());
        lnkLocation.setOnAction(e -> {
            try {
                Desktop.getDesktop().open(file.getParentFile());
            } catch (IOException ex) {
                InfoBox.getInstance().show("File Not Found", null,
                        "Cannot find the file you are looking for. It has may been moved.");
            }
        });

        lbFileSize.setText(new DecimalFormat("###,###,###,###,###,###")
                .format(new BigInteger(getAttribute(FILE_SIZE))) + " bytes");

        lbCreatedDate.setText(getAttribute(DATE_CREATED));
        lbModifiedDate.setText(getAttribute(DATE_MODIFIED));
        lbAccessedDate.setText(getAttribute(DATE_ACCESSED));
    }

    private void setupImageSection() {
        File file = item.getFile();
//        Files.debugMetadata(item.getFile());

        try {
            if(Files.isImage(file)) {
                showImageSection(true);

                lbImageTakenDate.setText(getAttribute(DATE_TAKEN));
                lbImageFileType.setText(getAttribute(FILE_TYPE).toUpperCase());
                lbImageWidth.setText(getAttribute(IMAGE_WIDTH) + " pixels");
                lbImageHeight.setText(getAttribute(IMAGE_HEIGHT) + " pixels");
                lbImageCameraMaker.setText(getAttribute(CAMERA_MAKER));
                lbImageCameraModel.setText(getAttribute(CAMERA_MODEL));
                lbImageLensModel.setText(getAttribute(LENS_MODEL));
                lbImageFNumber.setText(getAttribute(F_NUMBER));
                lbImageShutterSpeed.setText(getAttribute(SHUTTER_SPEED));
                lbImageISO.setText(getAttribute(ISO));
                lbImageApertureValue.setText(getAttribute(APERTURE_VALUE));
                lbImageFocalLength.setText(getAttribute(FOCAL_LENGTH));
                lbImageAuthor.setText(getAttribute(AUTHOR));
                lbImageCopyright.setText(getAttribute(COPYRIGHT));

                LoadImageTask loadImageTask = new LoadImageTask(file);
                imgThumbnail.imageProperty().bind(loadImageTask.valueProperty());
                new Thread(loadImageTask).start();
            }
            else showImageSection(false);

        } catch (IOException ignored) {}
    }

    private String getAttribute(String attribute) {
        return attributeService.convert(attribute, attributeService.getAttributes(attribute), item);
    }

    private void showImageSection(boolean show) {
        if(show) {
            if (!boxInfo.getChildren().contains(boxImage)) {
                boxInfo.getChildren().add(boxImage);
            }
        }
        else boxInfo.getChildren().remove(boxImage);
    }
}

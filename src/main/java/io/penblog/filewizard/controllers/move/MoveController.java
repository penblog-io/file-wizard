package io.penblog.filewizard.controllers.move;

import io.penblog.filewizard.guis.dialog.actions.TableAction;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.MoveMethod;
import io.penblog.filewizard.guis.dialog.DirectoryDialog;
import io.penblog.filewizard.guis.dialog.FileDialog;
import io.penblog.filewizard.guis.dialog.InfoBox;
import io.penblog.filewizard.services.MoverService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.services.SettingService;
import io.penblog.filewizard.states.MoveState;

import java.io.File;
import java.util.List;

/**
 * MoveController class manages Move UI tab
 */
public class MoveController {

    @FXML
    private ComboBox<MoveMethod> cboMethod;
    @FXML
    private RadioButton rdoCurrent;
    @FXML
    private TableView<Item> tbMove;
    @FXML
    private TableColumn<Item, Item> colFilename, colNewFolderName;
    @FXML
    private Label lbSpecificText;

    private MoverService moverService;
    private SettingService settingService;
    private MoveState moveState;

    @FXML
    public void initialize() {
        settingService = ServiceContainer.getSettingService();
        moverService = ServiceContainer.getMoverService();
        moveState = MoveState.getInstance();

        setupTableView();
        setupFilenameColumn();
        setupNewFileNameColumn();
        setupMethodComboBox();
        setupSpecificText();
        rdoCurrent.setSelected(true);
    }

    /**
     * On remove button click, remove selected items from item list in ItemService
     * it will also remove from the table view since their values are bind.
     */
    @FXML
    public void onRemoveClick() {
        moverService.removeItems(tbMove.getSelectionModel().getSelectedItems());
        tbMove.refresh();
    }

    /**
     * On clear button click, remove all items from the ItemService
     * it will also remove from the table view since their values are bind.
     */
    @FXML
    public void onClearClick() {
        moverService.clearItems();
        tbMove.refresh();
    }

    /**
     * On move button click, move all files to each respective folders
     */
    @FXML
    public void onMoveClick() {
        int success = 0;
        int skip = 0;
        for (Item item : moverService.getItems()) {
            if (item.isError()) skip++;
            else success++;
        }

        moverService.move();
        InfoBox.getInstance().show("Status", "",
                success + " item" + (success > 1 ? "s have" : " has") + " been moved.\n" +
                        skip + " item" + (skip > 1 ? "s have" : " has") + " been skipped.\n");

        moverService.clearNonErrorItems();
        tbMove.refresh();
    }

    /**
     * On open button click, open a multi-select dialog box
     */
    @FXML
    public void onOpenClick() {
        List<File> files = new FileDialog(settingService.setting().getLastOpenDirectory()).openMultipleSelect();
        if (files != null) setFiles(files);
    }

    /**
     * on files dragged on table view, instead using file dialog, users can drag and drop files to the table view
     */
    @FXML
    public void onFilesDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        setFiles(db.getFiles());
        event.setDropCompleted(true);
        event.consume();
    }

    @FXML
    public void onFilesDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    /**
     * Add selected/dragged files to ItemService, immediately trigger "preview" method.
     */
    private void setFiles(List<File> files) {
        if (files.size() > 0) {
            moverService.setFiles(files);
            settingService.setLastOpenDirectory(files.get(0).getParentFile());
            settingService.write();
            moverService.preview(moveState.getSelectedMethod());
            tbMove.refresh();
        }
    }


    private void setupTableView() {
        TableAction.initialize(tbMove);

        tbMove.setItems(moverService.getItems());

        // if item data is modified anywhere, try to refresh the table view to display the updated data
        moveState.addPropertyChangeListener(evt -> {
            if ("invalidateTableData".equals(evt.getPropertyName())) {
                tbMove.refresh();
            }
        });
    }

    private void setupFilenameColumn() {
        colFilename.setCellValueFactory(new PropertyValueFactory<>("absolutePath"));
    }

    private void setupNewFileNameColumn() {
        colNewFolderName.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Item getValue() {
                return param.getValue();
            }
        });

        // highlight table cell based on their status, red if there is an error
        colNewFolderName.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    if (item.isError()) {
                        if (!getStyleClass().contains("table-cell-error")) {
                            getStyleClass().add("table-cell-error");
                        }
                        setText(item.getErrorMessage());
                    } else {
                        getStyleClass().remove("table-cell-error");
                        setText(item.getDestFolderName());
                    }
                }
            }
        });
    }

    private void setupMethodComboBox() {
        cboMethod.setItems(FXCollections.observableArrayList(MoveMethod.values()));
        cboMethod.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(MoveMethod item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) setText(item.value());
                else setText("");
            }
        });

        cboMethod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                moveState.setSelectedMethod(newValue));
    }

    private void setupSpecificText() {
        if (!lbSpecificText.getStyleClass().contains("move-specific-text-light")) {
            lbSpecificText.getStyleClass().add("move-specific-text-light");
        }
    }

    /**
     * on "Current Folder" radio button clicked, sub-folders will be created inside files' current folder,
     * then will be moved to those sub-folders
     */
    @FXML
    public void onCurrentRadioButtonClick() {
        moverService.setMoveTarget(null);
        lbSpecificText.setOpacity(0.5);
        preview();
    }

    /**
     * on "Specific Folder" radio button clicked, it will trigger a folder dialog box for the user to choose
     * a destination folder, then sub-folders will be created in that specific folders
     */
    @FXML
    public void onSpecificRadioButtonClick() {
        File moveTarget = new DirectoryDialog(settingService.setting().getLastOpenDirectory()).openSingleSelect();
        if (moveTarget != null) {
            settingService.setLastOpenDirectory(moveTarget.getParentFile());
            settingService.write();
            lbSpecificText.setOpacity(1);
            lbSpecificText.setText(moveTarget.getAbsolutePath());
            moverService.setMoveTarget(moveTarget);
            preview();
        } else {
            moverService.setMoveTarget(null);
            rdoCurrent.setSelected(true);
        }
    }

    /**
     * Preview method displays destination folders for files in the table where they will be moved to.
     */
    private void preview() {
        moverService.preview(moveState.getSelectedMethod());
        moveState.invalidateTableData();
    }
}

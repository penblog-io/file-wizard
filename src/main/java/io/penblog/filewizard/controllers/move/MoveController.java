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


    @FXML
    public void onRemoveClick() {
        moverService.removeItems(tbMove.getSelectionModel().getSelectedItems());
        tbMove.refresh();
    }

    @FXML
    public void onClearClick() {
        moverService.clearItems();
        tbMove.refresh();
    }

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

    @FXML
    public void onOpenClick() {
        List<File> files = new FileDialog(settingService.setting().getLastOpenDirectory()).openMultipleSelect();
        if (files != null) setFiles(files);
    }

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


    @FXML
    public void onCurrentRadioButtonClick() {
        moverService.setMoveTarget(null);
        lbSpecificText.setOpacity(0.5);
        preview();
    }

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

    private void preview() {
        moverService.preview(moveState.getSelectedMethod());
        moveState.invalidateTableData();
    }
}

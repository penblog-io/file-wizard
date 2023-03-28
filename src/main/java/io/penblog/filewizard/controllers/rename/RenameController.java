package io.penblog.filewizard.controllers.rename;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.guis.dialog.FileDialog;
import io.penblog.filewizard.guis.dialog.InfoBox;
import io.penblog.filewizard.guis.dialog.actions.TableAction;
import io.penblog.filewizard.services.RenamerService;
import io.penblog.filewizard.services.ServiceContainer;
import io.penblog.filewizard.services.SettingService;
import io.penblog.filewizard.states.RenameState;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.List;

public class RenameController {

    @FXML
    private ComboBox<RenameMethod> cboMethod;
    @FXML
    private TableView<Item> tbRename;
    @FXML
    private TableColumn<Item, Item> colOriginalFilename, colNewFilename;
    private RenamerService renamerService;
    private SettingService settingService;
    private RenameState renameState;

    @FXML
    public void initialize() {
        settingService = ServiceContainer.getSettingService();
        renamerService = ServiceContainer.getRenamerService();
        renameState = RenameState.getInstance();

        setupTableView();
        setupOriginalFilenameColumn();
        setupNewFileNameColumn();
        setupMethodComboBox();
    }


    @FXML
    public void onRemoveClick() {
        renamerService.removeItems(tbRename.getSelectionModel().getSelectedItems());
        tbRename.refresh();
    }

    @FXML
    public void onClearClick() {
        renamerService.clearItems();
        tbRename.refresh();
    }

    @FXML
    public void onRenameClick() {
        int success = 0;
        int skip = 0;
        for (Item item : renamerService.getItems()) {
            if (item.isError()) skip++;
            else success++;
        }

        renamerService.rename();
        InfoBox.getInstance().show("Status", "",
                success + " item" + (success > 1 ? "s have" : " has") + " been renamed.\n" +
                        skip + " item" + (skip > 1 ? "s have" : " has") + " been skipped.");

        renamerService.clearNonErrorItems();
        tbRename.refresh();
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
            renamerService.setFiles(files);
            settingService.setLastOpenDirectory(files.get(0).getParentFile());
            settingService.write();
            renamerService.preview(renameState.getSelectedMethod());
            tbRename.refresh();
        }
    }

    private void setupTableView() {
        TableAction.initialize(tbRename);

        tbRename.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbRename.setItems(renamerService.getItems());
        renameState.addPropertyChangeListener(evt -> {
            if ("invalidateTableData".equals(evt.getPropertyName())) {
                tbRename.refresh();
            }
        });
    }

    private void setupOriginalFilenameColumn() {
        colOriginalFilename.setCellValueFactory(new PropertyValueFactory<>("originalFilename"));
    }

    private void setupNewFileNameColumn() {
        colNewFilename.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Item getValue() {
                return param.getValue();
            }
        });
        colNewFilename.setCellFactory(param -> new TableCell<>() {
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
                        setText(item.getNewFilename());
                    }
                }
            }
        });
    }

    private void setupMethodComboBox() {
        cboMethod.setItems(FXCollections.observableArrayList(RenameMethod.values()));
        cboMethod.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(RenameMethod item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) setText(item.value());
                else setText("");
            }
        });

        cboMethod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                renameState.setSelectedMethod(newValue));
    }
}

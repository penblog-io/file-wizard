package io.penblog.filewizard.guis.dialog.actions;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.guis.dialog.InfoBox;
import io.penblog.filewizard.states.InfoState;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.IOException;


/**
 * This helper class remove duplicate code for both TableView on Rename and Move views
 */
public class TableAction {


    public static void initialize(TableView<Item> tableView) {
        setMultipleSelect(tableView);
        openFileOnItemDoubleClick(tableView);
        showInfoOnItemSelected(tableView);
    }

    public static void setMultipleSelect(TableView<Item> tableView) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public static void openFileOnItemDoubleClick(TableView<Item> tableView) {
        tableView.setRowFactory(openFileOnDoubleClick -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Desktop.getDesktop().open(row.getItem().getFile());
                    } catch (IOException e) {
                        InfoBox.getInstance().show("File Not Found", null, "Cannot find the file you are looking for. It has may been moved.");
                    }
                }
            });
            return row;
        });
    }

    public static void showInfoOnItemSelected(TableView<Item> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldItem, newItem) -> {
            InfoState.getInstance().setSelectedItem(newItem);
        }));
    }
}

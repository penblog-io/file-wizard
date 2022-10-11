package io.penblog.filewizard.controllers.components;

import io.penblog.filewizard.states.MoveState;
import io.penblog.filewizard.states.RenameState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Attribute {

    private static final String JSON_ATTRIBUTE_LIST = "json/attributes.json";

    @FXML
    private ListView<io.penblog.filewizard.enums.Attribute> lstAttribute;
    @FXML
    private ListView<String[]> lstAttributeFormat;


    private final Map<io.penblog.filewizard.enums.Attribute, ArrayList<String[]>> data = new HashMap<>();

    @FXML
    public void initialize() {
        loadData();
        setupAttributeList();
        setupFormatList();
    }


    private void loadData() {
        InputStream is = Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(JSON_ATTRIBUTE_LIST));
        JSONObject json = new JSONObject(new JSONTokener(is));

        for (io.penblog.filewizard.enums.Attribute attr : io.penblog.filewizard.enums.Attribute.values()) {
            data.put(attr, new ArrayList<>());

            if (json.has(attr.key())) {
                JSONArray items = (JSONArray) json.get(attr.key());
                for (int i = 0; i < items.length(); i++) {
                    JSONArray item = items.getJSONArray(i);
                    data.get(attr).add(new String[]{
                            item.getString(0),
                            item.getString(1),
                            item.getString(2)
                    });
                }
            }
        }
    }

    private void setupAttributeList() {

        lstAttribute.setItems(FXCollections.observableArrayList(io.penblog.filewizard.enums.Attribute.values()));
        lstAttribute.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(io.penblog.filewizard.enums.Attribute item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText("");
                else setText(item.value());
            }
        });

        lstAttribute.getSelectionModel().selectedItemProperty().addListener((listener, oldValue, newValue) -> {
            io.penblog.filewizard.enums.Attribute attribute = lstAttribute.getSelectionModel().getSelectedItem();
            if (attribute == null) {
                RenameState.getInstance().setSelectedAttribute(null);
                MoveState.getInstance().setSelectedAttribute(null);
            } else {
                RenameState.getInstance().setSelectedAttribute(attribute);
                MoveState.getInstance().setSelectedAttribute(attribute);
                lstAttributeFormat.setItems(FXCollections.observableArrayList(data.get(attribute)));
            }

        });
    }

    private void setupFormatList() {
        lstAttributeFormat.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) setText(item[0]);
                else setText("");
            }
        });

        lstAttributeFormat.getSelectionModel().selectedItemProperty().addListener((listener, oldValue, newValue) -> {
            String[] selected = lstAttributeFormat.getSelectionModel().getSelectedItem();
            RenameState.getInstance().setSelectedAttributeFormat(selected == null ? null : selected[1]);
            MoveState.getInstance().setSelectedAttributeFormat(selected == null ? null : selected[1]);
        });

    }
}

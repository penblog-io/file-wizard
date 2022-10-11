package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class ItemService {

    /**
     * This property ensures that each item is unique based on the absolute path of the file.
     * it also helps to get item by absolute path faster with iterating through the list
     */
    private final Map<String, Integer> indexes = new HashMap<>();
    private final ObservableList<Item> items = FXCollections.observableArrayList();


    public void put(List<Item> items) {
        for (Item item : items) {
            if (indexes.get(item.getAbsolutePath()) == null) {
                this.items.add(item);
                indexes.put(item.getFile().getAbsolutePath(), items.size() - 1);
            }
        }
        sort();
    }

    public void remove(List<Item> items) {
        List<Integer> itemIndexes = new ArrayList<>(items.stream().map(Item::getAbsolutePath).toList()
                .stream().map(indexes::get).toList());
        itemIndexes.sort(Collections.reverseOrder());
        itemIndexes.forEach(index -> this.items.remove(index.intValue()));
        reIndexes();
    }

    public void reIndexes() {
        indexes.clear();
        for (int i = 0; i < items.size(); i++) {
            indexes.put(items.get(i).getFile().getAbsolutePath(), i);
            items.get(i).setIndex(i);
        }
    }

    public void clear() {
        items.clear();
        indexes.clear();
    }

    public int size() {
        return items.size();
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public Item get(String absolutePath) {
        Integer index = indexes.get(absolutePath);
        return items.get(index);
    }

    private void sort() {
        items.sort(Comparator.comparing(Item::getOriginalFilename));
        reIndexes();
    }

}

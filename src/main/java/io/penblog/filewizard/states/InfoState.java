package io.penblog.filewizard.states;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.interfaces.StateInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InfoState implements StateInterface {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private static InfoState infoState;
    private Item item;

    private InfoState() {
    }

    public static InfoState getInstance() {
        if (infoState == null) infoState = new InfoState();
        return infoState;
    }

    public void setSelectedItem(Item selectedItem) {
        Item oldValue = this.item;
        this.item = selectedItem;
        support.firePropertyChange("itemSelected", oldValue, this.item);
    }

    public Item getSelectedItem() {
        return item;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}

package io.penblog.filewizard.interfaces;

import java.beans.PropertyChangeListener;


public interface StateInterface {
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}

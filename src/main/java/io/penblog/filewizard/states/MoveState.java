package io.penblog.filewizard.states;

import io.penblog.filewizard.enums.Attribute;
import io.penblog.filewizard.enums.MoveMethod;
import io.penblog.filewizard.interfaces.StateInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MoveState implements StateInterface {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private static MoveState moveState;
    private MoveMethod selectedMethod;
    private Attribute selectedAttribute;
    private String selectedAttributeFormat;


    private MoveState() {
    }

    public static MoveState getInstance() {
        if (moveState == null) moveState = new MoveState();
        return moveState;
    }

    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

    public void setSelectedAttribute(Attribute selectedAttribute) {
        Attribute oldValue = this.selectedAttribute;
        this.selectedAttribute = selectedAttribute;
        support.firePropertyChange("selectedAttribute", oldValue, this.selectedAttribute);
    }

    public String getSelectedAttributeFormat() {
        return selectedAttributeFormat;
    }

    public void setSelectedAttributeFormat(String selectedAttributeFormat) {
        String oldValue = this.selectedAttributeFormat;
        this.selectedAttributeFormat = selectedAttributeFormat;
        support.firePropertyChange("selectedAttributeFormat", oldValue, this.selectedAttributeFormat);
    }

    public MoveMethod getSelectedMethod() {
        return selectedMethod;
    }

    public void setSelectedMethod(MoveMethod selectedMethod) {
        MoveMethod oldValue = this.selectedMethod;
        this.selectedMethod = selectedMethod;
        support.firePropertyChange("selectedMethod", oldValue, this.selectedMethod);
    }

    public void invalidateTableData() {
        support.firePropertyChange("invalidateTableData", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}

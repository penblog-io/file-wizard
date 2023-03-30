package io.penblog.filewizard.states;

import io.penblog.filewizard.enums.Attribute;
import io.penblog.filewizard.enums.MoveMethod;
import io.penblog.filewizard.interfaces.StateInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * MoveState class manages states for Move UI tab
 */
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

    /**
     * setSelectedAttribute fires "selectedAttribute" event when an attribute is selected.
     */
    public void setSelectedAttribute(Attribute selectedAttribute) {
        Attribute oldValue = this.selectedAttribute;
        this.selectedAttribute = selectedAttribute;
        support.firePropertyChange("selectedAttribute", oldValue, this.selectedAttribute);
    }

    public String getSelectedAttributeFormat() {
        return selectedAttributeFormat;
    }

    /**
     * setSelectedAttributeFormat fires "selectedAttributeFormat" event when an attribute format is selected.
     */
    public void setSelectedAttributeFormat(String selectedAttributeFormat) {
        String oldValue = this.selectedAttributeFormat;
        this.selectedAttributeFormat = selectedAttributeFormat;
        support.firePropertyChange("selectedAttributeFormat", oldValue, this.selectedAttributeFormat);
    }

    public MoveMethod getSelectedMethod() {
        return selectedMethod;
    }

    /**
     * setSelectedMethod fires "selectedMethod" event when a method is selected.
     */
    public void setSelectedMethod(MoveMethod selectedMethod) {
        MoveMethod oldValue = this.selectedMethod;
        this.selectedMethod = selectedMethod;
        support.firePropertyChange("selectedMethod", oldValue, this.selectedMethod);
    }

    /**
     * when invalidateTableData is called, it fires "invalidateTableData" event.
     */
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

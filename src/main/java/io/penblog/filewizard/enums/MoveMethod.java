package io.penblog.filewizard.enums;

import io.penblog.filewizard.interfaces.BaseEnumInterface;

public enum MoveMethod implements BaseEnumInterface {
    NEW_NAME("newName", "New Name"),
    SEQUENCE("sequence", "Sequence");

    private final String key;
    private final String value;

    MoveMethod(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

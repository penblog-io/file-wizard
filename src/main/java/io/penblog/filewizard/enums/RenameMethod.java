package io.penblog.filewizard.enums;

import io.penblog.filewizard.interfaces.BaseEnumInterface;

public enum RenameMethod implements BaseEnumInterface {

    NEW_NAME("newName", "New Name"),
    SEQUENCE("sequence", "Sequence"),
    PREPEND("prepend", "Prepend"),
    APPEND("append", "Append"),
    REPLACE("replace", "Replace"),
    UPPERCASE("upperCase", "Upper Case"),
    LOWERCASE("lowerCase", "Lower Case"),
    REMOVE("remove", "Remove");

    private final String key;
    private final String value;

    RenameMethod(final String key, final String value) {
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

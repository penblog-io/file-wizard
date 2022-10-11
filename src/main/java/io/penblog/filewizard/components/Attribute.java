package io.penblog.filewizard.components;

public record Attribute(String original, io.penblog.filewizard.enums.Attribute attribute, String value,
                        boolean isValidValue) {

    public boolean isRegistered() {
        return attribute != null;
    }

    @Override
    public String toString() {
        return original;
    }

}

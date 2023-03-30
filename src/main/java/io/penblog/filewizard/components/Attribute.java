package io.penblog.filewizard.components;

/**
 * Java Bean attribute
 * @param original
 * @param attribute
 * @param value
 * @param isValidValue
 */
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

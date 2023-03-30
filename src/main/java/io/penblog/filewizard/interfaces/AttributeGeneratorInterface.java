package io.penblog.filewizard.interfaces;

import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;

import java.io.IOException;

/**
 * To register a new Attribute, it must implement this AttributeGeneratorInterface
 */
public interface AttributeGeneratorInterface {

    /**
     * Generate attribute from item
     */
    String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException;

    /**
     * Validate if the attribute value is valid
     */
    void validateAttributeValue(String attributeValue) throws IllegalAttributeValueException;
}

package io.penblog.filewizard.interfaces;

import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;

import java.io.IOException;

public interface AttributeGeneratorInterface {

    String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException;

    void validateAttributeValue(String attributeValue) throws IllegalAttributeValueException;
}

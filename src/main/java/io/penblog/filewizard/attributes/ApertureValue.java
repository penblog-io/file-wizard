package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;


/**
 * Retrieve Aperture Value from a file if available
 * attribute: {apertureValue}
 */
public class ApertureValue implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getApertureValue(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

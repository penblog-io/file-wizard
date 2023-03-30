package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve a model of lens used to capture a media file (i.e: RF-S55-210mm F5-7.1 IS STM  etc.)
 * attribute: {lensModel}
 */
public class LensModel implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getLensModel(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

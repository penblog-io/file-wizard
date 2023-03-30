package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve focal length number from a media file (i.e: 18 mm, 70 mm etc.)
 * attribute: {focalLength}
 */
public class FocalLength implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getFocalLength(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

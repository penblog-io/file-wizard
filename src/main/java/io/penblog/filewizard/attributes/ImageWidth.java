package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve an image width in pixel (i.e: 1920, 1080 etc.)
 * attribute: {imageWidth}
 */
public class ImageWidth implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return String.valueOf(Files.getImageWidth(item.getFile()));
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve a shutter speed from a media file (i.e: 1/100, 1/400 etc.)
 * attribute: {shutterSpeed}
 */
public class ShutterSpeed implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getShutterSpeed(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

package io.penblog.filewizard.attributes;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve Camera Maker from a media file (i.e: Canon, Nikon etc.)
 * attribute: {cameraMake}
 */
public class CameraMake implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getCameraMake(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

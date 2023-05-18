package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve an ISO value from a media file (i.e: 100, 200, 500 etc.)
 * attribute: {iso}
 */
public class ISO implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return String.valueOf(Files.getISO(item.getFile()));
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

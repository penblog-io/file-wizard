package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

/**
 * Retrieve a file extension from a filename, NOT from a mime type
 * attribute: {fileExtension}
 */
public class FileExtension implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) {
        return Files.getExtension(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {
    }
}

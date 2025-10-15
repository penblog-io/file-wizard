package io.penblog.filewizard.attributes;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve creation date of a file
 * attribute: {dateCreated:format}
 */
public class DateCreated implements AttributeGeneratorInterface {

    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        return "timestamp".equals(attributeValue)
                ? Files.getDateCreatedAsTimestamp(item.getFile())
                : Files.getDateCreated(item.getFile(), attributeValue);
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

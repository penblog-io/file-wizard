package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve date taken of a media file
 * attribute: {dateTaken:format}
 */
public class DateTaken implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return "timestamp".equals(attributeValue)
                ? Files.getDateTakenAsTimestamp(item.getFile())
                : Files.getDateTaken(item.getFile(), attributeValue);
    }

    @Override
    public void validateAttributeValue(String attributeValue) {
    }
}

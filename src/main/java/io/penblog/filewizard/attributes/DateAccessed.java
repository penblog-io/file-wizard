package io.penblog.filewizard.attributes;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve last accessed date of a file
 * attribute: {dateAccessed:format}
 */
public class DateAccessed implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        return "timestamp".equals(attributeValue)
                ? Files.getDateAccessedAsTimestamp(item.getFile(), attributeValue)
                : Files.getDateAccessed(item.getFile(), attributeValue);
    }

    @Override
    public void validateAttributeValue(String attributeValue) {
    }
}

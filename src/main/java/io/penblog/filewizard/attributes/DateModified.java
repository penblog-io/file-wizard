package io.penblog.filewizard.attributes;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

public class DateModified implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        return Files.getDateModified(item.getFile(), attributeValue);
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

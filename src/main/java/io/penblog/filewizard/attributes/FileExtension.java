package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

public class FileExtension implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) {
        return Files.getExtension(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {
    }
}

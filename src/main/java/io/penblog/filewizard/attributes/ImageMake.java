package io.penblog.filewizard.attributes;

import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

public class ImageMake implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException, AttributeNotFoundException {
        return Files.getImageMake(item.getFile());
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

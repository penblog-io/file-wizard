package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Retrieve a size of a file
 * attribute: {fileSize:unit}
 */
public class FileSize implements AttributeGeneratorInterface {

    private static final List<String> unitList = new ArrayList<>(Arrays.asList("kb", "mb", "gb", "tb"));
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        Long size = Files.getFileSize(item.getFile());

        switch (attributeValue.toLowerCase()) {
            case "kb" -> size = size / 1024;
            case "mb" -> size = size / 1024 / 1024;
            case "gb" -> size = size / 1024 / 1024 / 1024;
            case "tb" -> size = size / 1024 / 1024 / 1024 / 1024;
        }
        return size + "";
    }

    /**
     * Validate if the unit is not in the list
     */
    @Override
    public void validateAttributeValue(String attributeValue) throws IllegalAttributeValueException {
        String value = attributeValue.toLowerCase();
        if(!unitList.contains(value)) throw new IllegalAttributeValueException();
    }
}

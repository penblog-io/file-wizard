package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;
import java.util.regex.Pattern;

public class FileName implements AttributeGeneratorInterface {
    /**
     * @param attributeValue Provide start and end to indexes to get substring of the file name:
     *                       part 1: start index
     *                       part 2: end index
     */
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        String name = Files.getName(item.getFile());

        if (!attributeValue.isEmpty()) {
            String[] index = attributeValue.split(",");
            int start = 0;
            int end = name.length();
            try {
                if (index.length >= 1) start = Integer.parseInt(index[0]);
                if (index.length >= 2) end = Integer.parseInt(index[1]);
            } catch (NumberFormatException ignored) {
            }

            if (end > name.length()) end = name.length();
            name = start >= end ? "" : name.substring(start, end);
        }

        return name;
    }

    @Override
    public void validateAttributeValue(String attributeValue) throws IllegalAttributeValueException {
        if (!attributeValue.isEmpty()) {
            Pattern pattern = Pattern.compile("\\d+,?\\d*");
            if (!pattern.matcher(attributeValue).matches()) throw new IllegalAttributeValueException();
        }
    }
}

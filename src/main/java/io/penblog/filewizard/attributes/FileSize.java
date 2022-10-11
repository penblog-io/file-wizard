package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

public class FileSize implements AttributeGeneratorInterface {
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

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

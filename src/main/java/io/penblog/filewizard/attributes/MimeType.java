package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve a mime type of file, currently, this is very limited to Java default method probeContentType
 * attribute: {mimeType}
 */
public class MimeType implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        String mimeType = Files.getMimeType(item.getFile());
        if(mimeType == null) return "";
        if(attributeValue.isEmpty()) mimeType = mimeType.replace("/", "+");
        else mimeType = mimeType.replace("/", attributeValue);
        return mimeType;
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

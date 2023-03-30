package io.penblog.filewizard.attributes;

import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.helpers.MimeType;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;

/**
 * Retrieve file type based on mime type, currently, it only support media type
 * attribute: {fileType}
 * TODO: File type is currently support a limited number of mime types defined in json/mime-types.json
 *       more will be added in the future
 */
public class FileType implements AttributeGeneratorInterface {
    @Override
    public String generate(Item item, String attributeValue) throws IOException {
        String mimeType = Files.getMimeType(item.getFile());
        if(mimeType == null || !MimeType.getInstance().has(mimeType)) return "";

        return MimeType.getInstance().getName(mimeType);
    }

    @Override
    public void validateAttributeValue(String attributeValue) {

    }
}

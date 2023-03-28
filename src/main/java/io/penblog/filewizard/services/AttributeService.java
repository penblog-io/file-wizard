package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;
import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import io.penblog.filewizard.helpers.EnumHelper;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class AttributeService {

    private final Map<io.penblog.filewizard.enums.Attribute, AttributeGeneratorInterface>
            attributeGenerators = new HashMap<>();

    public void registerAttributeGenerator(io.penblog.filewizard.enums.Attribute attribute,
                                           AttributeGeneratorInterface attributeGenerator) {
        attributeGenerators.put(attribute, attributeGenerator);
    }

    public String convert(String name, List<Attribute> attributes, Item item) {
        String newName = name;
        for (Attribute attr : attributes) {

            if (attr.isRegistered() && attr.isValidValue()) {

                AttributeGeneratorInterface attributeGenerator = attributeGenerators.get(attr.attribute());

                if (attributeGenerator != null) {
                    String value = "";
                    String original = attr.original().replaceAll("[{}]", "");

                    try {
                        value = attributeGenerator.generate(item, attr.value());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AttributeNotFoundException ignored) {
                    }

                    newName = newName.replaceAll("(?<!\\\\)\\{" + original + "(?<!\\\\)}", value);
                }
            } else {
                newName = newName.replaceAll("(?<!\\\\)\\{" + attr.original() + "(?<!\\\\)}", "");
            }
        }
        newName = newName.replaceAll("\\\\\\{", "{");
        newName = newName.replaceAll("\\\\}", "}");

        return newName;
    }


    public List<Attribute> getAttributes(String name) {
        if (name == null) return new ArrayList<>();
        return Pattern.compile("(?<!\\\\)\\{(.*?)(?<!\\\\)}")
                .matcher(name)
                .results()
                .map(matches -> {
                    String originalString = matches.group(1);
                    String attributeName, attributeValue = "";

                    int i = originalString.indexOf(":");
                    if(i > 0) {
                        attributeName = originalString.substring(0, i).trim();
                        attributeValue = originalString.substring(i + 1).trim();
                    }
                    else attributeName = originalString.trim();

                    io.penblog.filewizard.enums.Attribute attribute =
                            EnumHelper.getByKey(io.penblog.filewizard.enums.Attribute.class, attributeName);

                    boolean isValidAttributeValue = true;
                    if (attribute != null) {
                        try {
                            attributeGenerators.get(attribute).validateAttributeValue(attributeValue);
                        } catch (IllegalAttributeValueException e) {
                            isValidAttributeValue = false;
                        }
                    }

                    return new Attribute(
                            originalString,
                            attribute,
                            attributeValue,
                            isValidAttributeValue
                    );

                })
                .collect(Collectors.toList());
    }

}

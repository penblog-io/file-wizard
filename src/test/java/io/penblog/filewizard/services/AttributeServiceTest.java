package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.interfaces.AttributeGeneratorInterface;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.AttributeNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AttributeServiceTest {

    @ParameterizedTest
    @MethodSource("convertData")
    @Order(2)
    void convert(String name, String expected, String[] returned) {
        AttributeService attributeService = new AttributeService();
        AttributeGeneratorInterface attributeGenerator = mock(AttributeGeneratorInterface.class);
        for (io.penblog.filewizard.enums.Attribute a : io.penblog.filewizard.enums.Attribute.values()) {
            attributeService.registerAttributeGenerator(a, attributeGenerator);
        }

        Item item = mock(Item.class);
        List<Attribute> attributes = attributeService.getAttributes(name);
        try {
            for(int i = 0; i < attributes.size(); i++) {
                Attribute attribute = attributes.get(i);
                String r = returned[i];
                when(attributeGenerator.generate(item, attribute.value())).thenReturn(r);
            }
        } catch (IOException | AttributeNotFoundException e) {
            throw new RuntimeException(e);
        }


        String result = attributeService.convert(name, attributes, item);
        assertEquals(expected, result);
    }

    static Stream<Arguments> convertData() {
        return Stream.of(
                arguments("fileWizard.app", "fileWizard.app", new String[]{}),
                arguments("fileWizard-{fileSize}.app", "fileWizard-123.app", new String[]{"123"}),
                arguments("fileWizard-{dateCreated:yyyy}-{fileSize}.app", "fileWizard-2022-123.app", new String[]{"2022", "123"}),
                arguments("fileWizard-{}.app", "fileWizard-.app", new String[]{""}),
                arguments("fileWizard-\\{}.app", "fileWizard-{}.app", new String[]{}),
                arguments("fileWizard-{\\}.app", "fileWizard-{}.app", new String[]{})
        );
    }


    @ParameterizedTest
    @MethodSource("getAttributesData")
    @Order(1)
    void getAttributes(String name, List<Attribute> expected) {
        AttributeService attributeService = ServiceContainer.getAttributeService();
        List<Attribute> result = attributeService.getAttributes(name);

        assertEquals(expected.size(), result.size());
        if (expected.size() == result.size()) {
            for (int i = 0; i < expected.size(); i++) {
                Attribute expectedAttr = expected.get(i);
                Attribute resultAttr = result.get(i);

                assertEquals(expectedAttr.attribute(), resultAttr.attribute());
                assertEquals(expectedAttr.value(), resultAttr.value());
                assertEquals(expectedAttr.isRegistered(), resultAttr.isRegistered());
            }
        }
    }

    static Stream<Arguments> getAttributesData() {
        return Stream.of(
                arguments("", List.of()),
                arguments("FileWizard-{", List.of()),
                arguments("FileWizard-}", List.of()),
                arguments("FileWizard-\\{}", List.of()),
                arguments("FileWizard-{\\\\}", List.of()),
                arguments("FileWizard-{dateTaken:yyyy-MM-dd}", List.of(
                        new Attribute("", io.penblog.filewizard.enums.Attribute.DATE_TAKEN, "yyyy-MM-dd", true)
                )),
                arguments("FileWizard-{ dateCreated : dd }", List.of(
                        new Attribute("", io.penblog.filewizard.enums.Attribute.DATE_CREATED, "dd", true)
                )),
                arguments("FileWizard-{dateCreated:dd}{fileSize}", List.of(
                        new Attribute("", io.penblog.filewizard.enums.Attribute.DATE_CREATED, "dd", true),
                        new Attribute("", io.penblog.filewizard.enums.Attribute.FILE_SIZE, "", true)
                )),
                arguments("FileWizard-{}", List.of(
                        new Attribute("", null, "", true)
                )),
                arguments("FileWizard-{madeUpAttribute}", List.of(
                        new Attribute("", null, "", true)
                ))
        );
    }
}
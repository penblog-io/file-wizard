package io.penblog.filewizard.attributes;

import io.penblog.filewizard.exceptions.IllegalAttributeValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FileNameTest {

    @ParameterizedTest
    @MethodSource("generateData")
    void generate(Item item, String attributeValue, String expected) {

        FileName attribute = new FileName();
        String value = attribute.generate(item, attributeValue);
        assertEquals(expected, value);
    }

    static Stream<Arguments> generateData() {
        Map<String, Item> items = Dataset.items();
        return Stream.of(
                arguments(items.get("Java (Programming Language)"), "", "Java (Programming Language)"),
                arguments(items.get("IMG_001.JPG"), "", "IMG_001"),
                arguments(items.get("Avengers: Endgame.mp4"), "0,8", "Avengers"),
                arguments(items.get("John Lennon - Imagine.mp3"), "14,21", "Imagine"),
                arguments(items.get("IMG_0762.MOV"), "4", "0762")
        );
    }


    @ParameterizedTest
    @MethodSource("validateAttributeValueThrowData")
    void validateAttributeValueThrow(String value) {
        FileName attribute = new FileName();
        Exception thrown = assertThrows(IllegalAttributeValueException.class, ()
                -> attribute.validateAttributeValue(value));
        assertTrue(thrown.getMessage().contains("Invalid"));
    }

    static Stream<Arguments> validateAttributeValueThrowData() {
        return Stream.of(
                arguments("a"),
                arguments("a,a"),
                arguments("1,b"),
                arguments("1,1,2"),
                arguments(",2"),
                arguments("a,1")
        );
    }


    @ParameterizedTest
    @MethodSource("validateAttributeValueDoesNotThrowData")
    void validateAttributeValueDoesNotThrow(String value) {
        FileName attribute = new FileName();
        assertDoesNotThrow(() -> attribute.validateAttributeValue(value));
    }

    static Stream<Arguments> validateAttributeValueDoesNotThrowData() {
        return Stream.of(
                arguments(""),
                arguments("1"),
                arguments("1,2"),
                arguments("2,30")
        );
    }
}
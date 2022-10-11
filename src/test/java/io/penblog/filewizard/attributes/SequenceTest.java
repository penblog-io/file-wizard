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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SequenceTest {

    @ParameterizedTest
    @MethodSource("generateData")
    void generate(Item item, int index, String attributeValue, String expected) {
        item.setIndex(index);

        Sequence attribute = new Sequence();
        try {
            String value = attribute.generate(item, attributeValue);
            assertEquals(expected, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> generateData() {
        Map<String, Item> items = Dataset.items();
        return Stream.of(
                arguments(items.get("IMG_001.JPG"), 0, "number,1,1,1", "1"),
                arguments(items.get("IMG_001.JPG"), 1, "number,1000,1,1", "1001"),
                arguments(items.get("IMG_001.JPG"), 1, "number,1000,5,1", "1005"),
                arguments(items.get("IMG_001.JPG"), 10, "number,10,10,1", "110"),
                arguments(items.get("IMG_001.JPG"), 10, "number,10,10,1,AAAAA", "AA110"),
                arguments(items.get("IMG_001.JPG"), 100, "number,1000,1,1,ABCDEFG", "ABC1100"),
                arguments(items.get("IMG_001.JPG"), 4, "number,1,1,5", "1"),
                arguments(items.get("IMG_001.JPG"), 5, "number,1,1,5", "2"),
                arguments(items.get("IMG_001.JPG"), 100, "number,1000,1,100", "1001"),

                arguments(items.get("IMG_001.JPG"), 0, "number,10,-1,1", "10"),
                arguments(items.get("IMG_001.JPG"), 2, "number,10,-5,1", "0"),
                arguments(items.get("IMG_001.JPG"), 2, "number,100,-10,1", "80"),
                arguments(items.get("IMG_001.JPG"), 3, "number,100,-10,1,GGG", "G70"),

                arguments(items.get("IMG_001.JPG"), 0, "letter,A,1,1", "A"),
                arguments(items.get("IMG_001.JPG"), 1, "letter,A,1,1", "B"),
                arguments(items.get("IMG_001.JPG"), 25, "letter,A,1,1", "Z"),
                arguments(items.get("IMG_001.JPG"), 26, "letter,A,1,1", "AA"),
                arguments(items.get("IMG_001.JPG"), 52, "letter,A,1,1", "BA"),
                arguments(items.get("IMG_001.JPG"), 1, "letter,ZZZZZZ,1,1", "AAAAAAA"),
                arguments(items.get("IMG_001.JPG"), 25, "letter,ZZZZZZ,-1,1", "ZZZZZA"),
                arguments(items.get("IMG_001.JPG"), 1, "letter,AAA,-1,1", "ZZ"),
                arguments(items.get("IMG_001.JPG"), 2, "letter,AAA,-1,2", "ZZ"),
                arguments(items.get("IMG_001.JPG"), 2, "letter,AAA,-1,3", "AAA")
        );
    }


    @ParameterizedTest
    @MethodSource("validateAttributeValueThrowData")
    void validateAttributeValueThrow(String value) {
        Sequence attribute = new Sequence();
        Exception thrown = assertThrows(IllegalAttributeValueException.class, ()
                -> attribute.validateAttributeValue(value));
        assertTrue(thrown.getMessage().contains("Invalid"));
    }

    static Stream<Arguments> validateAttributeValueThrowData() {
        return Stream.of(
                arguments("number"),
                arguments("alpha,1,1"),
                arguments("letter,1,1,0,1,2"),
                arguments("alpha,A,1,0,ABC"),
                arguments("letter,1,1,1,ABC"),
                arguments("letter,1,A,1,ABC")
        );
    }


    @ParameterizedTest
    @MethodSource("validateAttributeValueDoesNotThrowData")
    void validateAttributeValueDoesNotThrow(String value) {
        Sequence attribute = new Sequence();
        assertDoesNotThrow(() -> attribute.validateAttributeValue(value));
    }

    static Stream<Arguments> validateAttributeValueDoesNotThrowData() {
        return Stream.of(
                arguments("number,1"),
                arguments("letter,A,1"),
                arguments("letter,A,1,1"),
                arguments("number,1,1,1"),
                arguments("number,1,1,1,0000"),
                arguments("number,1,1,10,0000")
        );
    }
}
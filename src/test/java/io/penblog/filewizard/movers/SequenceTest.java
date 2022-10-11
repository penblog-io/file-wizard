package io.penblog.filewizard.movers;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.OptionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.MissingOptionException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SequenceTest {

    @ParameterizedTest
    @MethodSource("moveData")
    public void move(Item item, String appendText, String expected) {

        Sequence mover = new Sequence();

        OptionService option = new OptionService();
        option.put(Option.MOVE_SEQUENCE_TEXT, appendText);

        try {
            String name = mover.move(item, option, new ArrayList<>());
            assertEquals(expected, name);
        } catch (MissingOptionException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> moveData() {
        Map<String, Item> dataset = Dataset.items();
        return Stream.of(
                arguments(dataset.get("Notepad"), "0001", "0001"),
                arguments(dataset.get("IMG_001.JPG"), "000A", "000A"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), "Z", "Z")
        );
    }
}
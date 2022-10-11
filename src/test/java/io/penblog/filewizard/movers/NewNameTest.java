package io.penblog.filewizard.movers;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.OptionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class NewNameTest {

    @ParameterizedTest
    @MethodSource("moveData")
    public void move(Item item, String newNameText, String expected) {

        NewName mover = new NewName();

        OptionService option = new OptionService();
        option.put(Option.MOVE_NEW_NAME_TEXT, newNameText);

        try {
            String name = mover.move(item, option, new ArrayList<>());
            assertEquals(expected, name);
        } catch (SameFilenameException ignored) {
        } catch (MissingOptionException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> moveData() {
        Map<String, Item> dataset = Dataset.items();
        return Stream.of(
                arguments(dataset.get("Notepad"), "", "Notepad"),
                arguments(dataset.get("IMG_001.JPG"), "0001", "0001"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), "2030-01-01", "2030-01-01"),
                arguments(dataset.get("VID_20161230_151652.mp4"), "435", "435")
        );
    }
}
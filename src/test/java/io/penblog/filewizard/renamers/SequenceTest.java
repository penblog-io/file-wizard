package io.penblog.filewizard.renamers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SequenceTest {

    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, String appendText, String expected) {

        Sequence renamer = new Sequence();

        OptionService option = new OptionService();
        option.put(Option.RENAME_SEQUENCE_TEXT, appendText);

        try {
            String name = renamer.rename(item, option, new ArrayList<>());
            assertEquals(expected, name);
        } catch (SameFilenameException ignored) {
        } catch (MissingOptionException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> renameData() {
        Map<String, Item> dataset = Dataset.items();
        return Stream.of(
                arguments(dataset.get("Notepad"), "0001", "0001"),
                arguments(dataset.get("IMG_001.JPG"), "000A", "000A.JPG"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), "Z", "Z.xlsx")
        );
    }
}
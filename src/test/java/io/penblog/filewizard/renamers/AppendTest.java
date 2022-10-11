package io.penblog.filewizard.renamers;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.services.OptionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;


public class AppendTest {

    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, String appendText, String expected) {

        Append renamer = new Append();

        OptionService option = new OptionService();
        option.put(Option.RENAME_APPEND_TEXT, appendText);

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
                arguments(dataset.get("Notepad"), "", "Notepad"),
                arguments(dataset.get("IMG_001.JPG"), "_ABCD", "IMG_001_ABCD.JPG"),
                arguments(dataset.get("IMG_001.JPG"), "_2022", "IMG_001_2022.JPG"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), " - part 2", "Financial Statement 2020 - part 2.xlsx")
        );
    }
}
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

class RemoveTest {

    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, String removeText, boolean isCaseInsensitive, boolean isRegex, String expected) {

        Remove renamer = new Remove();

        OptionService option = new OptionService();
        option.put(Option.RENAME_REMOVE_TEXT, removeText);
        option.put(Option.RENAME_REMOVE_CASE_INSENSITIVE, isCaseInsensitive);
        option.put(Option.RENAME_REMOVE_REGEX, isRegex);

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
                arguments(dataset.get("Notepad"), "", false, false, "Notepad"),
                arguments(dataset.get("IMG_001.JPG"), "_", false, false, "IMG001.JPG"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), "Financial ", false, false, "Statement 2020.xlsx"),
                arguments(dataset.get("Java (Programming Language)"), "java", false, false, "Java (Programming Language)"),
                arguments(dataset.get("Avengers: Endgame.mp4"), " endgame", true, false, "Avengers:.mp4"),
                arguments(dataset.get("VID_20161230_151652.mp4"), "\\d", true, true, "VID__.mp4")
        );
    }
}
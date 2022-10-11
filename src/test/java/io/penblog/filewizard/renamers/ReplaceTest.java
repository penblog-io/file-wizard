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

class ReplaceTest {

    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, String searchText, String replaceText, String expected) {

        Replace renamer = new Replace();

        OptionService option = new OptionService();
        option.put(Option.RENAME_REPLACE_SEARCH, searchText);
        option.put(Option.RENAME_REPLACE_TEXT, replaceText);

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
                arguments(dataset.get("Notepad"), "", "", "Notepad"),
                arguments(dataset.get("IMG_001.JPG"), "", "ABC", "IMG_001.JPG"),
                arguments(dataset.get("Civil War.docx"), "War", "Peace", "Civil Peace.docx"),
                arguments(dataset.get("Financial Statement 2020.xlsx"), "2020", "2030", "Financial Statement 2030.xlsx")
        );
    }
}
package io.penblog.filewizard.renamers;

import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.services.OptionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.UpperCaseOption;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UpperCaseTest {
    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, UpperCaseOption upperCaseOption, String specificChars, String expected) {

        UpperCase renamer = new UpperCase();

        OptionService option = new OptionService();
        option.put(Option.RENAME_UPPERCASE_OPTION, upperCaseOption);
        option.put(Option.RENAME_UPPERCASE_SPECIFIC_CHARACTERS_TEXT, specificChars);

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
                arguments(dataset.get("Notepad"), UpperCaseOption.ALL_CHARACTERS, "", "NOTEPAD"),
                arguments(dataset.get("IMG_001.JPG"), UpperCaseOption.ALL_CHARACTERS, "", "IMG_001.JPG"),
                arguments(dataset.get("Java (Programming Language)"), UpperCaseOption.SPECIFIC_CHARACTERS, "JAVA", "JAVA (Programming Language)"),
                arguments(dataset.get("Financial Statement 2021.xlsx"), UpperCaseOption.SPECIFIC_CHARACTERS, "a", "FinAnciAl StAtement 2021.xlsx")
        );
    }
}
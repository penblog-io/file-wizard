package io.penblog.filewizard.renamers;

import io.penblog.filewizard.enums.options.LowerCaseOption;
import io.penblog.filewizard.services.OptionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.penblog.filewizard.Dataset;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LowerCaseTest {
    @ParameterizedTest
    @MethodSource("renameData")
    public void rename(Item item, LowerCaseOption lowerCaseOption, String specificChars, String expected) {

        LowerCase renamer = new LowerCase();

        OptionService option = new OptionService();
        option.put(Option.RENAME_LOWERCASE_OPTION, lowerCaseOption);
        option.put(Option.RENAME_LOWERCASE_SPECIFIC_CHARACTERS_TEXT, specificChars);

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
                arguments(dataset.get("Notepad"), LowerCaseOption.ALL_CHARACTERS, "", "notepad"),
                arguments(dataset.get("IMG_001.JPG"), LowerCaseOption.ALL_CHARACTERS, "", "img_001.JPG"),
                arguments(dataset.get("Civil War - Part II.docx"), LowerCaseOption.ALL_CHARACTERS, "", "civil war - part ii.docx"),
                arguments(dataset.get("WIKIPEDIA.pdf"), LowerCaseOption.SPECIFIC_CHARACTERS, "wiki", "wikiPEDIA.pdf"),
                arguments(dataset.get("WIKIPEDIA.pdf"), LowerCaseOption.SPECIFIC_CHARACTERS, "WIKI", "wikiPEDIA.pdf")
        );
    }
}
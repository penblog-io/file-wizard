package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.LowerCaseOption;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.OptionService;

import java.util.List;
import java.util.regex.Pattern;


public class LowerCase extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        LowerCaseOption option = (LowerCaseOption) optionService.get(Option.RENAME_LOWERCASE_OPTION);
        String specificText = optionService.getString(Option.RENAME_LOWERCASE_SPECIFIC_CHARACTERS_TEXT);
        if (option == null) throw new MissingOptionException();
        if (option == LowerCaseOption.SPECIFIC_CHARACTERS && specificText == null) throw new MissingOptionException();

        String name = Files.getName(item.getFile());
        String newName = name;
        if (option == LowerCaseOption.ALL_CHARACTERS) {
            newName = name.toLowerCase();
        } else if (option == LowerCaseOption.SPECIFIC_CHARACTERS) {
            newName = Pattern.compile(specificText, Pattern.CASE_INSENSITIVE).matcher(name)
                    .replaceAll(specificText.toLowerCase());
        }

        if (newName.equals(name)) throw new SameFilenameException();

        String extension = Files.getExtension(item.getFile());
        if (!extension.isEmpty()) extension = "." + extension;
        return newName + extension;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return null;
    }

}

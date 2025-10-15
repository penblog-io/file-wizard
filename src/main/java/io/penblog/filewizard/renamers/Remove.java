package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.OptionService;
import io.penblog.filewizard.components.Item;

import java.util.List;
import java.util.regex.Pattern;

public class Remove extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        String newName;
        String remove = optionService.getString(Option.RENAME_REMOVE_TEXT);
        Boolean caseInsensitive = optionService.getBoolean(Option.RENAME_REMOVE_CASE_INSENSITIVE);
        Boolean regex = optionService.getBoolean(Option.RENAME_REMOVE_REGEX);
        if (remove == null || caseInsensitive == null || regex == null) throw new MissingOptionException();

        remove = getAttributeService().convert(remove, attributes, item);
        if (remove.equals("")) throw new SameFilenameException();

        String name = Files.getName(item.getFile());

        if (regex || caseInsensitive) {
            Pattern pattern = caseInsensitive ?
                    Pattern.compile(remove, Pattern.CASE_INSENSITIVE) :
                    Pattern.compile(remove);
            newName = pattern.matcher(name).replaceAll("");
        } else {
            newName = name.replaceFirst(remove, "");
        }

        if (newName.equals(name)) throw new SameFilenameException();

        String extension = Files.getExtension(item.getFile());
        if (!extension.isEmpty()) extension = "." + extension;
        return newName + extension;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.RENAME_REMOVE_TEXT);
    }
}

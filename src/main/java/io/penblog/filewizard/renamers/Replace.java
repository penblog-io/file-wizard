package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.OptionService;

import java.util.List;


public class Replace extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        String search = optionService.getString(Option.RENAME_REPLACE_SEARCH);
        String replace = optionService.getString(Option.RENAME_REPLACE_TEXT);
        if (search == null || replace == null) throw new MissingOptionException();

        if (search.isEmpty()) throw new SameFilenameException();

        replace = getAttributeService().convert(replace, attributes, item);

        String name = Files.getName(item.getFile());
        String extension = Files.getExtension(item.getFile());
        if (!extension.isEmpty()) extension = "." + extension;
        return name.replaceAll(search, replace) + extension;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.RENAME_REPLACE_SEARCH)
                + optionService.getString(Option.RENAME_REPLACE_TEXT);
    }

}

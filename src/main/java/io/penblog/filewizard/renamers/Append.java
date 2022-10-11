package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.OptionService;

import java.util.List;


public class Append extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {
        String append = optionService.getString(Option.RENAME_APPEND_TEXT);
        if (append == null) throw new MissingOptionException();

        append = getAttributeService().convert(append, attributes, item);
        if (append.equals("")) throw new SameFilenameException();

        String name = Files.getName(item.getFile());
        String extension = Files.getExtension(item.getFile());
        if (!extension.isEmpty()) extension = "." + extension;

        return name + append + extension;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.RENAME_APPEND_TEXT);
    }

}

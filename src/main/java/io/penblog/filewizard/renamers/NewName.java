package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.Files;
import io.penblog.filewizard.services.OptionService;

import java.util.List;

public class NewName extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        String name = Files.getName(item.getFile());

        String newName = optionService.getString(Option.RENAME_NEW_NAME_TEXT);
        if (newName == null) throw new MissingOptionException();

        newName = getAttributeService().convert(newName, attributes, item);
        if (newName.equals("") || name.equals(newName)) throw new SameFilenameException();


        String ext = Files.getExtension(item.getFile());
        if (ext.length() > 0) ext = "." + ext;
        return newName + ext;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.RENAME_NEW_NAME_TEXT);
    }

}

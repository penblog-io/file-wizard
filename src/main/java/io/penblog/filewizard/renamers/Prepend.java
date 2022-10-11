package io.penblog.filewizard.renamers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.services.OptionService;

import java.util.List;

public class Prepend extends RenamerAbstract {

    public String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        String prepend = optionService.getString(Option.RENAME_PREPEND_TEXT);
        if (prepend == null) throw new MissingOptionException();

        prepend = getAttributeService().convert(prepend, attributes, item);
        if (prepend.equals("")) throw new SameFilenameException();

        return prepend + item.getOriginalFilename();
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.RENAME_PREPEND_TEXT);
    }

}

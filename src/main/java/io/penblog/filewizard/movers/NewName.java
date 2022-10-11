package io.penblog.filewizard.movers;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.services.OptionService;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.exceptions.MissingOptionException;

import java.util.List;

public class NewName extends MoverAbstract {

    public String move(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException {

        String newName = optionService.getString(Option.MOVE_NEW_NAME_TEXT);
        if (newName == null) throw new MissingOptionException();

        newName = attributeService.convert(newName, attributes, item);
        if (newName.equals("")) throw new SameFilenameException();

        return newName;
    }

    @Override
    public String optionToString(OptionService optionService) {
        return optionService.getString(Option.MOVE_NEW_NAME_TEXT);
    }
}

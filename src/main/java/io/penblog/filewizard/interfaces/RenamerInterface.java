package io.penblog.filewizard.interfaces;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.services.OptionService;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.exceptions.MissingOptionException;

import java.util.List;

public interface RenamerInterface {
    String rename(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException;

    String optionToString(OptionService optionService);
}

package io.penblog.filewizard.interfaces;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.services.OptionService;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;

import java.util.List;

public interface MoverInterface {
    String move(Item item, OptionService optionService, List<Attribute> attributes)
            throws MissingOptionException, SameFilenameException;

    String optionToString(OptionService optionService);
}

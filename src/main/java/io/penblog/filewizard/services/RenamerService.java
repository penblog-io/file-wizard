package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Attribute;
import io.penblog.filewizard.interfaces.RenamerInterface;
import javafx.collections.ObservableList;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.RenameMethod;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.SystemUtils;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RenamerService {

    /**
     * When generating new file names, some of them may result in the same name, so this variable keep track
     * of duplicate new file name. Duplicate file names won't be able to rename
     */
    private final Map<String, List<String>> duplicate = new HashMap<>();
    private final ItemService itemService = new ItemService();

    /**
     * List of available renamers, new movers can be registered in ServiceContainer
     */
    private final Map<RenameMethod, RenamerInterface> renamers = new HashMap<>();
    private final OptionService optionService;
    private final AttributeService attributeService;

    public RenamerService(OptionService optionService, AttributeService attributeService) {
        this.optionService = optionService;
        this.attributeService = attributeService;
    }

    public void registerRenamer(RenameMethod method, RenamerInterface renamer) {
        renamers.put(method, renamer);
    }

    public void preview(RenameMethod method) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                RenamerInterface renamer = renamers.get(method);
                duplicate.clear();

                if (renamer != null) {

                    List<Attribute> attributes = attributeService.getAttributes(renamer.optionToString(optionService));

                    for (Item item : itemService.getItems()) {

                        // check if file exists, a file and can be moved or deleted
                        if (!item.getFile().exists()) {
                            item.setError(true, "(File is not found.)");
                            continue;
                        }

                        try {
                            String newFilename = renamer.rename(item, optionService, attributes);
                            item.setNewFilename(newFilename);
                            item.setError(false);

                            String key = item.getFile().getParent() + "/" + newFilename;
                            if (SystemUtils.isWindows()) key = key.toLowerCase();
                            if (!duplicate.containsKey(key)) {
                                duplicate.put(key, new ArrayList<>());
                            }
                            // add new file name to duplicate hash map, if one absolute path has two or more
                            // entry, their new names are duplicate
                            duplicate.get(key).add(item.getFile().getAbsolutePath());

                        } catch (SameFilenameException e) {
                            item.setError(true, "(Filename did not change, skip)");
                        } catch (MissingOptionException e) {
                            e.printStackTrace();
                        }
                    }

                    // set error flag if one absolute path has two entries.
                    for (Map.Entry<String, List<String>> entry : duplicate.entrySet()) {
                        if (entry.getValue().size() > 1) {
                            for (String key : entry.getValue()) {
                                itemService.get(key).setError(true, "(Duplicate filename, skip)");
                            }
                        }
                    }
                }
                return null;
            }
        }).start();

    }

    public void setOption(Option option, Object value) {
        optionService.put(option, value);
    }

    public void setFiles(List<File> files) {
        itemService.put(files.stream().map(Item::new).toList());
    }

    /**
     * Rename files to the new names based on the selected renaming method.
     */
    public void rename() {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                for (Item item : itemService.getItems()) {

                    // only rename if not error
                    if (item.isError()) continue;

                    Path p = Paths.get(item.getFile().toURI());
                    try {
                        Path newPath = p.resolveSibling(item.getNewFilename());
                        Files.move(p, newPath);
                        item.setFile(new File(newPath.toUri()));
                        item.setNewFilename("");

                    } catch (IOException e) {
                        item.setError(true, "Cannot rename file.");
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }

    public ObservableList<Item> getItems() {
        return itemService.getItems();
    }

    public void removeItems(List<Item> items) {
        itemService.remove(items);
    }

    public void clearItems() {
        itemService.clear();
    }

    public void clearNonErrorItems() {
        List<File> errorFiles = new ArrayList<>();
        for (Item item : itemService.getItems()) {
            if (item.isError()) errorFiles.add(item.getFile());
        }
        itemService.clear();
        setFiles(errorFiles);
    }
}

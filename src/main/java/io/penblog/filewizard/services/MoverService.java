package io.penblog.filewizard.services;

import io.penblog.filewizard.components.Attribute;
import javafx.collections.ObservableList;
import io.penblog.filewizard.components.Item;
import io.penblog.filewizard.enums.MoveMethod;
import io.penblog.filewizard.enums.options.Option;
import io.penblog.filewizard.exceptions.MissingOptionException;
import io.penblog.filewizard.exceptions.SameFilenameException;
import io.penblog.filewizard.helpers.SystemUtils;
import io.penblog.filewizard.interfaces.MoverInterface;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoverService {

    /**
     * When generating new file names, some of them may result in the same name, so this variable keep track
     * of duplicate new file name. Duplicate file names won't be able to rename
     */
    private final Map<String, List<String>> duplicate = new HashMap<>();
    private final ItemService itemService = new ItemService();

    /**
     * List of available movers, new movers can be registered in ServiceContainer
     */
    private final Map<MoveMethod, MoverInterface> movers = new HashMap<>();
    private final OptionService optionService;
    private final AttributeService attributeService;
    private File moveTarget;

    public MoverService(OptionService optionService, AttributeService attributeService) {
        this.optionService = optionService;
        this.attributeService = attributeService;
    }

    public void registerMover(MoveMethod method, MoverInterface mover) {
        movers.put(method, mover);
    }

    public void setMoveTarget(File moveTarget) {
        this.moveTarget = moveTarget;
    }

    public void preview(MoveMethod method) {

        MoverInterface mover = movers.get(method);
        duplicate.clear();

        if (mover != null) {

            List<Attribute> attributes = attributeService.getAttributes(mover.optionToString(optionService));
            for (Item item : itemService.getItems()) {

                // check if file exists, a file and can be moved or deleted
                if (!item.getFile().exists()) {
                    item.setError(true, "(File is not found.)");
                    continue;
                }

                try {
                    String newFolderName = mover.move(item, optionService, attributes);
                    String parentFolderPath = moveTarget == null ?
                            item.getFile().getParent() : moveTarget.getAbsolutePath();

                    String destFolderName = parentFolderPath + (SystemUtils.isWindows() ? "\\" : "/") + newFolderName;

                    item.setDestFolderName(destFolderName);
                    item.setError(false);

                    String key = destFolderName + (SystemUtils.isWindows() ? "\\" : "/") + item.getOriginalFilename();
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
    }

    public void setOption(Option option, Object value) {
        optionService.put(option, value);
    }

    public void setFiles(List<File> files) {
        itemService.put(files.stream().map(Item::new).toList());
    }

    /**
     * Move files to their respective sub-folders
     */
    public void move() {

        for (Item item : itemService.getItems()) {
            // only rename if not error
            if (item.isError()) continue;

            File dirFile = new File(item.getDestFolderName());
            boolean dirExists;
            if (!dirFile.exists()) {
                dirExists = dirFile.mkdirs();
            } else dirExists = true;

            if (dirExists) {
                Path p = Paths.get(item.getFile().toURI());
                try {
                    Path newPath = Paths.get(item.getDestFullFilename());
                    Files.move(p, newPath);
                    item.setFile(new File(newPath.toUri()));
                } catch (IOException e) {
                    item.setError(true, "Cannot move file.");
                    e.printStackTrace();
                }
            }
        }
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

    /**
     * You do not want to clear all files, those cannot be moved remain in the list so that user can adjust
     * their settings so that they won't be duplicated
     */
    public void clearNonErrorItems() {
        List<File> errorFiles = new ArrayList<>();
        for (Item item : itemService.getItems()) {
            if (item.isError()) errorFiles.add(item.getFile());
        }
        itemService.clear();
        setFiles(errorFiles);
    }
}

package io.penblog.filewizard.helpers;

import io.penblog.filewizard.interfaces.BaseEnumInterface;
import java.util.Arrays;
import java.util.List;

/**
 * Enum helper class
 */
public class EnumHelper {

    /**
     * Retrieve all keys of an enum class
     */
    public static <T extends BaseEnumInterface> List<String> keys(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(BaseEnumInterface::key).toList();
    }

    /**
     * Retrieve all values of an enum class
     */
    public static <T extends BaseEnumInterface> List<String> values(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(BaseEnumInterface::value).toList();
    }

    /**
     * Get an enum by key from a specific enum class
     */
    public static <T extends BaseEnumInterface> T getByKey(Class<T> enumClass, String key) {
        if (key == null) return null;
        for (T e : enumClass.getEnumConstants()) {
            if (e.key().equals(key)) return e;
        }
        return null;
    }

    /**
     * Get an enum by value from a specific enum class
     */
    public static <T extends BaseEnumInterface> T getByValue(Class<T> enumClass, String value) {
        for (T e : enumClass.getEnumConstants()) {
            if (e.value().equals(value)) return e;
        }
        return null;
    }
}

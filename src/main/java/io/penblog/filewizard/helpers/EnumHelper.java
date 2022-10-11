package io.penblog.filewizard.helpers;

import io.penblog.filewizard.interfaces.BaseEnumInterface;

import java.util.Arrays;
import java.util.List;

public class EnumHelper {
    public static <T extends BaseEnumInterface> List<String> keys(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(BaseEnumInterface::key).toList();
    }

    public static <T extends BaseEnumInterface> List<String> values(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(BaseEnumInterface::value).toList();
    }

    public static <T extends BaseEnumInterface> T getByKey(Class<T> enumClass, String key) {
        if (key == null) return null;
        for (T e : enumClass.getEnumConstants()) {
            if (e.key().equals(key)) return e;
        }
        return null;
    }

    public static <T extends BaseEnumInterface> T getByValue(Class<T> enumClass, String value) {
        for (T e : enumClass.getEnumConstants()) {
            if (e.value().equals(value)) return e;
        }
        return null;
    }
}

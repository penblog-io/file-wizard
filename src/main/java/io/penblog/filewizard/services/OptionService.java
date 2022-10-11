package io.penblog.filewizard.services;


import io.penblog.filewizard.enums.options.Option;

import java.util.HashMap;
import java.util.Map;

public class OptionService {

    private final Map<Option, Object> option = new HashMap<>();

    public void put(Option key, Object value) {
        option.put(key, value);
    }

    public Object get(Option key) {
        return option.get(key);
    }

    public String getString(Option key) {
        return (String) option.get(key);
    }

    public Integer getInteger(Option key) {
        return (Integer) option.get(key);
    }

    public Boolean getBoolean(Option key) {
        return (Boolean) option.get(key);
    }
}

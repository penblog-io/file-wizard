package io.penblog.filewizard.helpers;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class MimeType {

    private static final String JSON_MIME_TYPE_LIST = "json/mime-types.json";
    private final Map<String, String> mimeTypes = new HashMap<>();
    private static MimeType mimeType;

    private MimeType() {
        InputStream is = Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(JSON_MIME_TYPE_LIST));

        JSONObject json = new JSONObject(new JSONTokener(is));
        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            String value = json.getString(key);
            mimeTypes.put(key, value);
        }
    }

    public static MimeType getInstance() {
        if (mimeType == null) mimeType = new MimeType();
        return mimeType;
    }

    public boolean has(String key) {
        return mimeTypes.containsKey(key);
    }

    public String getName(String key) {
        return mimeTypes.get(key);
    }
}

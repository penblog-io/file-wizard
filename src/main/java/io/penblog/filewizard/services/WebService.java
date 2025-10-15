package io.penblog.filewizard.services;

import javafx.application.HostServices;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebService extends BaseService {

    private String newVersion = null;
    private String currentVersion;
    private String checkUpdateUrl;
    private HostServices hostServices;

    public WebService(Logger logger) {
        super(logger);
    }

    public void setHostServices(Properties properties, HostServices hostServices) {
        this.hostServices = hostServices;
        currentVersion = properties.getProperty("version");
        checkUpdateUrl = properties.getProperty("checkUpdateUrl");
    }

    public boolean hasUpdate() {
        return version().compareTo(currentVersion) > 0;
    }

    public String version() {
        if (newVersion == null) {
            try {
                URL url = new URL(checkUpdateUrl);
                JSONObject json = new JSONObject(new JSONTokener(url.openStream()));
                newVersion = json.getString("version").trim();

            } catch (ConnectException e) {
                getLogger().log(Level.SEVERE, "Connection: " + e.getMessage());
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "IO: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newVersion == null ? currentVersion : newVersion;
    }

    public void open(String url) {
        if (hostServices != null) {
            hostServices.showDocument(url);
        }
//        if (Desktop.isDesktopSupported()) {
//            try {
//                Desktop.getDesktop().browse(new URI(url));
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
    }
}

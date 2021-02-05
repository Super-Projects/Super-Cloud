package de.z1up.supercloud.core.settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsManager {

    private final Map<String, Setting> settings = new HashMap();

    public void loadSettings() {

        /* -----------< TEMP >----------- */

        // ccs-active
        loadCCsActive();
        // console-prefix
        loadConsolePrefix();


        /* -----------< PERM >----------- */
    }

    void loadCCsActive() {

        // ccs-active

        boolean active = true;

        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            active = false;
        }

        Setting setting = new Setting("ccs-active", active);
        settings.put("ccs-active", setting);
    }

    void loadConsolePrefix() {
        String username = System.getProperty("user.name");
        String prefix = username + "@Cloud $ ";
        Setting setting = new Setting("console-prefix", prefix);
        settings.put("console-prefix", setting);
    }

    public Setting getByName(String name) {
        return settings.get(name);
    }

}

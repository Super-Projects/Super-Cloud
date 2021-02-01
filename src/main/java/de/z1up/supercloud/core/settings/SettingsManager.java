package de.z1up.supercloud.core.settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsManager ***REMOVED***

    private final Map<String, Setting> settings = new HashMap();

    public void loadSettings() ***REMOVED***

        /* -----------< TEMP >----------- */

        // ccs-active
        loadCCsActive();
        // console-prefix
        loadConsolePrefix();


        /* -----------< PERM >----------- */
    ***REMOVED***

    void loadCCsActive() ***REMOVED***

        // ccs-active

        boolean active = true;

        if(System.getProperty("os.name").toLowerCase().contains("win")) ***REMOVED***
            active = false;
        ***REMOVED***

        Setting setting = new Setting("ccs-active", active);
        settings.put("ccs-active", setting);
    ***REMOVED***

    void loadConsolePrefix() ***REMOVED***
        String username = System.getProperty("user.name");
        String prefix = username + "@Cloud $ ";
        Setting setting = new Setting("console-prefix", prefix);
        settings.put("console-prefix", setting);
    ***REMOVED***

    public Setting getByName(String name) ***REMOVED***
        return settings.get(name);
    ***REMOVED***

***REMOVED***

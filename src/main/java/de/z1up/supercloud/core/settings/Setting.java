package de.z1up.supercloud.core.settings;

import de.z1up.supercloud.core.file.CloudFile;

public class Setting ***REMOVED***

    private String key;
    private Object value;
    private CloudFile file;
    private SettingMode mode;

    public Setting(String key, Object value, CloudFile file) ***REMOVED***
        this.key = key;
        this.value = value;
        this.file = file;
        this.mode = SettingMode.PERM;
    ***REMOVED***

    public Setting(String key, Object value) ***REMOVED***
        this.key = key;
        this.value = value;
        this.file = null;
        this.mode = SettingMode.TEMP;
    ***REMOVED***

    void load() ***REMOVED***

    ***REMOVED***

    public void update() ***REMOVED***

    ***REMOVED***

    public void save() ***REMOVED***
        if(this.mode != SettingMode.PERM) ***REMOVED***
           return;
        ***REMOVED***
        if(file == null) ***REMOVED***
            return;
        ***REMOVED***
    ***REMOVED***

    public void setValue(Object value) ***REMOVED***
        this.value = value;
    ***REMOVED***

    public Object getValue() ***REMOVED***
        return this.value;
    ***REMOVED***

    public enum SettingMode ***REMOVED***
        TEMP, PERM
    ***REMOVED***

***REMOVED***

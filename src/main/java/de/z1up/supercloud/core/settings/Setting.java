package de.z1up.supercloud.core.settings;

import de.z1up.supercloud.core.file.CloudFile;

public class Setting {

    private String key;
    private Object value;
    private CloudFile file;
    private SettingMode mode;

    public Setting(String key, Object value, CloudFile file) {
        this.key = key;
        this.value = value;
        this.file = file;
        this.mode = SettingMode.PERM;
    }

    public Setting(String key, Object value) {
        this.key = key;
        this.value = value;
        this.file = null;
        this.mode = SettingMode.TEMP;
    }

    void load() {

    }

    public void update() {

    }

    public void save() {
        if(this.mode != SettingMode.PERM) {
           return;
        }
        if(file == null) {
            return;
        }
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public enum SettingMode {
        TEMP, PERM
    }

}

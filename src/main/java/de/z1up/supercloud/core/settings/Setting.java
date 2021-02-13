package de.z1up.supercloud.core.settings;

import com.google.gson.Gson;
import de.z1up.supercloud.core.Core;

public class Setting {

    private final String          key;
    private final Object          value;

    public Setting(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public void save() {
        save(this.getKey(), this.getValue());
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public static void save(final String key, final Object value) {
        Core.getInstance().getSettingsManager().save(new Setting(key, value));
    }

    public static void load(final String key, final Object value) {
        Core.getInstance().getSettingsManager().load(new Setting(key, value));
    }

    public static Setting parse(final String json) {
        final Gson gson = new Gson();
        final Setting setting
                = gson.fromJson(json, Setting.class);
        return setting;
    }

    public static boolean getBool(final String key) {

        final Setting setting = Core.getInstance().getSettingsManager().getSetting(key);
        final boolean value = (boolean) setting.getValue();
        return value;
    }

    public static String getText(final String key) {

        final Setting setting = Core.getInstance().getSettingsManager().getSetting(key);
        final String value = (String) setting.getValue();
        return value;
    }

    public static Number getNumber(final String key) {

        final Setting setting = Core.getInstance().getSettingsManager().getSetting(key);
        final Number value = (Number) setting.getValue();
        return value;
    }

}

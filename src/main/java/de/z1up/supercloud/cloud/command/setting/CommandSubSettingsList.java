package de.z1up.supercloud.cloud.command.setting;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.settings.Setting;

import java.util.List;

public class CommandSubSettingsList extends SubCommand {

    public CommandSubSettingsList(Command superCommand) {
        super("settings_list",
                "List all the possible System- Settings",
                "settings list",
                null,
                superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {

        final List<Setting> settings
                = Core.getInstance().getSettingsManager().getSettings();

        Cloud.getInstance().getLogger().help(String.format("%-20s - %15s", "Key", "Value"));
        Cloud.getInstance().getLogger().clearLine();

        settings.forEach(setting -> Cloud.getInstance().getLogger().help(this.format(setting)));

        return false;
    }

    private String format(final Setting setting) {
        return String.format("%-20s - %15s", setting.getKey(), setting.getValue().toString());
    }
}

package de.z1up.supercloud.core.chat;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.interfaces.Sender;
import de.z1up.supercloud.core.settings.Setting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Logger {

    private final char colorCode                    = '§';
    private final Map<String, String> COLOR_CODES   = new HashMap();

    private boolean ccsActive                       = true;
    private boolean debuggerActive                  = false;

    private final Calendar calendar;
    private final SimpleDateFormat sdf;

    private LogWriter writer;

    public Logger() {

        this.calendar   = Calendar.getInstance();
        this.sdf        = new SimpleDateFormat("HH:mm:ss");

        initColorCodes();
        updateCCsActive();
        initLogWriter();
    }

    public void clearLine() {
        System.out.println(" ");
    }

    private String addPreLog(String message, Sender sender) {

        message = "§7[§e" + getTime() + "§7] " + (sender == null ? "" : sender.getDisplay()) + "§7 " + message;
        return message;
    }

    private String addPreDebug(String message, Sender sender) {

        message = "§7[§e" + getTime() + "§7] " + (sender == null ? "" : sender.getDisplay()) + " §7[§cDEBUG§7] " + message;
        return message;
    }

    private String addPreHelp(String message) {

        message = "    §8■ §7" + message;
        return message;
    }

    public String getTime() {
        return sdf.format(calendar.getTime());
    }

    public void help(String help) {

        help = addPreHelp(help);

        if(ccsActive) {
            help = translateColorCodes(help);
        } else {
            help = getRaw(help);
        }

        System.out.println(help);
    }

    public void debug(String debug) {

        this.debug(debug, null);

    }

    public void debug(String debug, Sender sender) {

        debug = addPreDebug(debug, sender);

        if(!debuggerActive) {
            return;
        }

        writer.addLine(getRaw(debug));

        if(ccsActive) {
            debug = translateColorCodes(debug);
        } else {
            debug = getRaw(debug);
        }

        System.out.println(debug);

    }

    public void log(String log) {
        this.log(log, Cloud.getInstance());
    }

    public void log(String log, Sender sender) {

        log = addPreLog(log, sender);

        writer.addLine(getRaw(log));

        if(ccsActive) {
            log = translateColorCodes(log);
        } else {
            log = getRaw(log);
        }

        System.out.println(log);

    }

    public String translateAlternateColorCodes(char colorCode, String message) {
        for(String key : COLOR_CODES.keySet()) {
            String chatColor = COLOR_CODES.get(key);
            if(ccsActive) {
                message = message.replaceAll(colorCode + key, ChatColor.RESET + chatColor);
            } else {
                message = message.replaceAll(colorCode + key, "");
            }
        }

        return message;
    }

    public String translateColorCodes(String message) {

        message = translateAlternateColorCodes(this.colorCode, message);
        return message;

    }

    public String getRaw(String message) {

        for(String key : COLOR_CODES.keySet()) {
            message = message.replaceAll(this.colorCode + key, "");
        }

        return message;
    }

    void initColorCodes() {

        if(COLOR_CODES == null) {
            return;
        }

        if(!COLOR_CODES.isEmpty()) {
            return;
        }

        if(!ccsActive) {
            return;
        }

        COLOR_CODES.put("0", ChatColor.BLACK);
        COLOR_CODES.put("1", ChatColor.DARK_BLUE);
        COLOR_CODES.put("2", ChatColor.DARK_GREEN);
        COLOR_CODES.put("3", ChatColor.DARK_AQUA);
        COLOR_CODES.put("4", ChatColor.DARK_RED);
        COLOR_CODES.put("5", ChatColor.DARK_PURPLE);
        COLOR_CODES.put("6", ChatColor.GOLD);
        COLOR_CODES.put("7", ChatColor.GRAY);
        COLOR_CODES.put("8", ChatColor.DARK_GRAY);
        COLOR_CODES.put("9", ChatColor.BLUE);
        COLOR_CODES.put("a", ChatColor.GREEN);
        COLOR_CODES.put("b", ChatColor.AQUA);
        COLOR_CODES.put("c", ChatColor.RED);
        COLOR_CODES.put("d", ChatColor.LIGHT_PURPLE);
        COLOR_CODES.put("e", ChatColor.YELLOW);
        COLOR_CODES.put("f", ChatColor.WHITE);
    }

    void updateCCsActive() {
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            ccsActive   = false;
        }
    }

    void updateDebuggerActive() {
        this.debuggerActive     = Setting.getBool("debugging");
    }

    public void updateValues() {
        this.updateDebuggerActive();
    }

    void initLogWriter() {
        this.writer = new LogWriter();
    }

    public LogWriter getWriter() {
        return writer;
    }

    public boolean isDebugActive() {
        return !debuggerActive;
    }

}

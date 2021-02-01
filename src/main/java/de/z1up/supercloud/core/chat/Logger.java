package de.z1up.supercloud.core.chat;

import java.util.HashMap;
import java.util.Map;

public class Logger ***REMOVED***

    private final String PREFIX = "§8[§bSuperCloud§8]" + "§7 ";
    private final char colorCode = '§';
    private final Map<String, String> COLOR_CODES = new HashMap();

    private boolean ccsActive = true;
    private boolean debuggerActive = false;

    private LogWriter writer;

    public Logger() ***REMOVED***
        initColorCodes();
        updateCCsActive();
        initLogWriter();
        updateDebuggerActive();
    ***REMOVED***

    public void debug(String debug) ***REMOVED***

        debug = PREFIX + debug;

        if(!debuggerActive) ***REMOVED***
            return;
        ***REMOVED***

        writer.addLine(getRaw(debug));

        if(ccsActive) ***REMOVED***
            debug = translateColorCodes(debug);
        ***REMOVED***

        System.out.println(debug);

    ***REMOVED***

    public void log(String log) ***REMOVED***

        log = PREFIX + log;

        writer.addLine(getRaw(log));

        if(ccsActive) ***REMOVED***
            log = translateColorCodes(log);
        ***REMOVED*** else ***REMOVED***
            log = getRaw(log);
        ***REMOVED***
        System.out.println(log);

    ***REMOVED***

    public String translateAlternateColorCodes(char colorCode, String message) ***REMOVED***
        for(String key : COLOR_CODES.keySet()) ***REMOVED***
            String chatColor = COLOR_CODES.get(key);
            if(ccsActive) ***REMOVED***
                message = message.replaceAll(colorCode + key, ChatColor.RESET + chatColor);
            ***REMOVED*** else ***REMOVED***
                message = message.replaceAll(colorCode + key, "");
            ***REMOVED***
        ***REMOVED***

        return message;
    ***REMOVED***

    public String translateColorCodes(String message) ***REMOVED***

        message = translateAlternateColorCodes(this.colorCode, message);
        return message;

    ***REMOVED***

    public String getRaw(String message) ***REMOVED***

        for(String key : COLOR_CODES.keySet()) ***REMOVED***
            message = message.replaceAll(this.colorCode + key, "");
        ***REMOVED***

        return message;
    ***REMOVED***

    void initColorCodes() ***REMOVED***

        if(COLOR_CODES == null) ***REMOVED***
            return;
        ***REMOVED***

        if(!COLOR_CODES.isEmpty()) ***REMOVED***
            return;
        ***REMOVED***

        if(!ccsActive) ***REMOVED***
            return;
        ***REMOVED***

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
    ***REMOVED***

    void updateCCsActive() ***REMOVED***
        if(System.getProperty("os.name").toLowerCase().contains("win")) ***REMOVED***
            ccsActive = false;
        ***REMOVED***
    ***REMOVED***

    void updateDebuggerActive() ***REMOVED***
        debuggerActive = true;
        // ...
    ***REMOVED***

    void initLogWriter() ***REMOVED***
        this.writer = new LogWriter();
    ***REMOVED***

    public LogWriter getWriter() ***REMOVED***
        return writer;
    ***REMOVED***
***REMOVED***

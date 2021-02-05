package de.z1up.supercloud.core.chat;

import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.StringGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogWriter extends StringGenerator ***REMOVED***

    private final String PATH           = "logs";
    private final String SDF            = "HH-mm-ss";

    private CloudFile cloudFile;
    private CloudFolder cloudFolder;

    public LogWriter() ***REMOVED***
        createFiles();
    ***REMOVED***

    private void createFiles() ***REMOVED***

        createFolder();
        createFile();

    ***REMOVED***

    private void createFolder() ***REMOVED***
        cloudFolder = new CloudFolder(PATH);
    ***REMOVED***

    private void createFile() ***REMOVED***
        String time = getTime();
        String rnd = generateRandomTag(3);
        cloudFile = new CloudFile(cloudFolder, time + "-" + rnd + ".log");
    ***REMOVED***

    public void write(String text) ***REMOVED***
        addLine("[" + getTime() + "] " + text);
    ***REMOVED***

    public String getTime() ***REMOVED***
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(SDF);
        return sdf.format(calendar.getTime());
    ***REMOVED***

    public void addLine(String text) ***REMOVED***
        try ***REMOVED***
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(cloudFile.get(), true));
            bw.write(text);
            bw.newLine();
            bw.close();
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***
    ***REMOVED***

***REMOVED***

package de.z1up.supercloud.core.chat;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.StringGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogWriter extends StringGenerator {

    private final String PATH           = "logs";
    private final String SDF            = "yyyy-MM-dd_HH-mm-ss";

    private CloudFile cloudFile;
    private CloudFolder cloudFolder;

    public String getFileName() {
        return this.cloudFile.getFileName();
    }

    public LogWriter() {
        createFiles();
    }

    private void createFiles() {

        createFolder();
        createFile();

    }

    private void createFolder() {
        cloudFolder = new CloudFolder(PATH);
    }

    private void createFile() {
        String time = getTime();
        String rnd = generateRandomTag(3);
        cloudFile = new CloudFile(cloudFolder, time + "-" + rnd + ".log");
    }

    public void write(String text) {
        addLine("[" + getTime() + "] " + text);
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(SDF);
        return sdf.format(calendar.getTime());
    }

    public void addLine(String text) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(cloudFile.get(), true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}

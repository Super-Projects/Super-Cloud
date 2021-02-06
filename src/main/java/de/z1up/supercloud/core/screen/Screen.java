package de.z1up.supercloud.core.screen;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.interfaces.Screenable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Screen implements Screenable {

    private final InputStream in;

    private String sender;
    private boolean active;

    public Screen(InputStream in, String sender, boolean active) {
        this.in = in;
        this.sender = sender;
        this.active = active;
    }

    @Override
    public boolean isScreeningActive() {
        return this.active;
    }

    @Override
    public void enableScreening() {
        this.active = true;
    }

    @Override
    public void disableScreening() {
        this.active = false;
    }

    public String getSender() {
        return sender;
    }

    public void open() {

        final InputStreamReader reader
                = new InputStreamReader(this.in);

        final BufferedReader bufferedReader
                = new BufferedReader(reader);

        String line = "";

        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            if(this.isScreeningActive()) {

                String output
                        = "[" + this.getSender() + "] " + line;

                Cloud.getInstance().getLogger().log(output);

            }

        }

    }

    public void close() {

        this.disableScreening();
        Cloud.getInstance().getLogger().log("[" + this.sender + "] §cScreen closed!");

    }

}

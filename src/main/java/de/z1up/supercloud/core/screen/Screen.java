package de.z1up.supercloud.core.screen;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.interfaces.Screenable;
import de.z1up.supercloud.core.interfaces.Sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Screen implements Screenable {

    private final transient InputStream   in;

    private final Sender        sender;
    private boolean             active;

    public Screen(final InputStream in,
                  final Sender sender,
                  final boolean active) {

        this.in         = in;
        this.sender     = sender;
        this.active     = active;
    }

    @Override
    public boolean isScreeningActive() {
        return this.active;
    }

    @Override
    public void enableScreening() {
        this.active = true;
        this.open();
    }

    @Override
    public void disableScreening() {
        this.active = false;
        this.close();
    }

    @Override
    public Sender getSender() {
        return sender;
    }

    public void open() {

        if(!this.isScreeningActive()) {
            return;
        }

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

            String output
                    = "[" + this.getSender() + "] " + line;
            Cloud.getInstance().getLogger().log(output);

        }

    }

    public void close() {

        Cloud.getInstance().getLogger().log("§cScreen closed!", this.getSender());

    }

}

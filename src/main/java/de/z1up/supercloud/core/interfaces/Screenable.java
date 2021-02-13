package de.z1up.supercloud.core.interfaces;

public interface Screenable {

    boolean isScreeningActive();

    void enableScreening();

    void disableScreening();

    Sender getSender();

}

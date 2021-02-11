package de.z1up.supercloud.core.interfaces;

public interface SetUp extends Cancellable {

    void runSetUp();

    boolean isCompleted();

    void checkCompleted();

}

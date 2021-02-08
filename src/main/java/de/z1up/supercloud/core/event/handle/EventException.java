package de.z1up.supercloud.core.event.handle;

public class EventException extends Exception {

    private final Throwable cause;

    public EventException(Throwable throwable) {
        this.cause = throwable;
    }

    public EventException() {
        this.cause = null;
    }

    public EventException(Throwable cause, String message) {
        super(message);
        this.cause = cause;
    }

    public EventException(String message) {
        super(message);
        this.cause = null;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
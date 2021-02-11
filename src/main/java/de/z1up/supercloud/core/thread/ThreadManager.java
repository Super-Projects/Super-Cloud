package de.z1up.supercloud.core.thread;

import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.time.CloudThread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadManager {

    private final Collection<CloudThread> threads;

    public ThreadManager() {

        this.threads = new ArrayList<>();

    }

    public void killThread(final CloudThread e) {

        if(e.isAlive()) {

            synchronized (e) {
                e.shutdown();
            }

        }

        if(this.exists0(e.getUniqueID().getTag())) {
            this.removeThread0(e);
        }

    }

    public void removeThread(final String tag) {

        if(this.exists0(tag)) {
            this.removeThread0(this.getThread0(tag));
        }

    }

    public void removeThread(final CloudThread e) {

        if(this.exists0(e.getUniqueID().getTag())) {
            this.removeThread0(e);
        }

    }

    private void removeThread0(final CloudThread e) {
        this.threads.remove(e);
    }

    public boolean exists(final UID uid) {
        return this.exists0(uid.getTag());
    }

    public boolean exists(final String tag) {
        return this.exists0(tag);
    }

    private boolean exists0(final String tag) {

        final AtomicBoolean value
                = new AtomicBoolean(false);

        this.threads.forEach(thread -> {

            if(thread.getUniqueID().getTag().equals(tag)) {
                value.set(true);
            }

        });

        return value.get();
    }

    public void createThread(final CloudThread e) {
        this.createThread0(e);
    }

    public CloudThread createThread() {
        CloudThread thread = new CloudThread() {
            @Override
            public void run() {
            }
        };

        this.createThread0(thread);

        return thread;
    }

    private void createThread0(final CloudThread e) {
        this.threads
                .add(e);
    }

    public Collection<CloudThread> getThreads() {
        return this.threads;
    }

    public CloudThread getThread(final UID uid) {
        return this.getThread0(uid.getTag());
    }

    public CloudThread getThread(final String tag) {
        return this.getThread0(tag);
    }

    private CloudThread getThread0(final String tag) {

        AtomicReference<CloudThread> actual = null;

        this.threads.forEach(thread -> {

            if(thread.getUniqueID().getTag().equals(tag)) {
                actual.set(thread);
            }

        });

        return actual.get();
    }

}

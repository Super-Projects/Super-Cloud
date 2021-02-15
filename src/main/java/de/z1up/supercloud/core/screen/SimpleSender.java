package de.z1up.supercloud.core.screen;

import de.z1up.supercloud.core.interfaces.Sender;

public class SimpleSender implements Sender {

    private final String                name;
    private final String                display;
    private final transient Class       declaringClass;

    public SimpleSender(final String name, final Class declaringClass) {
        this(name, "§8[§b" + name + "§8]", declaringClass);
    }

    public SimpleSender(final String name,
                        String display,
                        final Class declaringClass) {

        this.name               = name;
        this.display            = display;
        this.declaringClass     = declaringClass;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class getDeclaringClass() {
        return this.declaringClass;
    }

    @Override
    public String getDisplay() {
        return this.display;
    }

}

package de.z1up.supercloud.core.input;

import de.z1up.supercloud.cloud.Cloud;

import java.util.Scanner;

public class InputReader ***REMOVED***

    private Scanner scanner;
    private String prefix;

    public InputReader() ***REMOVED***
        updatePrefix();
    ***REMOVED***

    public void open() ***REMOVED***
        this.scanner = new Scanner(System.in);
    ***REMOVED***

    public final String getInput() ***REMOVED***

        System.out.print(prefix + " ");

        final String input = this.scanner.nextLine();

        Cloud.getInstance().getLogger().getWriter().addLine(prefix + " " + input);

        return input;
    ***REMOVED***

    public void close() ***REMOVED***
        this.scanner.close();
    ***REMOVED***

    void updatePrefix() ***REMOVED***
        String username = System.getProperty("user.name");
        this.prefix = username + "@Cloud $";
    ***REMOVED***

***REMOVED***

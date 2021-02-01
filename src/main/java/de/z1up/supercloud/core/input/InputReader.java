package de.z1up.supercloud.core.input;

import de.z1up.supercloud.cloud.Cloud;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner;
    private String prefix;

    public InputReader() {
        updatePrefix();
    }

    public void open() {
        this.scanner = new Scanner(System.in);
    }

    public final String getInput() {

        System.out.print(prefix + " ");

        final String input = this.scanner.nextLine();

        Cloud.getInstance().getLogger().getWriter().addLine(prefix + " " + input);

        return input;
    }

    public void close() {
        this.scanner.close();
    }

    void updatePrefix() {
        String username = System.getProperty("user.name");
        this.prefix = username + "@Cloud $";
    }

}

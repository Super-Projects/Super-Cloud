package de.z1up.supercloud.core.input;

import de.z1up.supercloud.cloud.Cloud;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner;
    private String prefix;

    public InputReader() {
        this.updatePrefix();
    }

    public void open() {
        if(this.scanner == null)
            this.scanner = new Scanner(System.in);
    }

    public final String getInput() {

        if(this.scanner == null) {
            this.open();
        }

        System.out.print(prefix + " ");

        while (this.scanner.hasNextLine()) {
            final String input = this.scanner.nextLine();

            Cloud.getInstance().getLogger().getWriter().addLine(prefix + " " + input);

            return input;
        }
        return "none";
    }

    public void close() {
        if(this.scanner != null)
            this.scanner.close();
    }

    void updatePrefix() {
        String username = System.getProperty("user.name");
        this.prefix = username + "@Cloud $";
    }

}

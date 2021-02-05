package de.z1up.supercloud.core.id;

import java.security.SecureRandom;

public class StringGenerator {

    private final static String VALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789123456789#$!";

    protected static String generateRandomTag(int length) {

        SecureRandom random = new SecureRandom();
        int val_length = VALS.length();

        String tag = "";

        for(int i = 0; i < length; i++) {

            tag = tag + VALS.charAt(random.nextInt(val_length));

        }

        return tag;
    }

}

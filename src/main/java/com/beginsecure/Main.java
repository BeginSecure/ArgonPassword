package com.beginsecure;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.err.println("send in a password to match");
            System.exit(1);
        }

        Argon2 argon2 = Argon2Factory.create();

        char[] password = readPasswordFromUser();

        try {
            String hash = argon2.hash(10, 65536, 1, args[0]);

            if(argon2.verify(hash, password)) {
                System.out.println("match!!");
            } else {
                System.out.println("no match!!");
            }
        } finally {
            argon2.wipeArray(password);
        }
    }

    public static char[] readPasswordFromUser() {
        Console console = System.console();
        if (console != null) {
            return console.readPassword("Enter your password: ");
        } else {
            // Fallback method when Console is not available
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your password: ");
            try {
                String password = reader.readLine();
                return password.toCharArray();
            } catch (IOException e) {
                e.printStackTrace();
                return new char[]{};
            }
        }
    }
}

package com.marakana.sforums.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoderMain {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: <secret> <raw-password> [encoded-password]");
        } else {
            String secret = args[0];
            String rawPassword = args[1];
            String encodedPassword = args.length >= 3 ? args[2] : null;

            PasswordEncoder passwordEncoder = new StandardPasswordEncoder(secret);

            if (encodedPassword != null) {
                System.out.println(passwordEncoder.matches(rawPassword, encodedPassword));
            } else {
                System.out.println(passwordEncoder.encode(rawPassword));
            }
        }
    }
}

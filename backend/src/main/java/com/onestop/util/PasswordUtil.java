package com.onestop.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

private static final int ITERATIONS = 600000;
private static final int KEY_LENGTH = 256;
private static final int SALT_LENGTH = 16;

private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

private static final SecureRandom RANDOM = new SecureRandom();

// Generate a secure password hash
public static String hashPassword(String password) {

    if (password == null || password.isEmpty()) {
        throw new IllegalArgumentException("Password cannot be empty.");
    }

    byte[] salt = new byte[SALT_LENGTH];
    RANDOM.nextBytes(salt);

    PBEKeySpec spec = new PBEKeySpec(
            password.toCharArray(),
            salt,
            ITERATIONS,
            KEY_LENGTH
    );

    try {
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance(ALGORITHM);

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return ITERATIONS + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);

    } catch (Exception e) {

        throw new RuntimeException("Password hashing failed.", e);

    } finally {

        spec.clearPassword();
    }
}

// Verify the entered password
public static boolean verifyPassword(
        String password, String storedHash) {

    if (password == null || storedHash == null) {
        return false;
    }

    try {
        String[] parts = storedHash.split(":");

        if (parts.length != 3) {
            return false;
        }

        int iterations = Integer.parseInt(parts[0]);

        if (iterations <= 0 || iterations > 1000000) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

        if (salt.length != SALT_LENGTH || expectedHash.length != KEY_LENGTH / 8) {
            return false;
        }

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                iterations,
                KEY_LENGTH
        );

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(ALGORITHM);

            byte[] actualHash = factory.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(
                    expectedHash,
                    actualHash
            );

        } finally {

            spec.clearPassword();
        }

    } catch (Exception e) {

        return false;
    }
}
}

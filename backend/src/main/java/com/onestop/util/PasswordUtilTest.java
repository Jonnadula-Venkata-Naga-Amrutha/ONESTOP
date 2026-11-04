package com.onestop.util;

public class PasswordUtilTest {

    public static void main(String[] args) {

        String password = "Test@12345";

        // Generate password hash
        String hashedPassword = PasswordUtil.hashPassword(password);

        System.out.println("Password Hash Generated Successfully!");

        // Verify correct password
        boolean correctPassword =
                PasswordUtil.verifyPassword(password, hashedPassword);

        // Verify incorrect password
        boolean incorrectPassword =
                PasswordUtil.verifyPassword("Wrong@123", hashedPassword);

        System.out.println("Correct Password Accepted: " + correctPassword);

        System.out.println("Wrong Password Accepted: " + incorrectPassword);

        if (correctPassword && !incorrectPassword) {
            System.out.println("PASSWORD SECURITY TEST PASSED!");
        } else {
            System.out.println("PASSWORD SECURITY TEST FAILED!");
        }
    }
}
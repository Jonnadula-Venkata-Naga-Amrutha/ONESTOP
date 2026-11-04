package com.onestop.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {

            DatabaseMetaData metadata = connection.getMetaData();

            System.out.println("========================================");
            System.out.println("ONESTOP DATABASE CONNECTION SUCCESSFUL!");
            System.out.println("========================================");

            System.out.println("Database: "
                    + metadata.getDatabaseProductName());

            System.out.println("Database Version: "
                    + metadata.getDatabaseProductVersion());

            System.out.println("Connected Database: "
                    + connection.getCatalog());

            System.out.println("========================================");

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED!");
            e.printStackTrace();
        }
    }
}
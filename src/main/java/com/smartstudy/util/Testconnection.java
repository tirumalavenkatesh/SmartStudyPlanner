package com.smartstudy.util;

import java.sql.Connection;

public class Testconnection {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Database connection failed!");
        }
    }
}
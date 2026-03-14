package com.aud.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/arabic_urdu_dictionary?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; // Update with your MySQL username
    private static final String PASSWORD = ""; // Update with your MySQL password

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection: " + e.getMessage());
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
    	 try {
    		 if (connection == null || connection.isClosed()) {
           
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
    	 }
    	 } catch (SQLException e) {
             throw new RuntimeException("Failed to re-establish database connection: " + e.getMessage());
         }
        
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Allow reinitialization
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}

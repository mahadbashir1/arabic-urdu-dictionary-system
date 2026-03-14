package com.aud.main;

import com.aud.dal.DatabaseInitializer;
import com.aud.pl.DictionaryGUI;

public class Main {
    public static void main(String[] args) {
        // Initialize the database tables if they don't exist
        try {
            DatabaseInitializer.initializeDatabase();
            System.out.println("Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            return; // Stop if DB setup fails
        }

        // Launch the Dictionary GUI (updated constructor)
        new DictionaryGUI();
    }
}

package com.aud.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            String[] sqlStatements = {

                // ========== PATTERN TABLE ==========
                "CREATE TABLE IF NOT EXISTS Pattern (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "Pattern_form VARCHAR(50) NOT NULL UNIQUE" +
                ")",

                // ========== ROOT TABLE ==========
                "CREATE TABLE IF NOT EXISTS Root (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "root_letters VARCHAR(10) NOT NULL UNIQUE" +
                ")",

                // ========== WORD TABLE ==========
                "CREATE TABLE IF NOT EXISTS Word (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "Arabic_form VARCHAR(50) NOT NULL, " +
                "Base_form VARCHAR(50), " +
                "Urdu_meaning TEXT NOT NULL, " +
                "Part_of_speech VARCHAR(20) NOT NULL, " +
                "Root_ID INT, " +
                "Pattern_ID INT, " +         // <-- NEW FOREIGN KEY
                "FOREIGN KEY (Root_ID) REFERENCES Root(ID), " +
                "FOREIGN KEY (Pattern_ID) REFERENCES Pattern(ID)" +
                ")",

                // ========== METADATA TABLE ==========
                "CREATE TABLE IF NOT EXISTS Metadata (" +
                "Word_ID INT, " +
                "Gender ENUM('Masculine', 'Feminine') DEFAULT NULL, " +
                "Number ENUM('Singular', 'Dual', 'Plural') DEFAULT NULL, " +
                "`Case` VARCHAR(20) DEFAULT NULL, " +
                "Verb_Form VARCHAR(10) DEFAULT NULL, " +
                "Tense VARCHAR(20) DEFAULT NULL, " +
                "Transitivity ENUM('Transitive', 'Intransitive') DEFAULT NULL, " +
                "FOREIGN KEY (Word_ID) REFERENCES Word(ID), " +
                "PRIMARY KEY (Word_ID)" +
                ")"
            };

            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage());
        }
    }
}

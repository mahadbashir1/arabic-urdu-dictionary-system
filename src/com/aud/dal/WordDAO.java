package com.aud.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.aud.dtos.WordDTO;

public class WordDAO implements IWordDAO {
    private Connection connection;

    public WordDAO() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void addWord(WordDTO word) throws SQLException {
        String sql = "INSERT INTO Word (Arabic_form, Base_form, Urdu_meaning, Part_of_speech, Root_ID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, word.getArabicForm());
            stmt.setString(2, word.getBaseForm());
            stmt.setString(3, word.getUrduMeaning());
            stmt.setString(4, word.getPartOfSpeech());
            stmt.setInt(5, word.getRootId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    word.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public WordDTO getWordById(int id) throws SQLException {
        String sql = "SELECT * FROM Word WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    WordDTO word = new WordDTO();
                    word.setId(rs.getInt("ID"));
                    word.setArabicForm(rs.getString("Arabic_form"));
                    word.setBaseForm(rs.getString("Base_form"));
                    word.setUrduMeaning(rs.getString("Urdu_meaning"));
                    word.setPartOfSpeech(rs.getString("Part_of_speech"));
                    word.setRootId(rs.getInt("Root_ID"));
                    return word;
                }
            }
        }
        return null;
    }

    @Override
    public void updateWord(WordDTO word) throws SQLException {
        String sql = "UPDATE Word SET Arabic_form = ?, Base_form = ?, Urdu_meaning = ?, Part_of_speech = ?, Root_ID = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, word.getArabicForm());
            stmt.setString(2, word.getBaseForm());
            stmt.setString(3, word.getUrduMeaning());
            stmt.setString(4, word.getPartOfSpeech());
            stmt.setInt(5, word.getRootId());
            stmt.setInt(6, word.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteWord(int id) throws SQLException {
        String sql = "DELETE FROM Word WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<WordDTO> getAllWords() throws SQLException {
        List<WordDTO> words = new ArrayList<>();
        String sql = "SELECT * FROM Word";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                WordDTO word = new WordDTO();
                word.setId(rs.getInt("ID"));
                word.setArabicForm(rs.getString("Arabic_form"));
                word.setBaseForm(rs.getString("Base_form"));
                word.setUrduMeaning(rs.getString("Urdu_meaning"));
                word.setPartOfSpeech(rs.getString("Part_of_speech"));
                word.setRootId(rs.getInt("Root_ID"));
                words.add(word);
            }
        }
        return words;
    }
}

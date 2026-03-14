package com.aud.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.aud.dtos.MetadataDTO;

public class MetadataDAO implements IMetadataDAO {
    private Connection connection;

    public MetadataDAO() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void addMetadata(MetadataDTO metadata) throws SQLException {
        String sql = "INSERT INTO Metadata (Word_ID, Gender, Number, `Case`, Verb_Form, Tense, Transitivity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, metadata.getWordId());
            stmt.setString(2, metadata.getGender());
            stmt.setString(3, metadata.getNumber());
            stmt.setString(4, metadata.getCaseType());
            stmt.setString(5, metadata.getVerbForm());
            stmt.setString(6, metadata.getTense());
            stmt.setString(7, metadata.getTransitivity());
            stmt.executeUpdate();
        }
    }

    @Override
    public MetadataDTO getMetadataByWordId(int wordId) throws SQLException {
        String sql = "SELECT * FROM Metadata WHERE Word_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, wordId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MetadataDTO metadata = new MetadataDTO();
                    metadata.setWordId(rs.getInt("Word_ID"));
                    metadata.setGender(rs.getString("Gender"));
                    metadata.setNumber(rs.getString("Number"));
                    metadata.setCaseType(rs.getString("Case"));
                    metadata.setVerbForm(rs.getString("Verb_Form"));
                    metadata.setTense(rs.getString("Tense"));
                    metadata.setTransitivity(rs.getString("Transitivity"));
                    return metadata;
                }
            }
        }
        return null;
    }

    @Override
    public void updateMetadata(MetadataDTO metadata) throws SQLException {
        String sql = "UPDATE Metadata SET Gender = ?, Number = ?, `Case` = ?, Verb_Form = ?, Tense = ?, Transitivity = ? WHERE Word_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, metadata.getGender());
            stmt.setString(2, metadata.getNumber());
            stmt.setString(3, metadata.getCaseType());
            stmt.setString(4, metadata.getVerbForm());
            stmt.setString(5, metadata.getTense());
            stmt.setString(6, metadata.getTransitivity());
            stmt.setInt(7, metadata.getWordId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMetadata(int wordId) throws SQLException {
        String sql = "DELETE FROM Metadata WHERE Word_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, wordId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<MetadataDTO> getAllMetadata() throws SQLException {
        List<MetadataDTO> metadataList = new ArrayList<>();
        String sql = "SELECT * FROM Metadata";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MetadataDTO metadata = new MetadataDTO();
                metadata.setWordId(rs.getInt("Word_ID"));
                metadata.setGender(rs.getString("Gender"));
                metadata.setNumber(rs.getString("Number"));
                metadata.setCaseType(rs.getString("Case"));
                metadata.setVerbForm(rs.getString("Verb_Form"));
                metadata.setTense(rs.getString("Tense"));
                metadata.setTransitivity(rs.getString("Transitivity"));
                metadataList.add(metadata);
            }
        }
        return metadataList;
    }
}
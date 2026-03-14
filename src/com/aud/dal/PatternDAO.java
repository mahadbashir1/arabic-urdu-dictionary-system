package com.aud.dal;

import java.sql.*;
import java.util.*;
import com.aud.dtos.PatternDTO;

public class PatternDAO implements IPatternDAO {

    private final Connection connection;

    public PatternDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertPattern(PatternDTO pattern) throws SQLException {
        String sql = "INSERT INTO Pattern (Pattern_form) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pattern.getPatternForm());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<PatternDTO> getAllPatterns() throws SQLException {
        List<PatternDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Pattern";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new PatternDTO(
                    rs.getInt("ID"),
                    rs.getString("Pattern_form")
                ));
            }
        }
        return list;
    }

    @Override
    public PatternDTO getPatternById(int id) throws SQLException {
        String sql = "SELECT * FROM Pattern WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PatternDTO(
                        rs.getInt("ID"),
                        rs.getString("Pattern_form")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public PatternDTO getPatternByForm(String form) throws SQLException {
        String sql = "SELECT * FROM Pattern WHERE Pattern_form = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, form);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PatternDTO(
                        rs.getInt("ID"),
                        rs.getString("Pattern_form")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void updatePattern(PatternDTO pattern) throws SQLException {
        String sql = "UPDATE Pattern SET Pattern_form = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pattern.getPatternForm());
            stmt.setInt(2, pattern.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePattern(int id) throws SQLException {
        String sql = "DELETE FROM Pattern WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

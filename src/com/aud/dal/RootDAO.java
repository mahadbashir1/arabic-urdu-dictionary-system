package com.aud.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.aud.dtos.RootDTO;

public class RootDAO implements IRootDAO {
    private Connection connection;

    public RootDAO() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void addRoot(RootDTO root) throws SQLException {
        String sql = "INSERT INTO Root (root_letters) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, root.getRootLetters());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    root.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public RootDTO getRootById(int id) throws SQLException {
        String sql = "SELECT * FROM Root WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RootDTO root = new RootDTO();
                    root.setId(rs.getInt("ID"));
                    root.setRootLetters(rs.getString("root_letters"));
                    return root;
                }
            }
        }
        return null;
    }

    @Override
    public void updateRoot(RootDTO root) throws SQLException {
        String sql = "UPDATE Root SET root_letters = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, root.getRootLetters());
            stmt.setInt(2, root.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteRoot(int id) throws SQLException {
        String sql = "DELETE FROM Root WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<RootDTO> getAllRoots() throws SQLException {
        List<RootDTO> roots = new ArrayList<>();
        String sql = "SELECT * FROM Root";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RootDTO root = new RootDTO();
                root.setId(rs.getInt("ID"));
                root.setRootLetters(rs.getString("root_letters"));
                roots.add(root);
            }
        }
        return roots;
    }
}

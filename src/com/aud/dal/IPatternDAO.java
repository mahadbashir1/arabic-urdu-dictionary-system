package com.aud.dal;

import java.sql.SQLException;
import java.util.List;
import com.aud.dtos.PatternDTO;

public interface IPatternDAO {
    void insertPattern(PatternDTO pattern) throws SQLException;
    List<PatternDTO> getAllPatterns() throws SQLException;
    PatternDTO getPatternById(int id) throws SQLException;
    PatternDTO getPatternByForm(String form) throws SQLException;
    void updatePattern(PatternDTO pattern) throws SQLException;
    void deletePattern(int id) throws SQLException;
}

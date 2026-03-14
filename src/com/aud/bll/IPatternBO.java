package com.aud.bll;

import java.util.List;
import com.aud.dtos.PatternDTO;

public interface IPatternBO {
    void addPattern(PatternDTO pattern) throws Exception;
    List<PatternDTO> getAllPatterns() throws Exception;
    PatternDTO getPatternById(int id) throws Exception;
    void updatePattern(PatternDTO pattern) throws Exception;
    void deletePattern(int id) throws Exception;
}

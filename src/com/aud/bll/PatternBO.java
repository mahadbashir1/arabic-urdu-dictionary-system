package com.aud.bll;

import java.util.List;
import com.aud.dal.IPatternDAO;
import com.aud.dtos.PatternDTO;

public class PatternBO implements IPatternBO {

    private final IPatternDAO patternDAO;

    public PatternBO(IPatternDAO patternDAO) {
        this.patternDAO = patternDAO;
    }

    @Override
    public void addPattern(PatternDTO pattern) throws Exception {
        patternDAO.insertPattern(pattern);
    }

    @Override
    public List<PatternDTO> getAllPatterns() throws Exception {
        return patternDAO.getAllPatterns();
    }

    @Override
    public PatternDTO getPatternById(int id) throws Exception {
        return patternDAO.getPatternById(id);
    }

    @Override
    public void updatePattern(PatternDTO pattern) throws Exception {
        patternDAO.updatePattern(pattern);
    }

    @Override
    public void deletePattern(int id) throws Exception {
        patternDAO.deletePattern(id);
    }
}

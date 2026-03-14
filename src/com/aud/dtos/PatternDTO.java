package com.aud.dtos;

public class PatternDTO {
    private int id;
    private String patternForm;

    public PatternDTO() {}

    public PatternDTO(int id, String patternForm) {
        this.id = id;
        this.patternForm = patternForm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatternForm() {
        return patternForm;
    }

    public void setPatternForm(String patternForm) {
        this.patternForm = patternForm;
    }

    @Override
    public String toString() {
        return patternForm;
    }
}
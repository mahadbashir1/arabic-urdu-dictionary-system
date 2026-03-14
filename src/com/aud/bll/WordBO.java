package com.aud.bll;

import java.util.List;
import com.aud.dal.IWordDAO;
import com.aud.dal.WordDAO;
import com.aud.dtos.WordDTO;
import com.aud.facade.DAOFactory;

public class WordBO implements IWordBO {
    private IWordDAO wordDAO;

    public WordBO(IWordDAO wordDAO) {
        this.wordDAO =wordDAO;
    }

    @Override
    public void addWord(WordDTO word) throws Exception {
        if (word == null || word.getArabicForm() == null || word.getArabicForm().trim().isEmpty() ||
            word.getUrduMeaning() == null || word.getUrduMeaning().trim().isEmpty() ||
            word.getPartOfSpeech() == null || word.getPartOfSpeech().trim().isEmpty() || word.getRootId() <= 0) {
            throw new IllegalArgumentException("All required fields are required and rootId must be positive");
        }
        wordDAO.addWord(word);
    }

    @Override
    public WordDTO getWordById(int id) throws Exception {
        return wordDAO.getWordById(id);
    }

    @Override
    public void updateWord(WordDTO word) throws Exception {
        if (word == null || word.getId() <= 0) {
            throw new IllegalArgumentException("Invalid word data");
        }
        wordDAO.updateWord(word);
    }

    @Override
    public void deleteWord(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        wordDAO.deleteWord(id);
    }

    @Override
    public List<WordDTO> getAllWords() throws Exception {
        return wordDAO.getAllWords();
    }
}

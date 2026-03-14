package com.aud.dal;

import java.util.List;
import com.aud.dtos.WordDTO;

public interface IWordDAO {
    void addWord(WordDTO word) throws Exception;
    WordDTO getWordById(int id) throws Exception;
    void updateWord(WordDTO word) throws Exception;
    void deleteWord(int id) throws Exception;
    List<WordDTO> getAllWords() throws Exception;
}

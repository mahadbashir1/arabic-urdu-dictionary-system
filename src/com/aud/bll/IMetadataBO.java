package com.aud.bll;

import java.util.List;
import com.aud.dtos.MetadataDTO;

public interface IMetadataBO {
    void addMetadata(MetadataDTO metadata) throws Exception;
    MetadataDTO getMetadataByWordId(int wordId) throws Exception;
    void updateMetadata(MetadataDTO metadata) throws Exception;
    void deleteMetadata(int wordId) throws Exception;
    List<MetadataDTO> getAllMetadata() throws Exception;
}
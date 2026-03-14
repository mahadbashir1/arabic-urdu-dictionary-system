package com.aud.bll;

import java.util.List;
import com.aud.dal.IMetadataDAO;
import com.aud.dal.MetadataDAO;
import com.aud.dtos.MetadataDTO;
import com.aud.facade.DAOFactory;

public class MetadataBO implements IMetadataBO {
    private IMetadataDAO metadataDAO;

    public MetadataBO(IMetadataDAO mdao) {
        this.metadataDAO = mdao;
    }

    @Override
    public void addMetadata(MetadataDTO metadata) throws Exception {
        if (metadata == null || metadata.getWordId() <= 0) {
            throw new IllegalArgumentException("Word ID must be positive");
        }
        metadataDAO.addMetadata(metadata);
    }

    @Override
    public MetadataDTO getMetadataByWordId(int wordId) throws Exception {
        return metadataDAO.getMetadataByWordId(wordId);
    }

    @Override
    public void updateMetadata(MetadataDTO metadata) throws Exception {
        if (metadata == null || metadata.getWordId() <= 0) {
            throw new IllegalArgumentException("Invalid metadata data");
        }
        metadataDAO.updateMetadata(metadata);
    }

    @Override
    public void deleteMetadata(int wordId) throws Exception {
        if (wordId <= 0) {
            throw new IllegalArgumentException("Invalid Word ID");
        }
        metadataDAO.deleteMetadata(wordId);
    }

    @Override
    public List<MetadataDTO> getAllMetadata() throws Exception {
        return metadataDAO.getAllMetadata();
    }
}
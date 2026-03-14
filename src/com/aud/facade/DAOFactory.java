package com.aud.facade;

import com.aud.dal.DBConnection;
import com.aud.dal.IRootDAO;
import com.aud.dal.IWordDAO;
import com.aud.dal.IMetadataDAO;
import com.aud.dal.IPatternDAO;

import com.aud.dal.RootDAO;
import com.aud.dal.WordDAO;
import com.aud.dal.MetadataDAO;
import com.aud.dal.PatternDAO;

public class DAOFactory {

    public static IRootDAO getRootDAO() {
        return new RootDAO();
    }

    public static IWordDAO getWordDAO() {
        return new WordDAO();
    }

    public static IMetadataDAO getMetadataDAO() {
        return new MetadataDAO();
    }

    public static IPatternDAO getPatternDAO() {
        return new PatternDAO(
                DBConnection.getInstance().getConnection()
        );
    }
}

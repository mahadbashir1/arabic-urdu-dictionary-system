package com.aud.facade;

import com.aud.bll.IMetadataBO;
import com.aud.bll.IRootBO;
import com.aud.bll.IWordBO;
import com.aud.bll.IPatternBO;

import com.aud.bll.MetadataBO;
import com.aud.bll.RootBO;
import com.aud.bll.WordBO;
import com.aud.bll.PatternBO;

public class BOFactory {

    public static IRootBO getRootBO() {
        return new RootBO(DAOFactory.getRootDAO());
    }

    public static IWordBO getWordBO() {
        return new WordBO(DAOFactory.getWordDAO());
    }

    public static IMetadataBO getMetadataBO() {
        return new MetadataBO(DAOFactory.getMetadataDAO());
    }

    public static IPatternBO getPatternBO() {
        return new PatternBO(DAOFactory.getPatternDAO());
    }
}

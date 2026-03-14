module ArabicUrduDictionary {
    requires java.sql;
    requires java.desktop;

    exports com.aud.pl;
    exports com.aud.bll;
    exports com.aud.dal;
    exports com.aud.dtos;
    exports com.aud.facade;
    opens com.aud.pl to java.desktop;
    opens com.aud.bll to java.desktop;
    opens com.aud.dal to java.sql;
    opens com.aud.dtos to java.desktop;
    opens com.aud.facade to java.desktop;
}

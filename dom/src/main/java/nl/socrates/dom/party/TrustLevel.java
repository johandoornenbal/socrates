package nl.socrates.dom.party;

import nl.socrates.dom.TitledEnum;

public enum TrustLevel implements TitledEnum{
    BANNED("verbannen-niveau5"),
    OUTER_CIRCLE("buitenring-niveau4"),
    ENTRY_LEVEL("instap-niveau3"),
    INNER_CIRCLE("binnenring-niveau2"),
    INTIMATE("intimi-niveau1");

    private String title;
    
    TrustLevel (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}

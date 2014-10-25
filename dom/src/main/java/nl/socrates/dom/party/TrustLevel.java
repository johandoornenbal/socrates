package nl.socrates.dom.party;

import nl.socrates.dom.TitledEnum;

// compare to kan gebruikt worden op enum: de eerst gedeclareerde waarde heeft de laagste ordening
// dus compare(BANNED, OUTERCIRCLE) levert negatieve waarde
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

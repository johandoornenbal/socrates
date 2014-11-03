package nl.yodo.dom.Party;

import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.YodoDomainObject;

public class Referral extends YodoDomainObject<Referral> {
    
    public Referral() {
        super("");
    }
    
    private YodoPerson referrer;
    
    public YodoPerson getReferrer() {
        return referrer;
    }
    
    public void setReferrer(final YodoPerson ref) {
        this.referrer = ref;
    }
    
    private TrustLevel trustLevel;
    
    public TrustLevel getTrustLevel() {
        return trustLevel;
    }
    
    public void setTrustLevel(final TrustLevel level) {
        this.trustLevel = level;
    }
}

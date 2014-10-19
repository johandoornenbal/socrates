package nl.socrates.dom.party;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;

@Immutable
public class Referer extends AbstractDomainObject {
    
    private Person owner;
    
    @Hidden(where = Where.OBJECT_FORMS)
    @MemberOrder(sequence = "10")
    @Named("Verwijzer")
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner(final Person owner) {
        this.owner = owner;
    }
    
    private LocalDate date;
    
    @Hidden(where = Where.OBJECT_FORMS)
    @MemberOrder(sequence = "20")
    @Named("Gemaakt op")
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(final LocalDate date) {
        this.date = date;
    }
    
    private TrustLevel level;
    
    @Hidden(where = Where.OBJECT_FORMS)
    @MemberOrder(sequence = "30")
    @Named("Vertrouwensniveau")
    public TrustLevel getLevel() {
        return level;
    }
    
    public void setLevel(final TrustLevel level) {
        this.level = level;
    }

}

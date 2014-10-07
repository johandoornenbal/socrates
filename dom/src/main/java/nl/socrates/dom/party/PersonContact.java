package nl.socrates.dom.party;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;

import java.util.List;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class PersonContact extends AbstractDomainObject implements Comparable<PersonContact>{
    
    private Person owner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    @Named("Eigenaar")
    @Hidden(where = Where.PARENTED_TABLES)
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner (final Person owner) {
        this.owner = owner;
    }
    
    private Person contact;
   
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "20")
    @Named("Contact")   
    public Person getContact() {
        return contact;
    }
    
    public void setContact(final Person contact) {
        this.contact = contact;
    }
    
    public List<Person> autoCompleteContact(String search) {
        return persons.findPersons(search);
    }
    
    private TrustLevel level;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "30")
    @Named("Vertrouwens niveau")
    public TrustLevel getLevel() {
        return level;
    }
    
    public void setLevel(final TrustLevel level) {
        this.level = level;
    }
    
    public TrustLevel defaultLevel() {
        return TrustLevel.ENTRY_LEVEL;
    }

    @Override
    public int compareTo(PersonContact o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .compare(this.getContact(), o.getContact())
                .compare(this.getLevel(), o.getLevel())
                .result();
    }
    
    @Inject
    Persons persons;

}

package nl.socrates.dom.party;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.EventBusService;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.person.PersonContact "
                    + "WHERE owner == :owner"),
    @javax.jdo.annotations.Query(
            name = "findReferringContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.person.PersonContact "
                    + "WHERE contact == :contact")                   
    })
public class PersonContact extends AbstractDomainObject implements Comparable<PersonContact> {
    
    private Person owner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "50")
    @Named("Eigenaar")
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner (final Person owner) {
        this.owner = owner;
    }
    
    private Person contact;
   
    @Title
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    @Named("Contact")
    @Disabled
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
    
    private LocalDateTime createdOn;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Persistent
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime created) {
        this.createdOn = created;
    }
    
    @MemberOrder(sequence = "20")
    @Named("gemaakt op")
    public LocalDate getDateCreatedOn() {
        return getCreatedOn().toLocalDate();
    }
    
    @Programmatic
    public void publishTestEvent() {
        eventBusService.post(new PersonContactEvent("Notificatie opgenomen bij ", this.getOwner(), this.getContact()));
    }
    
    @Named("Verwijderen?")
    public List<PersonContact> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Contact verwijderd");
        
        QueryDefault<PersonContact> query = 
                QueryDefault.create(
                        PersonContact.class, 
                    "findPersonContact", 
                    "owner", getOwner());
        
        return (List<PersonContact>) container.allMatches(query);   
    }
    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }

    
    @Override
    public int compareTo(PersonContact o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .compare(this.getContact(), o.getContact())
                .compare(this.getCreatedOn(), o.getCreatedOn())
                .compare(this.getLevel(), o.getLevel())
                .result();
    }
    
    @Inject
    Persons persons;
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    private EventBusService eventBusService;
    public void injectEventBusService(final EventBusService eventBusService) {
        this.eventBusService = eventBusService;
        eventBusService.register(this);
    }

}

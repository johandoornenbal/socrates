package nl.socrates.dom.party;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.services.clock.ClockService;

import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "30", repositoryFor = PersonContact.class)
@Named("Persoonlijke contacten")
@AutoComplete(repository=Persons.class,  action="findPersons")
public class PersonContacts extends SocratesDomainService<PersonContact>{
    
    public PersonContacts() {
        super(PersonContacts.class, PersonContact.class);
    }
    
    @MemberOrder(name = "PersonContacts", sequence = "1")
    @Named("Voeg contact toe")
    @NotInServiceMenu
    public PersonContact createContact(
        final Person owner,
        @Named("Contact") final Person contact
        ) {
        final PersonContact pc = container.newTransientInstance(PersonContact.class);
        pc.setOwner(owner);
        pc.setContact(contact);
        pc.setCreatedOn(clockService.nowAsLocalDateTime());
        container.persistIfNotAlready(pc);
        pc.publishTestEvent();
        return pc;
    }
    
    public List<Person> autoComplete1CreateContact(final String search) {
        return persons.findPersons(search);
    }
    
    @Named("Alle persoonlijke contacten")
    @NotInServiceMenu
    public List<PersonContact> listAll() {
        return container.allInstances(PersonContact.class);
    }
    
    @javax.inject.Inject 
    DomainObjectContainer container;
    
    @Inject
    Persons persons;
    
    @Inject
    private ClockService clockService;
    
    
}

package nl.socrates.dom.party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;

import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "30", repositoryFor = PersonContact.class)
@Named("Persoonlijke contacten")
//@AutoComplete(repository=Persons.class,  action="findPersons")
public class PersonContacts extends SocratesDomainService<PersonContact>{
    
    public PersonContacts() {
        super(PersonContacts.class, PersonContact.class);
    }
    
    @MemberOrder(name = "PersonContacts", sequence = "1")
    @Named("Voeg contact toe")
    @NotInServiceMenu
    public PersonContact createContact(
        final Person owner,
        @Named("Contact") final Person contact,
        @Named("Level") final Integer level
        ) {
        final PersonContact pc = container.newTransientInstance(PersonContact.class);
        pc.setOwner(owner);
        pc.setContact(contact);
        pc.setLevel(level);
        container.persistIfNotAlready(pc);
        return pc;
    }
    
    public List<Person> autoComplete1CreateContact(String search) {
        return Persons.findPersons(search);
    }
    
    @Named("Alle persoonlijke contacten")
    @NotInServiceMenu
    public List<PersonContact> listAll() {
        return container.allInstances(PersonContact.class);
    }
    
    @javax.inject.Inject 
    DomainObjectContainer container;
    
}

package nl.yodo.dom.Party;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;

import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "13", repositoryFor = YodoPersonalContact.class)
@Named("Pruts Personal Contacts")
public class YodoPersonalContacts extends YodoDomainService<YodoPersonalContact> {
    
    public YodoPersonalContacts() {
        super(YodoPersonalContacts.class, YodoPersonalContact.class);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "20")
    public List<YodoPersonalContact> allYourContacts(){
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContact", 
                    "owner", container.getUser().getName());
        return allMatches(query);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "10")
    @ActionSemantics(Of.NON_IDEMPOTENT)
    @Named("Personal contact maken")
    public YodoPersonalContact newPersonalContact(
            final @Named("Contact") YodoPerson contactPerson) {
        final YodoPersonalContact contact = newTransientInstance(YodoPersonalContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwner());
        contact.setOwner(container.getUser().getName());
        persist(contact);
        return contact;
    }
    
    public List<YodoPerson> autoComplete0NewPersonalContact(final String search) {
        return yodopersons.findYodoPersons(search);
    }
    
    public boolean hideNewPersonalContact(final @Named("Contact") YodoPerson contactPerson){
        // show in service menu
        if (contactPerson == null) {
            return false;
        }
        // do not show on own personinstance - you cannot add yourself as personal contact
        if (contactPerson.getOwner().equals(container.getUser().getName())) {
            return true;
        }
        // do not show when already contacted
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContactUniqueContact", 
                    "owner", container.getUser().getName(),
                    "contact", contactPerson.getOwner());
        return container.firstMatch(query) != null?
        true  : false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPersonalContact(final YodoPerson contactPerson) {
        
        if (Objects.equal(contactPerson.getOwner(), container.getUser().getName())) {
            return "Contact maken met jezelf heeft geen zin.";
        }
        
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContactUniqueContact", 
                    "owner", container.getUser().getName(),
                    "contact", contactPerson.getOwner());
        return container.firstMatch(query) != null?
        "Dit contact heb je al gemaakt. Pas het eventueel aan in plaats van een nieuwe te maken."        
        :null;
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;
     
    @Inject
    YodoPersons yodopersons;
}

package nl.yodo.dom.Party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.query.QueryDefault;

import nl.socrates.dom.utils.StringUtils;
import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "13", repositoryFor = YodoPerson.class)
@Named("Pruts Personen")
public class YodoPersons extends YodoDomainService<YodoPerson> {
    
    public YodoPersons() {
        super(YodoPersons.class, YodoPerson.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public YodoPerson newPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Optional @Named("Voornaam") String firstName,
            final @Named("Achternaam") String lastName) {
        final YodoPerson person = newTransientInstance(YodoPerson.class);
        person.setUniquePartyId(uniquePartyId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setOwner(container.getUser().getName());
        persist(person);
        return person;
    }
    
    public boolean hideNewPerson() {
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "owner", container.getUser().getName());        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Optional @Named("Voornaam") String firstName,
            final @Named("Achternaam") String lastName) {
        
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "owner", container.getUser().getName());        
        return container.firstMatch(query) != null?
        "Je hebt jezelf al aangemaakt. Pas je gegevens eventueel aan in plaats van hier een nieuwe te maken."        
        :null;
        
    }
    
    public List<YodoPerson> allPersons() {
        return allInstances();
    }
    
    public List<YodoPerson> findYodoPersons(final String lastname) {
        return allMatches("matchPersonByLastName", "lastName", StringUtils.wildcardToCaseInsensitiveRegex(lastname));
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;
}

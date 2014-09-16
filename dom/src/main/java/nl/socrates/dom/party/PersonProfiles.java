package nl.socrates.dom.party;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.value.Blob;

@DomainService(repositoryFor=PersonProfile.class)
public class PersonProfiles extends AbstractFactoryAndRepository {
    
    @Named("Alle profielen")
    public List<PersonProfile> listAll() {
        return container.allInstances(PersonProfile.class);
    }
    
    @MemberOrder(name = "PersonProfiles", sequence = "1")
    @Named("Voeg profiel toe")
    @NotInServiceMenu
    public PersonProfile createProfile(
        @Named("Porfiel naam") final String profilename,
        final Person person,
        @Optional @Named("Foto") Blob picture ) {
        final PersonProfile pf = container.newTransientInstance(PersonProfile.class);
        pf.setProfilename(profilename);
        pf.setPerson(person);
        pf.setPicture(picture);
        container.persistIfNotAlready(pf);
        return pf;
    }

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}

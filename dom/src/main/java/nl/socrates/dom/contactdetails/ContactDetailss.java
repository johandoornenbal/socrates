package nl.socrates.dom.contactdetails;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

@DomainService
public class ContactDetailss extends AbstractFactoryAndRepository {
    
    @ActionSemantics(Of.SAFE)
    public List<ContactDetails> allContactDetails() {
        return allInstances(ContactDetails.class);
    }

    public ContactDetails newContactDetails(
            @Named("Emailadres") String Email,
            @Optional @Named("Telefoonnummer") String Telephone
            )
    {
        // create transient object (not persistent)
        ContactDetails contactDetails = newTransientInstance(ContactDetails.class);
       
        //assign the values
        contactDetails.setEmail(Email);
        contactDetails.setTelephone(Telephone);
        
        // save object to database
        persist(contactDetails);
        return contactDetails;
    }
}

package nl.socrates.dom.login;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@DomainService
public class Logins extends AbstractFactoryAndRepository {
    
    @ActionSemantics(Of.SAFE)
    public List<Login> allLogins() {
        return allInstances(Login.class);
    }
    
    public Login newLogin(
            @Named("Gebruikersnaam") String loginName,
            @Named("Emailadres") String Email
            )
    {
        // create transient object (not persistent)
        Login login = newTransientInstance(Login.class);
        
        //set the values
        login.setLoginName(loginName);
        login.setEmail(Email);
        
        // save object to database
        persist(login);
        return login;
        
    }

}

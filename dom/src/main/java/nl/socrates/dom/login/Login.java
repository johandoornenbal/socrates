package nl.socrates.dom.login;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Bookmarkable
public class Login extends AbstractDomainObject{
    
    private String loginName;
    
    @Named("Gebruikersnaam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "1")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String name) {
        this.loginName = name;
    }

    private String Email;
    
    @Named("Emailadres")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "2")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }    
    
}

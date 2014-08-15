package nl.socrates.dom.contactdetails;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import nl.socrates.dom.user.User;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Bookmarkable
public class ContactDetails extends AbstractDomainObject implements Comparable<ContactDetails>{
    
    User owner;

    @Column(allowsNull="false")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }  
    
    private String Email;

    @Named("Emailadres")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")    
    public String getEmail() {
        return Email;
    }
    
    public void setEmail(String email) {
        this.Email=email;
    }
    
    private String Telephone;

    @Named("Telefoonnummer")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "20")    
    public String getTelephone() {
        return Telephone;
    }
    
    public void setTelephone(String tel) {
        this.Telephone=tel;
    }
    
    
    @Override
    public int compareTo(ContactDetails o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .result();        
    }
    
}



package nl.socrates.dom.contactdetails;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
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
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
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
    @javax.jdo.annotations.Column(allowsNull = "true")
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

    // Region Woonadres
    
    private String livingAddressStreet;

    @Named("Straat")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "30")    
    public String getLivingAddressStreet() {
        return livingAddressStreet;
    }
    
    public void setLivingAddressStreet(String street) {
        this.livingAddressStreet=street;
    }    

    private String livingAddressZip;

    @Named("Postcode")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "40")    
    public String getLivingAddressZip() {
        return livingAddressZip;
    }
    
    public void setLivingAddressZip(String zip) {
        this.livingAddressZip=zip;
    }    

    private String livingAddressTown;

    @Named("Plaatsnaam")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "50")    
    public String getLivingAddressTown() {
        return livingAddressTown;
    }
    
    public void setLivingAddressTown(String town) {
        this.livingAddressTown=town;
    }    

    private String livingAddressCountry;

    @Named("Land")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "60")    
    public String getLivingAddressCountry() {
        return livingAddressCountry;
    }
    
    public void setLivingAddressCountry(String country) {
        this.livingAddressCountry=country;
    }    

    // End region Postadres
    
    // Region Mailaddress
    
    private String mailAddressStreet;

    @Named("Straat postadres")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "70")    
    public String getMailAddressStreet() {
        return mailAddressStreet;
    }
    
    public void setMailAddressStreet(String street) {
        this.mailAddressStreet=street;
    }    

    private String mailAddressZip;

    @Named("Postcode postadres")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "80")    
    public String getMailAddressZip() {
        return mailAddressZip;
    }
    
    public void setMailAddressZip(String zip) {
        this.mailAddressZip=zip;
    }    

    private String mailAddressTown;

    @Named("Plaatsnaam postadres")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "90")    
    public String getMailAddressTown() {
        return mailAddressTown;
    }
    
    public void setMailAddressTown(String town) {
        this.mailAddressTown=town;
    }    

    private String mailAddressCountry;

    @Named("Land postadres")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "100")    
    public String getMailAddressCountry() {
        return mailAddressCountry;
    }
    
    public void setMailAddressCountry(String country) {
        this.mailAddressCountry=country;
    }    
    
    // End Region Mailaddress
    
    @Override
    public int compareTo(ContactDetails o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .result();        
    }
    
}



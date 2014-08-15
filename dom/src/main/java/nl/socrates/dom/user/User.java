package nl.socrates.dom.user;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.value.Blob;

import nl.socrates.dom.contactdetails.ContactDetails;
import nl.socrates.dom.user.User;


@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Bookmarkable
public class User extends AbstractDomainObject implements Comparable<User>{

    public String title() {
        return String.format("%s (%s)", getFirstName(), getDateOfBirth().toString("dd-MM-yyyy"));
    }
    private String baptismalName;

    @Named("Doopnaam")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "1")
    public String getBaptismalName() {
        return baptismalName;
    }

    public void setBaptismalName(String name) {
        this.baptismalName = name;
    }

    
    private String firstName;

    @Named("Naam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "20")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    
    private String middleName;
    
    @Named("tussen")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "30")
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }    

    private String lastName;
    
    @Named("Achternaam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "40")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String name) {
        this.lastName = name;
    }    
    
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "50")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
 
    private String placeOfBirth;

    @Named("Geboorteplaats")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "55")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String name) {
        this.placeOfBirth = name;
    }
    
    private String Nationality;

    @Named("Nationaliteit")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "56")
    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String name) {
        this.Nationality = name;
    }
   
    private Sex sex;
    @Named("Geslacht")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "60")
    public Sex getSex() {
        return sex;
    }
    
    public void setSex(Sex sex) {
        this.sex = sex;
    } 
    
    
    
    //region > foto (property)
    // //////////////////////////////////////////////////////////////////////////
    @javax.jdo.annotations.Persistent(defaultFetchGroup = "false", columns = {
            @javax.jdo.annotations.Column(name = "picture_name"),
            @javax.jdo.annotations.Column(name = "picture_mimetype"),
            @javax.jdo.annotations.Column(name = "picture_bytes", jdbcType = "BLOB", sqlType = "BLOB")
            })
    private Blob picture;

    @Named("Foto")
    @MemberOrder(sequence = "70")
//    @javax.jdo.annotations.Column(allowsNull = "true")
//    @Hidden(where=Where.ALL_TABLES)
    @Optional
    public Blob getPicture() {
        return picture;
    }

    public void setPicture(final Blob picture) {
        this.picture = picture;
    }
    //endregion
    
    private LocalDateTime joinedOn;

    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Persistent
    public LocalDateTime getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(LocalDateTime created) {
        this.joinedOn = created;
    }

    @MemberOrder(sequence = "80")
    @Named("Aangemeld op")
    public LocalDate getDateJoined() {
        return getJoinedOn().toLocalDate();
    }

    // {{ ContactDetails (Collection)
    @Persistent(mappedBy = "owner", dependentElement = "false")
    private SortedSet<ContactDetails> contactDetails = new TreeSet<ContactDetails>();

    @MemberOrder(sequence = "1")
    @Render(Type.EAGERLY)
    public SortedSet<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(final SortedSet<ContactDetails> collectionName) {
        this.contactDetails = collectionName;
    }
    // }}    
    
    @Override
    public int compareTo(User o) {
        return ComparisonChain.start()
        .compare(this.getFirstName(), o.getFirstName())
        .compare(this.getDateOfBirth(), o.getDateOfBirth())
        .result();
    }
    
    @Named("Voeg contactgegevens toe")
    public ContactDetails addContactDetails(){
        ContactDetails contactDetails = newTransientInstance(ContactDetails.class);
        contactDetails.setOwner(this);
        persist(contactDetails);
        return contactDetails; 
    }
    

}

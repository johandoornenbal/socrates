package nl.socrates.dom.party;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.value.Blob;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class PersonProfile extends AbstractDomainObject implements Comparable<PersonProfile>{


    private String profilename;

    @Title
    @Named("Profiel naam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(final String profile) {
        this.profilename = profile;
    }
    
    private Person person;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull="false")
    public Person getPerson()
    {
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
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
    @MemberOrder(sequence = "6")
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
        
    
    @Override
    public int compareTo(PersonProfile other) {
        return ComparisonChain.start()
                .compare(this.getPerson(), other.getPerson())
                .compare(this.getProfilename(), other.getProfilename())
                .result();
    }
    
}

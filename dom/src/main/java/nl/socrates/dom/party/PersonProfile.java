package nl.socrates.dom.party;

import java.util.List;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
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
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileByPerson", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.person.PersonProfile "
                    + "WHERE person == :person"),
    @javax.jdo.annotations.Query(        
            name = "findProfileByPersonAndLevel", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.person.PersonProfile "
                    + "WHERE person == :person && profileTrustlevel == :level")                  
    })
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
    
    @Hidden(where = Where.PARENTED_TABLES)
    @javax.jdo.annotations.Column(allowsNull="false")
    public Person getPerson()
    {
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }

    private TrustLevel profileTrustlevel;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named ("Vertrouwensniveau")
    public TrustLevel getProfileTrustlevel() {
        return profileTrustlevel;
    }
    
    public void setProfileTrustlevel(final TrustLevel level) {
        this.profileTrustlevel = level;
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
        
    @Named("Verwijderen")
    public List<PersonProfile> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Profiel verwijderd");
        
        QueryDefault<PersonProfile> query = 
                QueryDefault.create(
                     PersonProfile.class, 
                    "findProfileByPerson", 
                    "person", getPerson());
        
        return (List<PersonProfile>) container.allMatches(query);   
    }
    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    
    @Override
    public int compareTo(PersonProfile other) {
        return ComparisonChain.start()
                .compare(this.getPerson(), other.getPerson())
                .compare(this.getProfilename(), other.getProfilename())
                .compare(this.getProfileTrustlevel(), other.getProfileTrustlevel())
                .result();
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;   
    
}

package nl.yodo.dom;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import nl.socrates.dom.party.PersonContact;
import nl.socrates.dom.party.TrustLevel;

/**
 * A Domain object that is secure checks who is the owner of an instance. Only the owner and an admin of the app
 * can edit the instance. Only an admin can change the owner field.
 * A domain object that is mutable and can be changed by multiple users over time,
 * and should therefore have optimistic locking controls in place.
 * 
 * <p>
 * Subclasses must be annotated with:
 * <pre>
 * @javax.jdo.annotations.DatastoreIdentity(
 *     strategy = IdGeneratorStrategy.NATIVE,
 *     column = "id")
 * BUT NOT WITH (See reason..)    
 * @javax.jdo.annotations.Version(
 *     strategy=VersionStrategy.VERSION_NUMBER, 
 *     column="version")
 * public class MyDomainObject extends YodoMutableObject {
 *   ...
 * }
 * </pre>
 * 
 * <p>
 * Note however that if a subclass that has a supertype which is annotated 
 * with {@link javax.jdo.annotations.Version} (eg <tt>CommunicationChannel</tt>)
 * then the subtype must not also have a <tt>Version</tt> annotation (otherwise JDO
 * will end up putting a <tt>version</tt> column in both tables, and they are not 
 * kept in sync).
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public abstract class YodoSecureMutableObject<T extends YodoDomainObject<T>>
    extends YodoMutableObject<T> {
    
    public YodoSecureMutableObject(final String keyProperties) {
        super(keyProperties);
    }
    
    // /////// Security ////////////////////////////////////////////////
    
    public String disabled(Identifier.Type type){
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())) {
            return null;
        }
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return null;
        }
        // user is neither owner nor admin
        return "Not allowed to modify / Niet toegestaan te wijzigen";
    }

    
    private String owner;
    
//    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(final String owner) {
        this.owner=owner;
    }
    
    public void changeOwner(final String owner) {
        this.setOwner(owner);
    }
    
    public boolean hideChangeOwner(final String owner) {
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        return true;
    }
    
    @Hidden(where=Where.OBJECT_FORMS)
    public TrustLevel getViewerRights(){      
        QueryDefault<PersonContact> q =
                QueryDefault.create(
                        PersonContact.class, 
                        "findReferringOwnerContactUserName",
                        "owner", this.getOwner(),
                        "contactUserName", container.getUser().getName());
                if (container.allMatches(q).isEmpty()) {
                    return null;
                }
                if (!container.allMatches(q).isEmpty()) {
                    TrustLevel rights = container.firstMatch(q).getLevel();
                    return rights;
                }
        return null;
    }
 
    // /////// Injects //////////////////////////////////////////////// 
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}

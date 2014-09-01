package nl.socrates.dom.communicationchannel;

import java.util.SortedSet;

import javax.inject.Inject;
import javax.jdo.annotations.*;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionInteractionEvent;

import nl.socrates.dom.SocratesMutableObject;
import nl.socrates.dom.JdoColumnLength;
import nl.socrates.dom.WithNameGetter;
import nl.socrates.dom.WithReferenceGetter;

/**
 * Represents a mechanism for communicating with its
 * {@link CommunicationChannelOwner owner}.
 * 
 * <p>
 * This is an abstract entity; concrete subclasses are {@link PostalAddress
 * postal}, {@link PhoneOrFaxNumber phone/fax} and {@link EmailAddress email}.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByReferenceAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM nl.socrates.dom.communicationchannel.CommunicationChannel "
                        + "WHERE reference == :reference "
                        + "&& type == :type"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM nl.socrates.dom.communicationchannel.CommunicationChannel "
                        + "WHERE owner == :owner "),
        @javax.jdo.annotations.Query(
                name = "findByOwnerAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM nl.socrates.dom.communicationchannel.CommunicationChannel "
                        + "WHERE owner == :owner && type == :type"),
        @javax.jdo.annotations.Query(
                name = "findOtherByOwnerAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM nl.socrates.dom.communicationchannel.CommunicationChannel "
                        + "WHERE owner == :owner && type == :type && this != :exclude")

})
@Bookmarkable(BookmarkPolicy.AS_CHILD)
public abstract class CommunicationChannel 
    extends SocratesMutableObject<CommunicationChannel>
    implements WithNameGetter, WithReferenceGetter {

    public CommunicationChannel() {
        super("id, type");
    }

    public String iconName() {
        return getType().title().replace(" ", "");
    }
    
    // //////////////////////////////////////

    @MemberOrder(sequence = "2")
    @Hidden(where = Where.OBJECT_FORMS)
    public String getName() {
        return getContainer().titleOf(this);
    }

    // //////////////////////////////////////
    
    private CommunicationChannelOwner owner;

    /**
     * nb: annotated as <tt>@Optional</tt>, but this is a workaround because
     * cannot set <tt>@Column(allowNulls="false")</tt> for a polymorphic
     * relationship.
     */
    @javax.jdo.annotations.Persistent(
            extensions = {
                    @Extension(vendorName = "datanucleus",
                            key = "mapping-strategy",
                            value = "per-implementation"),
                    @Extension(vendorName = "datanucleus",
                            key = "implementation-classes",
                            value = "nl.socrates.dom.party.Party") //TODO: Asset eruit gehaald hier moet ik wat mee
            })
    @javax.jdo.annotations.Columns({
            @javax.jdo.annotations.Column(name = "ownerPartyId"),
//            @javax.jdo.annotations.Column(name = "ownerFixedAssetId")
    })
    @Optional
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public CommunicationChannelOwner getOwner() {
        return owner;
    }

    public void setOwner(final CommunicationChannelOwner owner) {
        this.owner = owner;
    }

    // //////////////////////////////////////  
    
    private CommunicationChannelType type;

    @MemberOrder(sequence = "1")
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.TYPE_ENUM)
    @Hidden(where = Where.OBJECT_FORMS)
    public CommunicationChannelType getType() {
        return type;
    }

    public void setType(final CommunicationChannelType type) {
        this.type = type;
    }

    // //////////////////////////////////////
    
    private String reference;

    /**
     * For import purposes only
     */
    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.REFERENCE)
    @Hidden
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    // //////////////////////////////////////
    
    private String description;

    @javax.jdo.annotations.Column(length = JdoColumnLength.DESCRIPTION)
    @Hidden(where = Where.ALL_TABLES)
    @Optional
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    // //////////////////////////////////////
    
    private boolean legal;

    @MemberOrder(sequence = "3")
    public boolean isLegal() {
        return legal;
    }

    public void setLegal(final boolean Legal) {
        this.legal = Legal;
    }

    // //////////////////////////////////////
    
    public static class RemoveEvent extends ActionInteractionEvent<CommunicationChannel> {
        private static final long serialVersionUID = 1L;

        public RemoveEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public CommunicationChannel getReplacement() {
            return (CommunicationChannel) (this.getArguments().isEmpty() ? null : getArguments().get(0));
        }
    }

    @ActionInteraction(CommunicationChannel.RemoveEvent.class)
    public void remove(@Named("Replace with") @Optional CommunicationChannel replacement) {
        getContainer().remove(this);
    }

    public SortedSet<CommunicationChannel> choices0Remove() {
        return communicationChannels.findOtherByOwnerAndType(getOwner(), getType(), this);
    }

    
    @Inject
    CommunicationChannels communicationChannels;    
}

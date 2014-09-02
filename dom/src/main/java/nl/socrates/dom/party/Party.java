/*
 *
 *  Copyright 2014 YODO international projects and consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package nl.socrates.dom.party;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import nl.socrates.dom.JdoColumnLength;
import nl.socrates.dom.RegexValidation;
import nl.socrates.dom.SocratesMutableObject;
import nl.socrates.dom.WithNameComparable;
import nl.socrates.dom.WithReferenceUnique;
import nl.socrates.dom.communicationchannel.CommunicationChannelOwner;

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
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "Party_reference_UNQ", members = "reference")
})
@javax.jdo.annotations.Indices({
    // to cover the 'findByReferenceOrName' query
    // both in this superclass and the subclasses
    @javax.jdo.annotations.Index(
            name = "Party_reference_name_IDX", members = { "reference", "name" })
})
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchByReferenceOrName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.Party "
                    + "WHERE reference.matches(:referenceOrName) "
                    + "   || name.matches(:referenceOrName)"),
    @javax.jdo.annotations.Query(
            name = "findByReference", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.Party "
                    + "WHERE reference == :reference") })
@AutoComplete(repository = Parties.class, action = "autoComplete")
@Bookmarkable
public abstract class Party
        extends SocratesMutableObject<Party>
        implements WithNameComparable<Party>, WithReferenceUnique, CommunicationChannelOwner {

    public Party() {
        super("name");
    }

    // //////////////////////////////////////

    private String reference;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.REFERENCE)
    @RegEx(validation = RegexValidation.REFERENCE, caseSensitive = false)
    @Disabled
    @Title(sequence = "1")
    @Named("Referentie")
    @Hidden(where=Where.ALL_TABLES)
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    // //////////////////////////////////////
    
    private String name;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.Party.NAME)
    @Title(sequence = "2", prepend = "-")
    @Named("Naam")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Provided so that subclasses can override and disable.
     */
    public String disableName() {
        return null;
    }

    // //////////////////////////////////////
    
    
    
}

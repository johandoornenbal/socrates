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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.TitleBuffer;

import nl.socrates.dom.JdoColumnLength;



@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.person.Person "
                    + "WHERE lastName.matches(:lastName)")
    })
@AutoComplete(repository=Persons.class,  action="autoComplete")
public class Person extends Party {
    
    private String initials;

    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.Person.INITIALS)
    @Named("Voorletter(s)")
    @Hidden(where=Where.ALL_TABLES)
    public String getInitials() {
        return initials;
    }

    public void setInitials(final String initials) {
        this.initials = initials;
    }
    

    // //////////////////////////////////////
    
    @Override
    public String disableName() {
        return "Cannot be updated directly; derived from first and last names";
    }

    // //////////////////////////////////////
    
    private String firstName;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "10")
    @Named("Voornaam")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // //////////////////////////////////////
    
    private String middleName;

    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.MIDDLE_NAME)
    @MemberOrder(sequence = "20")
    @Named("Tussen")
    @Hidden(where=Where.ALL_TABLES)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }


    // //////////////////////////////////////

    private String lastName;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "30")
    @Named("Achternaam")
    @Hidden(where=Where.ALL_TABLES)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    //////////////////////////////////////////////////
    
    private String baptismalName;

    @Named("Doopnaam")
    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "40")
    @Hidden(where=Where.ALL_TABLES)
    public String getBaptismalName() {
        return baptismalName;
    }

    public void setBaptismalName(String name) {
        this.baptismalName = name;
    }


    // //////////////////////////////////////
    
    private PersonGenderType gender;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.TYPE_ENUM)
    @MemberOrder(sequence = "50")
    @Named("Geslacht")
    public PersonGenderType getGender() {
        return gender;
    }

    public void setGender(final PersonGenderType gender) {
        this.gender = gender;
    }

    public PersonGenderType defaultGender() {
        return PersonGenderType.UNKNOWN;
    }

    // //////////////////////////////////////
    
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "60")
    @Named("Geboortedatum")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    // //////////////////////////////////////
 
    private String placeOfBirth;

    @Named("Geboorteplaats")
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "70")
    @Hidden(where=Where.ALL_TABLES)
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String name) {
        this.placeOfBirth = name;
    }

    // //////////////////////////////////////    
    
    private String nationality;

    @Named("Nationaliteit")
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "80")
    @Hidden(where=Where.ALL_TABLES)
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    // //////////////////////////////////////
    
    private Person lastContact;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Laatste die mij als contact toegevoegd heeft")
    @Disabled
    public Person getLastContact() {
        return lastContact;
    }
    
    public void setLastContact(final Person lastcontact) {
        this.lastContact = lastcontact;
    }
    
    // //////////////////////////////////////
    
    public String validate() {
        if ((getFirstName() == null || getFirstName().isEmpty()) && (getInitials() == null || getInitials().isEmpty())) {
                return "Er moet tenminste een voorletter of een voornaam ingevuld zijn";}
        else {
            return null;
        }
    }

    public void updating() {
        TitleBuffer tb = new TitleBuffer();
//        TODO: deze werkt op een of andere manier niet goed met JDO
        setName(tb.append(getLastName()).append(",", getFirstName()).toString());
    }
    
    // PersonProfile (Collection)
    private SortedSet<PersonProfile> personprofiles = new TreeSet<PersonProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "person", dependentElement = "true")
    @Named("Profielen")
    public SortedSet<PersonProfile> getPersonprofiles() {
        return personprofiles;
    }

    public void setPersonprofiles(final SortedSet<PersonProfile> personProfiles){
        this.personprofiles = personProfiles;
    }
    
    private SortedSet<PersonContact> personcontacts = new TreeSet<PersonContact>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "owner", dependentElement = "true")
    @Named("Persoonlijke contacten")
    public SortedSet<PersonContact> getPersoncontacts() {
        return personcontacts;
    }
    
    public void setPersoncontacts(final SortedSet<PersonContact> personcontacts) {
        this.personcontacts = personcontacts;
    }
    
//    @Render(Type.EAGERLY)
//    @Named("Personen verwijzend naar mij")
//    public List<Person> getReferringToMe(){
//        List<Person> pReferring = new ArrayList<Person>();
//        for(PersonContact e: pcontacts.listAll()) {
//            if (e.getContact() == this)
//                pReferring.add(e.getOwner());
//        }
//        return pReferring;
//    }

    @Render(Type.EAGERLY)
    @Named("Personen verwijzend naar mij")
    public List<Referer> getReferringToMe2(){
        List<Referer> pReferring = new ArrayList<Referer>();
        for(PersonContact e: pcontacts.listAll()) {
            if (e.getContact() == this) {
                Referer referer = new Referer();
                referer.setOwner(e.getOwner());
                referer.setDate(e.getDateCreatedOn());
                referer.setLevel(e.getLevel());
                pReferring.add(referer);
            }
        }
        return pReferring;
    }    
    

    @Inject
    PersonContacts pcontacts;
    
    // TODO: dient nog nergens voor. Probeer te regelen dat Referer goed doorklikt in de WicketViewer en niet een Null error geeft
    @SuppressWarnings("unused")
    @javax.inject.Inject
    private DomainObjectContainer container;
}

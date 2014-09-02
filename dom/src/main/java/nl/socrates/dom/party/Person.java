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

import javax.jdo.annotations.InheritanceStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.TitleBuffer;

import nl.socrates.dom.JdoColumnLength;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Person extends Party {
    
    private String initials;

    @javax.jdo.annotations.Column(length = JdoColumnLength.Person.INITIALS)
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

    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.PROPER_NAME)
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
    
    public String validate() {
        return getFirstName().isEmpty() || getInitials().isEmpty() ?
                "Er moet tenminste een voorletter of een voornaam ingevuld zijn" : null;
    }

    public void updating() {
        TitleBuffer tb = new TitleBuffer();
        setName(tb.append(getLastName()).append(",", getFirstName()).append(" ", getMiddleName()).toString());
    }

}

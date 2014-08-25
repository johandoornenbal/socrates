package nl.socrates.fixture.party;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.dom.party.Persons;
import nl.socrates.dom.party.Party;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);

    protected Party createPerson(String reference, String initials, String firstName, String middleName, String lastName, String baptismalName, PersonGenderType Gender, LocalDate dateOfBirth, String placeOfBirth, String Nationality, ExecutionContext executionContext) {

        Party party = persons.newPerson(reference, initials, firstName, middleName,lastName, baptismalName, Gender, dateOfBirth, placeOfBirth, Nationality);
        return executionContext.add(this, party.getReference(), party);
    }


    // //////////////////////////////////////

 

    @Inject
    protected Persons persons;

}

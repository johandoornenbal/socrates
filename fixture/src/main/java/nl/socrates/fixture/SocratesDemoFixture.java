package nl.socrates.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.party.Person;
import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.dom.party.Persons;


public class SocratesDemoFixture extends FixtureScript {
    
    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
        execute(new TearDownFixture(), executionContext);
        
        // create
        create("JDOORN", "J", "Johan","","Doornenbal", "Johannes", PersonGenderType.MALE, new LocalDate(1962,7,16), "Leeuwarden", "Nederland", executionContext);
        
    }
    
    // //////////////////////////////////////

    private Person create(String reference, String initials, String firstName, String middleName, String lastName, String baptismalName, PersonGenderType gender, LocalDate dateOfBirth, String placeOfBirth, String Nationality, ExecutionContext executionContext) {
        return persons.newPerson(reference, initials, firstName, middleName,lastName, baptismalName, gender, dateOfBirth, placeOfBirth, Nationality);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Persons persons;

}

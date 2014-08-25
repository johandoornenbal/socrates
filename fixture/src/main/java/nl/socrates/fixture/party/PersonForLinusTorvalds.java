package nl.socrates.fixture.party;

import org.joda.time.LocalDate;

import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.fixture.party.PersonAbstract;

public class PersonForLinusTorvalds extends PersonAbstract {

    public static final String PARTY_REFERENCE = "LTORVALDS";

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(PARTY_REFERENCE, "L", "Linus","","Torvalds", "LinusBaptismal" ,PersonGenderType.MALE, new LocalDate(1962,7,16), "Stockholm", "Sweden",  executionContext);
    }
}

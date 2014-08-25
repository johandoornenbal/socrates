package nl.socrates.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import nl.socrates.fixture.party.PersonForLinusTorvalds;

public class SocratesDemoFixture extends DiscoverableFixtureScript {
    
    public SocratesDemoFixture() {
        super(null, "demo");
    }
    
    @Override
    protected void execute(ExecutionContext executionContext) {
        execute(new PersonForLinusTorvalds(), executionContext);
    }

}

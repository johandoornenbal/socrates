package nl.socrates.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.login.Login;
import nl.socrates.dom.login.Logins;

public class LoginsFixture extends FixtureScript{

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        create("johan@filternet.nl");
        create("jvdwal@example.com");
    }

    // //////////////////////////////////////

    private Login create(final String Email) {
        return logins.newLogin(Email);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Logins logins;    
}

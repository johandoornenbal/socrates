package nl.socrates.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.login.Login;
import nl.socrates.dom.login.Logins;

public class LoginsFixture extends FixtureScript{

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        create("Johannes", "johan@filternet.nl");
        create("Jeroen", "jvdwal@example.com");
    }

    // //////////////////////////////////////

    private Login create(final String loginName, final String Email) {
        return logins.newLogin(loginName, Email);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Logins logins;    
}

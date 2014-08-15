package nl.socrates.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.contactdetails.ContactDetails;
import nl.socrates.dom.user.User;
import nl.socrates.dom.user.Users;

public class ContactsFixture extends FixtureScript {
    
    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new UsersFixture(), executionContext);

        User user = users.allUsers().get(0);

        ContactDetails details = (ContactDetails) user.addContactDetails();

        details.setEmail("email@example.com");

    }

    @Inject
    Users users;

}

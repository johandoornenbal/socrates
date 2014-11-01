package nl.yodo.app.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixtures.LogonFixture;
import org.apache.isis.applib.fixtures.SwitchUserFixture;

import nl.yodo.app.integtest.YodoIntegrationTest;
import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.YodoTrustedContact;
import nl.yodo.dom.YodoTrustedContacts;
import nl.yodo.fixture.YodoSwitchToJohanLogon;

public class YodoTrustedContactsTest extends YodoIntegrationTest {
    
    @Inject 
    YodoTrustedContacts contacts;
    @javax.inject.Inject
    DomainObjectContainer container;
    
    @BeforeClass
    public static void setupTransactionalData() {
//        scenarioExecution().install(new SocratesDemoFixture());
        scenarioExecution().install(new YodoSwitchToJohanLogon()); //Werkt niet lijkt het...
    }
    
    public static class NewContact extends YodoTrustedContactsTest {
        
        // TODO: Hoe test ik dat een tweede, zelfde contact niet mag voorkomen?
        // (de methode validateNewContact dus....)
        
        private static final String CONTACT = "Inez";
        private static final String OWNER = "tester";
        private static final TrustLevel LEVEL = TrustLevel.ENTRY_LEVEL;
        
        YodoTrustedContact c;
        
        @Before
        public void setUp() throws Exception { 
            c=contacts.newContact(CONTACT);
        }
        

        @Test
        public void valuesSet() throws Exception {
            assertThat(c.getContact(), is(CONTACT));
            assertThat(c.getOwner(), is(OWNER));
            assertThat(c.getLevel(), is(LEVEL));
        }
    }

}

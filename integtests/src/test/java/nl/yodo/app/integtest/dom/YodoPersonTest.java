package nl.yodo.app.integtest.dom;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nl.yodo.app.integtest.YodoIntegrationTest;
import nl.yodo.dom.Party.YodoPerson;
import nl.yodo.dom.Party.YodoPersons;
import nl.yodo.fixture.party.YodoPersonsTeardown;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class YodoPersonTest extends YodoIntegrationTest {
    
    @Inject
    YodoPersons persons;
    
    public static class TestPerson extends YodoPersonTest {
        
        YodoPerson p1;
        
        @Test
        public void valuesSet() throws Exception {
            p1 = persons.allPersons().get(0);
            
            assertThat(p1.getFirstName(), is("T."));
            assertThat(p1.getLastName(), is("Test1"));

            assertThat(p1.getMiddleName(), is("van der"));
            
            assertThat(p1.getUniquePartyId(), is("111"));
            
            assertThat(p1.getOwnedBy(), is("test1"));
            
        }
        
    }

    public static class NewPerson extends YodoPersonTest {
        
        YodoPerson p1;
        YodoPerson p2;
        
        @BeforeClass
        public static void setupTransactionalData() {
            scenarioExecution().install(new YodoPersonsTeardown());
        }
        
        @Before
        public void setUp() throws Exception {
            p1=persons.newPerson("123", "Voornaam", "van", "Achteren", "test1");
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);
            
            assertThat(p1.getFirstName(), is("Voornaam"));
            assertThat(p1.getLastName(), is("Achteren"));

            assertThat(p1.getMiddleName(), is("van"));
            
            assertThat(p1.getUniquePartyId(), is("123"));
            
            assertThat(p1.getOwnedBy(), is("test1"));
            
            assertThat(p2.getUniquePartyId(), is("123"));
            assertThat(p2.getOwnedBy(), is("test1"));
            
            assertThat(p1,is(p2));
            
        }
        
    }    
}

package integration.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import nl.socrates.dom.party.Parties;

public class PartiesTest extends SimpleAppIntegTest {

    @Inject
    Parties parties;

    @Test
    public void all() throws Exception {
        assertThat(parties.allParties().size(), is(3));
    }

}

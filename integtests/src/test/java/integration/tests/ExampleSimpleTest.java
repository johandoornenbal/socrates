package integration.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ExampleSimpleTest extends SimpleAppIntegTest{
    
    private Boolean boolA = true;
    private Boolean boolB = false;
    
    @Test
    public void dummy() throws Exception {
        assertThat(boolA, is(true));
        assertThat(boolB, is(false));
    }

}

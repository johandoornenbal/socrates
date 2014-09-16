package integration.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ExampleSocratesTest extends SocratesIntegrationTest {

    private Boolean boolA = true;
    private Boolean boolB = false;
    
    
//    @BeforeClass
//    public static void setupTransactionalData() {
//        scenarioExecution().install(new SocratesDemoFixture());
//    }
    
    @Test
    public void dummy() throws Exception {
        assertThat(boolA, is(true));
        assertThat(boolB, is(false));
    }

}

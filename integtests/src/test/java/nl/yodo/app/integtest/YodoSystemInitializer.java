package nl.yodo.app.integtest;

import org.apache.isis.core.integtestsupport.IsisSystemForTest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on
 * the current thread, initialized with Yodo's domain services and with
 * {@link org.estatio.fixture.EstatioBaseLineFixture reference data fixture}.
 */
public class YodoSystemInitializer {
    
    private YodoSystemInitializer() {
        
    }
    
    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if (isft == null) {
            isft = new YodoIntegTestBuilder().build().setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }

}

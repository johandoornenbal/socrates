package nl.yodo.fixture.pruts;

public class SecObjForTest1 extends SecObjAbstractFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        createSecObject("TestObject1");

    }

}

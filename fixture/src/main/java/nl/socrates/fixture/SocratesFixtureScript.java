package nl.socrates.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SocratesFixtureScript extends FixtureScript {

    protected SocratesFixtureScript() {
    }

    protected SocratesFixtureScript(String friendlyName, String localName) {
        super(friendlyName, localName);
    }

    protected SocratesFixtureScript(String friendlyName, String localName, Discoverability discoverability) {
        super(friendlyName, localName, discoverability);
    }

    // //////////////////////////////////////

    public static enum Prereqs {
        EXEC,
        SKIP
    }

    private Prereqs prereqs = Prereqs.EXEC;

    public SocratesFixtureScript withNoPrereqs() {
        return with(Prereqs.SKIP);
    }

    private SocratesFixtureScript with(final Prereqs prereqs) {
        this.prereqs = prereqs;
        return this;
    }

    protected boolean isExecutePrereqs() {
        return prereqs == Prereqs.EXEC;
    }

    // //////////////////////////////////////

    protected void execute(final String localNameOverride, final FixtureScript fixtureScript, ExecutionContext executionContext) {
        // cascade the prereqs setting
        if(fixtureScript instanceof SocratesFixtureScript) {
            final SocratesFixtureScript socratesFixtureScript = (SocratesFixtureScript) fixtureScript;
            socratesFixtureScript.with(prereqs);
        }
        super.execute(localNameOverride, fixtureScript, executionContext);
    }

}
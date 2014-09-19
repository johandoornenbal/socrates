package nl.socrates.dom.party;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Optional;

import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "25", repositoryFor = ChildOfPerson.class)
public class ChildOfPersons extends SocratesDomainService<ChildOfPerson>{
    
    public ChildOfPersons() {
        super (ChildOfPersons.class, ChildOfPerson.class);
    }

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "Person", sequence = "1")
    @NotInServiceMenu
    public ChildOfPerson newChild(
            final Person person,
            final  @Optional String name){
        final ChildOfPerson child = newTransientInstance(ChildOfPerson.class);
        child.setFieldOnChild(person);
        child.setTestName(name);
        persist(child);
        return child;
    }

}

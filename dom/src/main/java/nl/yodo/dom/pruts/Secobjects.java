package nl.yodo.dom.pruts;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "10", repositoryFor = Secobject.class)
@Named("Pruts")
public class Secobjects extends SocratesDomainService<Secobject>{
    
    public Secobjects(){
        super(Secobjects.class, Secobject.class);
    }

    public List<Secobject> allSecobjects(){
        return allInstances();
    }
    
    public Secobject newSecobject(
            final String name) {
        final Secobject obj = newTransientInstance(Secobject.class);
        obj.setName(name);
        obj.setOwner(container.getUser().getName());
        obj.setTestLevelOuter("Outer test");
        obj.setTestLevelInstap("Instap Test");
        obj.setTestLevelInner("Inner test");
        obj.setTestLevelIntimate("Intimate test");
        persist(obj);
        return obj;
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;
}

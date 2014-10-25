package nl.yodo.dom.pruts;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bookmarkable;

import nl.socrates.dom.party.TrustLevel;
import nl.yodo.dom.YodoSecureMutableObject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Bookmarkable
public class Secobject extends YodoSecureMutableObject<Secobject> {
    
    public Secobject(){
        super("name");
    }
    
    private String name;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name=name;
    }
    
    private String testLevelOuter;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelOuter() {
        return testLevelOuter;
    }
    
    public void setTestLevelOuter(final String string) {
        this.testLevelOuter=string;
    }
    
    public boolean hideTestLevelOuter() {
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        if (super.getViewerRights()!=null && TrustLevel.OUTER_CIRCLE.compareTo(super.getViewerRights())<=0){
            return false;
        }
        return true;
    }    
    
    private String testLevelInstap;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelInstap() {
        return testLevelInstap;
    }
    
    public void setTestLevelInstap(final String string) {
        this.testLevelInstap=string;
    }
    
    public boolean hideTestLevelInstap() {
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        if (super.getViewerRights()!=null && TrustLevel.ENTRY_LEVEL.compareTo(super.getViewerRights())<=0){
            return false;
        }
        return true;
    }    
    
    private String testLevelInner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelInner() {
        return testLevelInner;
    }
    
    public void setTestLevelInner(final String string) {
        this.testLevelInner=string;
    }

    public boolean hideTestLevelInner() {
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        if (super.getViewerRights()!=null && TrustLevel.INNER_CIRCLE.compareTo(super.getViewerRights())<=0){
            return false;
        }
        return true;
    }
    
    
    private String testLevelIntimate;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelIntimate() {
        return testLevelIntimate;
    }
    
    public void setTestLevelIntimate(final String string) {
        this.testLevelIntimate=string;
    }

    public boolean hideTestLevelIntimate() {
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        if (super.getViewerRights()!=null && TrustLevel.INTIMATE.compareTo(super.getViewerRights())<=0){
            return false;
        }
        return true;
    }

   
    @javax.inject.Inject
    private DomainObjectContainer container;

}

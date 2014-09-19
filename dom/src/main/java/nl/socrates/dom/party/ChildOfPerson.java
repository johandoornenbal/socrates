package nl.socrates.dom.party;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ChildOfPerson extends AbstractDomainObject {
    
    private Person fieldOnChild;
    
    @javax.jdo.annotations.Column(allowsNull="false")
    public Person getFieldOnChild() {
        return fieldOnChild;
    }
    
    public void setFieldOnChild(final Person fieldOnChild){
        this.fieldOnChild = fieldOnChild;
    }
    
    private String testName;
    
    @javax.jdo.annotations.Column(allowsNull="true")
    @Named("Regeltje om in te vullen")
    public String getTestName() {
        return testName;
    }
    
    public void setTestName(final String testName){
        this.testName = testName;
    }
    
    
//    public void modifyParentPropertyName(final ParentPropertyType parentPropertyName) {
//        ParentPropertyType currentParentPropertyName = getParentPropertyName();
//        // check for no-op
//        if (parentPropertyName == null ||
//                parentPropertyName.equals(currentParentPropertyName)) {
//            return;
//        }
//        // delegate to parent to associate
//        parentPropertyName.modifyChildPropertyNameInParent(this);
//        // additional business logic
//        onModifyParentPropertyName(currentParentPropertyName, parentPropertyName);
//    }
//
//    public void clearPropertyName() {
//        ParentPropertyType currentParentPropertyName = getParentPropertyName();
//        // check for no-op
//        if (currentParentPropertyName == null) {
//            return;
//        }
//        // delegate to parent to dissociate
//        currentParentPropertyName.clearChildPropertyNameInParent();
//        // additional business logic
//        onClearParentPropertyName(currentParentPropertyName);
//    }
}

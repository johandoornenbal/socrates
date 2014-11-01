package nl.yodo.dom.Party;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Where;

import nl.yodo.dom.TrustLevel;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findYodoPersonUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPerson "
                    + "WHERE owner == :owner"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPerson "
                    + "WHERE lastName.matches(:lastName)")                    
})
@AutoComplete(repository=YodoPersons.class,  action="autoComplete")
public class YodoPerson extends YodoParty {
    
    private String firstName;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String fn) {
        this.firstName = fn;
    }
    
    private String lastName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String ln) {
        this.lastName = ln;
    }
    
    public void modifyLastName(final String ln) {
        setLastName(ln);
        container.informUser("Bijgewerkt nog niet persisted!");
    }
    
    private String notForAllEyes;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Hidden(where=Where.ALL_TABLES)
    public String getNotForAllEyes() {
        return notForAllEyes;
    }
    
    public void setNotForAllEyes(final String string) {
        this.notForAllEyes = string;
    }
    
    public boolean hideNotForAllEyes() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    
    // TESTS /////////////////////////////////////////////////////
    
    public String title() {
        return this.getLastName() + ", " + this.getFirstName();
    }
    
    public void updating() {
        container.informUser("Nog niet persisted - TEST!");
    }
    
    public void updated() {
        container.informUser("Persisted - TEST!");
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}

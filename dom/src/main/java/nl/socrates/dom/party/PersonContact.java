package nl.socrates.dom.party;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@AutoComplete(repository=PersonContacts.class, action="autoComplete")
public class PersonContact extends AbstractDomainObject implements Comparable<PersonContact>{
    
    private Person owner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    @Named("Eigenaar")
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner (Person owner) {
        this.owner = owner;
    }
    
    private Person contact;
   
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "20")
    @Named("Contact")   
    public Person getContact() {
        return contact;
    }
    
    public void setContact(Person contact) {
        this.contact = contact;
    }
    
    private Integer level;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "30")
    @Named("Niveau")
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int compareTo(PersonContact o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .compare(this.getContact(), o.getContact())
                .compare(this.getLevel(), o.getLevel())
                .result();
    }
    
    

}

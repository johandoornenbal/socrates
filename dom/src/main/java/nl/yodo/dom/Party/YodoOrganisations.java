package nl.yodo.dom.Party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "14", repositoryFor = YodoOrganisation.class)
@Named("Pruts Organisaties")
public class YodoOrganisations extends YodoDomainService<YodoOrganisation> {
    
    public YodoOrganisations() {
        super(YodoOrganisations.class, YodoOrganisation.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public YodoOrganisation newOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName){
        YodoOrganisation org = newTransientInstance(YodoOrganisation.class);
        org.setOrganisationName(organisationName);
        org.setUniquePartyId(uniquePartyId);
        org.setOwner(container.getUser().getName());
        persist(org);
        return org;
    }
    
    public List<YodoOrganisation> allOrganisations() {
        return allInstances();
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;
}

package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings;

import co.ke.emtechhouse.hrm_payroll_system._exception.OrganizationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepo  organizationRepo;

    public Organization addOrganization(Organization organization){
        return organizationRepo.save(organization);
    }
    public List<Organization> findAllOrganization(){
        return organizationRepo.findAll();
    }
    public Organization findOrganizationById(Long id){
        return organizationRepo.findOrganizationById(id).orElseThrow(()-> new OrganizationNotFoundException("Organization " + id +"was not found"));
    }
//
    public Organization updateOrganization(Organization organization){
        return organizationRepo.save(organization);
    }
    public void deleteOrganization(Long id){
        organizationRepo.deleteOrganizationById(id);
    }
}

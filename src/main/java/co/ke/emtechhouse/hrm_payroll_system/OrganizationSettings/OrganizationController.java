package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/configure/organization/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationController(OrganizationService organizationService, OrganizationRepo organizationRepo) {
        this.organizationService = organizationService;
        this.organizationRepo = organizationRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<Organization> addOrganization(@RequestBody Organization organization){
        List<Organization> organizations = organizationService.findAllOrganization();
//        TODO: CHECK IF ORGANIZATION EXIST THEN BREAK
//        if(organizations){
//
//        }


        Organization newOrganization = organizationService.addOrganization(organization);
        return  new ResponseEntity<>(newOrganization, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Organization>> getAllOrganizations () {
        List<Organization> organizations = organizationService.findAllOrganization();
        return  new ResponseEntity<>(organizations, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Organization> getDepartmentById (@PathVariable("id") Long id){
        Organization organization = organizationService.findOrganizationById(id);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable("id") long id, @RequestBody Organization organization){
        Optional<Organization> organizationData = organizationRepo.findOrganizationById(id);
        if (organizationData.isPresent()) {
            Organization _organization = organizationData.get();
            _organization.setOrganization_address(organization.getOrganization_address());
            _organization.setOrganization_street(organization.getOrganization_street());
            _organization.setOrganization_building(organization.getOrganization_building());
            _organization.setOrganization_floor(organization.getOrganization_floor());
            _organization.setOrganization_kra_pin(organization.getOrganization_kra_pin());
            _organization.setOrganization_business_no(organization.getOrganization_business_no());
            _organization.setOrganization_bank1_name(organization.getOrganization_bank1_name());
            _organization.setOrganization_bank1_account(organization.getOrganization_bank1_account());
            _organization.setOrganization_bank2_name(organization.getOrganization_bank2_name());
            _organization.setOrganization_bank2_account(organization.getOrganization_bank2_account());
            _organization.setOrganization_pay_bill(organization.getOrganization_pay_bill());
            _organization.setOrganization_till(organization.getOrganization_till());
            _organization.setOrganization_telephone(organization.getOrganization_telephone());
            _organization.setOrganization_email(organization.getOrganization_email());
            _organization.setOrganization_location(organization.getOrganization_location());
            _organization.setOrganization_county(organization.getOrganization_county());
            _organization.setOrganization_country(organization.getOrganization_country());
            _organization.setOrganization_website(organization.getOrganization_website());
            _organization.setOrganization_map_link(organization.getOrganization_map_link());
            _organization.setStatus(organization.getStatus());
            _organization.setDeleted_at(organization.getDeleted_at());
            return new ResponseEntity<>(organizationRepo.save(_organization), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Organization> deleteOrganization(@PathVariable("id") Long id){
        organizationService.deleteOrganization(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Controllers;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Deliverables;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services.Pm_DeliverablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/deliverables/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_DeliverablesController {
    @Autowired
    private Pm_DeliverablesService pm_deliverablesService;

    @PostMapping("/add")
    public ResponseEntity<Pm_Deliverables> addPm_Deliverables(@RequestBody Pm_Deliverables pm_deliverables){
        Pm_Deliverables newPm_Deliverables = pm_deliverablesService.addPm_Deliverables(pm_deliverables);
        return  new ResponseEntity<>(newPm_Deliverables, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_Deliverables>> getAllPm_Deliverabless () {
        List<Pm_Deliverables> pm_deliverabless = pm_deliverablesService.findAllPm_Deliverabless();
        return  new ResponseEntity<>(pm_deliverabless, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_Deliverables> getPm_DeliverablesById (@PathVariable("id") Long id){
        Pm_Deliverables pm_deliverables = pm_deliverablesService.findPm_DeliverablesById(id);
        return new ResponseEntity<>(pm_deliverables, HttpStatus.OK);
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Pm_Deliverables> updatePm_Deliverables(@PathVariable("id") long id, @RequestBody Pm_Deliverables pm_deliverables){
//        Optional<Pm_Deliverables> pm_deliverablesData = pm_deliverablesRepo.findPm_DeliverablesById(id);
//
//        if (pm_deliverablesData.isPresent()) {
//            Pm_Deliverables _pm_deliverables = pm_deliverablesData.get();
//            _pm_deliverables.setPm_DeliverablesName(pm_deliverables.getPm_DeliverablesName());
//            _pm_deliverables.setHeadOfPm_Deliverables(pm_deliverables.getHeadOfPm_Deliverables());
//            _pm_deliverables.setDirectorOfPm_Deliverables(pm_deliverables.getDirectorOfPm_Deliverables());
//            _pm_deliverables.setPm_DeliverablesMail(pm_deliverables.getPm_DeliverablesMail());
//            _pm_deliverables.setStatus(pm_deliverables.getStatus());
//            _pm_deliverables.setDeleted(pm_deliverables.getDeleted());
//            return new ResponseEntity<>(pm_deliverablesRepo.save(_pm_deliverables), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pm_deliverablesService.deletePm_Deliverables(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
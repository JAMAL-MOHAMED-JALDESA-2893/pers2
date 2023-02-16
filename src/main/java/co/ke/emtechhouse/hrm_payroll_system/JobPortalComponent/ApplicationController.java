package co.ke.emtechhouse.hrm_payroll_system.JobPortalComponent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class ApplicationController {
private  final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @PostMapping("/add")
    public ResponseEntity<Application> addApplication(@RequestBody Application application){
        Application newApplication = applicationService.addApplication(application);
        return new ResponseEntity<>(newApplication, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Application>> getAllApplications(){
        List<Application> applications = applicationService.findAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Application> getAPplicationId (@PathVariable("id") Long id){
        Application application = applicationService.findApplicationById(id);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<Application> updateApplication(@RequestBody Application application){
        Application updateApplication = applicationService.updateApplication(application);
        return new ResponseEntity<>(updateApplication, HttpStatus.OK);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<EmployeeLeave> updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
//        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
//        if (employeeLeaveData.isPresent()) {
//            EmployeeLeave _employeeLeave = employeeLeaveData.get();
//            _employeeLeave.setSupervisorApproval(employeeLeave.getSupervisorApproval());
//            _employeeLeave.setSupervisorReasonForRejection(employeeLeave.getSupervisorReasonForRejection());
//            _employeeLeave.setDirectorApproval(employeeLeave.getDirectorApproval());
//            _employeeLeave.setDirectorReasonForRejection(employeeLeave.getDirectorReasonForRejection());
//            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Application> deleteApplication(@PathVariable("id")Long id){
        applicationService.deleteApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

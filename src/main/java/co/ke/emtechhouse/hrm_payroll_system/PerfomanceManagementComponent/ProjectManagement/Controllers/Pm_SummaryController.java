package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Controllers;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Summary;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services.Pm_SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/summary")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_SummaryController {
    @Autowired
    private Pm_SummaryService pm_summaryService;

    @PostMapping("/add")
    public ResponseEntity<Pm_Summary> addPm_Summary(@RequestBody Pm_Summary pm_summary){
        Pm_Summary newPm_Summary = pm_summaryService.addPm_Summary(pm_summary);
        return  new ResponseEntity<>(newPm_Summary, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_Summary>> getAllPm_Summarys () {
        List<Pm_Summary> pm_summarys = pm_summaryService.findAllPm_Summarys();
        return  new ResponseEntity<>(pm_summarys, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_Summary> getPm_SummaryById (@PathVariable("id") Long id){
        Pm_Summary pm_summary = pm_summaryService.findPm_SummaryById(id);
        return new ResponseEntity<>(pm_summary, HttpStatus.OK);
    }
    //    @PutMapping("/update/{id}")
//    public ResponseEntity<Pm_Summary> updatePm_Summary(@PathVariable("id") long id, @RequestBody Pm_Summary pm_summary){
//        Optional<Pm_Summary> pm_summaryData = pm_summaryRepo.findPm_SummaryById(id);
//
//        if (pm_summaryData.isPresent()) {
//            Pm_Summary _pm_summary = pm_summaryData.get();
//            _pm_summary.setPm_SummaryName(pm_summary.getPm_SummaryName());
//            _pm_summary.setHeadOfPm_Summary(pm_summary.getHeadOfPm_Summary());
//            _pm_summary.setDirectorOfPm_Summary(pm_summary.getDirectorOfPm_Summary());
//            _pm_summary.setPm_SummaryMail(pm_summary.getPm_SummaryMail());
//            _pm_summary.setStatus(pm_summary.getStatus());
//            _pm_summary.setDeleted(pm_summary.getDeleted());
//            return new ResponseEntity<>(pm_summaryRepo.save(_pm_summary), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pm_summaryService.deletePm_Summary(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

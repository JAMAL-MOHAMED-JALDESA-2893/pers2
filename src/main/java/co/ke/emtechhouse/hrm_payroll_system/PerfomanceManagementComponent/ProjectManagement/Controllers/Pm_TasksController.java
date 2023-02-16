package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Controllers;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Tasks;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services.Pm_TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/tasks")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_TasksController {
    @Autowired
    private Pm_TasksService pm_tasksService;

    @PostMapping("/add")
    public ResponseEntity<Pm_Tasks> addPm_Tasks(@RequestBody Pm_Tasks pm_tasks){
        Pm_Tasks newPm_Tasks = pm_tasksService.addPm_Tasks(pm_tasks);
        return  new ResponseEntity<>(newPm_Tasks, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_Tasks>> getAllPm_Taskss () {
        List<Pm_Tasks> pm_taskss = pm_tasksService.findAllPm_Taskss();
        return  new ResponseEntity<>(pm_taskss, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getTaskById (@PathVariable("id") Long id){
        Pm_Tasks task =  pm_tasksService.findPm_TasksById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping("/find/by/employee/{id}")
    public ResponseEntity<?> getPm_TasksByEmployeeId (@PathVariable("id") Long id){
        List<Pm_Tasks> pm_tasks = pm_tasksService.findPm_TasksByEmployeeId(id);
        return new ResponseEntity<>(pm_tasks, HttpStatus.OK);
    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<Pm_Tasks> updatePm_Tasks(@PathVariable("id") long id, @RequestBody Pm_Tasks pm_tasks){
//        Optional<Pm_Tasks> pm_tasksData = pm_tasksRepo.findPm_TasksById(id);
//
//        if (pm_tasksData.isPresent()) {
//            Pm_Tasks _pm_tasks = pm_tasksData.get();
//            _pm_tasks.setPm_TasksName(pm_tasks.getPm_TasksName());
//            _pm_tasks.setHeadOfPm_Tasks(pm_tasks.getHeadOfPm_Tasks());
//            _pm_tasks.setDirectorOfPm_Tasks(pm_tasks.getDirectorOfPm_Tasks());
//            _pm_tasks.setPm_TasksMail(pm_tasks.getPm_TasksMail());
//            _pm_tasks.setStatus(pm_tasks.getStatus());
//            _pm_tasks.setDeleted(pm_tasks.getDeleted());
//            return new ResponseEntity<>(pm_tasksRepo.save(_pm_tasks), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pm_tasksService.deletePm_Tasks(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

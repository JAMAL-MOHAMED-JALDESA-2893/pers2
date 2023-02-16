package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Controllers;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Parameters;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_ParametersRepo;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services.Pm_ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/parameters")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_ParametersController {
    @Autowired
    private Pm_ParametersService pm_parametersService;
    @Autowired
    private Pm_ParametersRepo pm_parametersRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addPm_Parameters(@RequestBody Pm_Parameters pm_parameters){
//       check if already created
//        check if the three values is more than the range of 0 to 100
        Float max_target = pm_parameters.getMax_target();
        Float ave_target = pm_parameters.getAve_target();
        Float min_target = pm_parameters.getMin_target();
        if (max_target<0 | max_target > 100 | ave_target < 0 | ave_target > 100 | min_target < 0 | max_target > 100 | min_target > max_target | min_target > ave_target ){
            return ResponseEntity.badRequest().body(new MessageResponse("Kindly check your target values respectively! Consider percentage range (0-100).Every Value should correspond to its designated value! Check and proceed"));
        }
        pm_parameters.setMax_target(max_target/100);
        pm_parameters.setAve_target(ave_target/100);
        pm_parameters.setMin_target(min_target/100);
        Pm_Parameters newPm_Parameters = pm_parametersService.addPm_Parameters(pm_parameters);
        return  new ResponseEntity<>(newPm_Parameters, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_Parameters>> getAllPm_Parameterss () {
        List<Pm_Parameters> pm_parameterss = pm_parametersService.findAllPm_Parameterss();
        return  new ResponseEntity<>(pm_parameterss, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_Parameters> getPm_ParametersById (@PathVariable("id") Long id){
        Pm_Parameters pm_parameters = pm_parametersService.findPm_ParametersById(id);
        return new ResponseEntity<>(pm_parameters, HttpStatus.OK);
    }
    @GetMapping("/find/employee/{employee_id}")
    public ResponseEntity<List<Pm_Parameters>>  findByEmployeeId(@PathVariable("employee_id") Long employee_id){
        List<Pm_Parameters> pm_parameters = pm_parametersRepo. findByEmployeeId(employee_id);
        return new ResponseEntity<>(pm_parameters, HttpStatus.OK);
    }

        @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePm_Parameters(@PathVariable("id") long id, @RequestBody Pm_Parameters pm_parameters){
        Optional<Pm_Parameters> pm_parametersData = pm_parametersRepo.findPm_ParametersById(id);
        if (pm_parametersData.isPresent()) {
            Pm_Parameters _pm_parameters = pm_parametersData.get();
            Float max_target = pm_parameters.getMax_target();
            Float ave_target = pm_parameters.getAve_target();
            Float min_target = pm_parameters.getMin_target();
            if (max_target<0 | max_target > 100 | ave_target < 0 | ave_target > 100 | min_target < 0 | max_target > 100 | min_target > max_target | min_target > ave_target ){
                return ResponseEntity.badRequest().body(new MessageResponse("Kindly check your target values respectively! Consider percentage range (0-100).Every Value should correspond to its designated value! Check and proceed"));
            }
            _pm_parameters.setParameter_name(pm_parameters.getParameter_name());
            _pm_parameters.setMeasurement_type(pm_parameters.getMeasurement_type());
            _pm_parameters.setMax_target(max_target/100);
            _pm_parameters.setAve_target(ave_target/100);
            _pm_parameters.setMin_target(min_target/100);
            _pm_parameters.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(pm_parametersRepo.save(_pm_parameters), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pm_parametersService.deletePm_Parameters(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

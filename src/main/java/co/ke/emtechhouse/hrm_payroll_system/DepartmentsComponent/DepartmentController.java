package co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/v1/departments/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class DepartmentController {

    @Autowired
    DepartmentRepo departmentRepo;

    private  final  DepartmentService departmentService;

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, EmployeeRepository employeeRepository) {

        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }
    @PostMapping("/add")
    public  ResponseEntity<Department> addDepartment(@RequestBody Department department){
        try {
            Department newDepartment = departmentService.addDepartment(department);
            return  new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments () {
        try {
            List<Department> departments = departmentService.findAllDepartments();
            return  new ResponseEntity<>(departments, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Department> getDepartmentById (@PathVariable("id") Long id){
        try {
            Department department = departmentService.findDepartmentById(id);
            return new ResponseEntity<>(department, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") long id, @RequestBody Department department){
        try {
            Optional<Department> departmentData = departmentRepo.findDepartmentById(id);
            if (departmentData.isPresent()) {
                Department _department = departmentData.get();
                _department.setDepartmentName(department.getDepartmentName());
                _department.setDirectorOfDepartment(department.getDirectorOfDepartment());
                _department.setDepartmentMail(department.getDepartmentMail());
                _department.setStatus(department.getStatus());
                _department.setDeleted(department.getDeleted());
                _department.setUpdatedAt(LocalDateTime.now());
                return new ResponseEntity<>(departmentRepo.save(_department), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") Long id){
        try {
//        Check if department has got employees
            List<Employee> employee = employeeRepository.findByDepartment(id);
            if(employee.isEmpty() ){
//            has no data, remove
                departmentService.deleteDepartment(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
//        has data dont remove
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}
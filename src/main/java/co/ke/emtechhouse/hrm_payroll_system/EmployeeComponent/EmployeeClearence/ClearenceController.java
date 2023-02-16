package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.ResignationController;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.Resignationamt;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.ResignationamtRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.ResignationamtService;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.KraRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.NhifRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.NssfRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveService;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType.LeaveTypeService;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/clearence")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class ClearenceController {
    private final ClearenceRepo clearenceRepo;
    private final ClearenceService clearenceService;
    private final ResignationController resignationController;
    private final ResignationamtRepo resignationamtRepo;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeService leaveTypeService;
    private final EmployeeLeaveService employeeLeaveService;
    private final NhifRepo nhifRepo;
    private final NssfRepo nssfRepo;
    private final KraRepo kraRepo;
    private final ResignationamtService resignationamtService;
    private final PayrollRepo payrollRepo;
    public ClearenceController(ClearenceRepo clearenceRepo, ClearenceService clearenceService, ResignationController resignationController, ResignationamtRepo resignationamtRepo, EmployeeRepository employeeRepository, LeaveTypeService leaveTypeService, EmployeeLeaveService employeeLeaveService, NhifRepo nhifRepo, NssfRepo nssfRepo, KraRepo kraRepo,  ResignationamtService resignationamtService, PayrollRepo payrollRepo) {
        this.clearenceRepo = clearenceRepo;
        this.clearenceService = clearenceService;
        this.resignationController = resignationController;
        this.resignationamtRepo = resignationamtRepo;
        this.employeeRepository = employeeRepository;
        this.leaveTypeService = leaveTypeService;
        this.employeeLeaveService = employeeLeaveService;
        this.nhifRepo = nhifRepo;
        this.nssfRepo = nssfRepo;
        this.kraRepo = kraRepo;
        this.resignationamtService = resignationamtService;
        this.payrollRepo = payrollRepo;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addClearence(@RequestBody Clearence clearence){
        System.out.println("In add clearance");
        System.out.println(clearence);
//        Check if employee has already made a Resignation Requests.
        Long employee_id = clearence.getEmployee_id();
        Optional<Clearence> _clearence =  clearenceRepo.findClearenceByEmployeeId(employee_id);
        if(_clearence.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("You have a pending Resignation!"));
        }
//                check if the date is more than 30  days then allow
        LocalDateTime exiting_date = clearence.getExit_date();
        LocalDateTime current_date = LocalDateTime.now();
        long days_difference  = ChronoUnit.DAYS.between( current_date,exiting_date);
        System.out.println(days_difference);
        if(days_difference < 30){
            return ResponseEntity.badRequest().body(new MessageResponse("You exit date is not enough for a 1 month resignation notice!"));
        }else{
            System.out.println("About to save clearance");
//          TODO: Remove the salary for that month from the salary table if the salary has not been commited and
//           recalculate the salary from the first of that month to the exit date
//          TODO: If the salary has been commited, the system should calculate the employee next payouts from the
//           1st of the next month.
//            Long employee_id = clearence.getEmployee_id();
            Clearence newClearence = clearenceService.addClearence(clearence);
//                    TODO: Call to insert amount to resignation Table
//            LocalDateTime exit_date = clearence.getExit_date();
            LocalDateTime monitor_from_date = current_date.plusMonths(1).withDayOfMonth(1);
            clearence.setMonitor_from_date(monitor_from_date);
            System.out.println(clearence);
//            resignationController.addResignationAmount(employee_id,monitor_from_date,exit_date);
            return new ResponseEntity<>(newClearence, HttpStatus.CREATED);

//            String month = current_date.getMonth().toString();
//            String year = Integer.toString(current_date.getYear());
//            Optional<Salary> emp_salary = salaryRepository.checkIfSalaryHasBeenGeneratedCommitedForEmployeeYearMonth(employee_id,year,month);
//            Optional<Payroll>
//            if (emp_salary.isPresent()){
////                check if commited or not
//                Salary _current_salary = emp_salary.get();
//                if (_current_salary.getIs_commited()){
////                    Commited Salary
//                    LocalDateTime monitor_from_date = current_date.plusMonths(1).withDayOfMonth(1);
//                    clearence.setMonitor_from_date(monitor_from_date);
//                    Clearence newClearence = clearenceService.addClearence(clearence);
////                    TODO: Call to insert amount to resignation Table
//                    LocalDateTime exit_date = clearence.getExit_date();
//                    resignationController.addResignationAmount(employee_id,monitor_from_date,exit_date);
//                        return new ResponseEntity<>(newClearence, HttpStatus.CREATED);
//                }else{
////                    NOT Commited salary
////                    REMOVE From the payroll
//                    Long salary_id = _current_salary.getId();
//                    salaryRepository.deleteById(salary_id);
//                    System.out.println("Employee Salary for this month has been removed from this month payroll!");
//                    LocalDateTime monitor_from_date = LocalDateTime.now();
//                    clearence.setMonitor_from_date(monitor_from_date);
//                    Clearence newClearence = clearenceService.addClearence(clearence);
////                    TODO: Call to insert amount to resignation Table
//                    LocalDateTime exit_date = clearence.getExit_date();
//                    resignationController.addResignationAmount(employee_id,monitor_from_date,exit_date);
//                    return new ResponseEntity<>(newClearence, HttpStatus.CREATED);
//                }
//            }else {
////                No salary Generated
//                LocalDateTime monitor_from_date = current_date.plusMonths(1).withDayOfMonth(1);
//                clearence.setMonitor_from_date(monitor_from_date);
//                Clearence newClearence = clearenceService.addClearence(clearence);
////                    TODO: Call to insert amount to resignation Table
//                LocalDateTime exit_date = clearence.getExit_date();
//                resignationController.addResignationAmount(employee_id,monitor_from_date,exit_date);
//                return new ResponseEntity<>(newClearence, HttpStatus.CREATED);
//            }
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Clearence>> getAllClearences () {
        List<Clearence> clearences = clearenceService.findAllClearence();
        return  new ResponseEntity<>(clearences, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Clearence> getClearenceById (@PathVariable("id") Long id){
        Clearence clearence = clearenceService.findClearenceById(id);
        return new ResponseEntity<>(clearence, HttpStatus.OK);
    }
    @GetMapping("/find/detailed/clearence/{employee_id}")
    public ResponseEntity<?> getClearenceDetailByEmployeeId (@PathVariable("employee_id") Long employee_id){
        ClearenceRepo.ClearenceDetail clearence = clearenceRepo.findClearenceDetailedByEmployeeId(employee_id);
        return new ResponseEntity<>(clearence, HttpStatus.OK);
    }

    @GetMapping("/find/detailed/clearence/all")
    public ResponseEntity<?> getallClearenceDetail (){
        List<ClearenceRepo.ClearenceDetail> clearence = clearenceRepo.findAllClearenceDetailed();
        return new ResponseEntity<>(clearence, HttpStatus.OK);
    }
    @GetMapping("/find/detailed/clearence/all/by/department/{department_id}")
    public ResponseEntity<?> getallClearenceDetailByDepartment(@PathVariable("department_id") Long department_id){
        List<ClearenceRepo.ClearenceDetail> clearence = clearenceRepo.findAllClearenceDetailedByDepartment(department_id);
        return new ResponseEntity<>(clearence, HttpStatus.OK);
    }



    @GetMapping("/find/by/department/{department_id}")
    public ResponseEntity<?> findByDepartment (@PathVariable("department_id") Long department_id){
        List<Clearence> clearence = clearenceRepo.findResignationByDepartment(department_id);
        return new ResponseEntity<>(clearence, HttpStatus.OK);
    }

    @GetMapping("/find/by/employee/{employee_id}")
    public ResponseEntity<?> getClearenceByEmployeeId (@PathVariable("employee_id") Long employee_id){
        Optional<Clearence> clearence = clearenceRepo.findClearenceByEmployeeId(employee_id);
        if (clearence.isPresent()){
            return new ResponseEntity<>(clearence, HttpStatus.OK);
        }
        else {
            return ResponseEntity.ok(new MessageResponse("No Resignation request for that particular employee!"));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Clearence> updateClearence(@PathVariable("id") long id, @RequestBody Clearence clearence){
        Optional<Clearence> clearenceData = clearenceRepo.findClearenceById(id);
        if (clearenceData.isPresent()) {
            Clearence _clearence = clearenceData.get();
            _clearence.setReason(clearence.getReason());
            _clearence.setExit_date(clearence.getExit_date());
            return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    supervisor approval
    @PutMapping("/checked/by/supervisor/{id}")
    public ResponseEntity<Clearence> supervisorCheck(@PathVariable("id") long id, @RequestBody Clearence clearence){
        System.out.println("Called!");
        System.out.println(clearence.getClearence_status());
        System.out.println("*********************");
        String status = clearence.getClearence_status();
        if(status.matches("Approved")){
            Optional<Clearence> clearenceData = clearenceRepo.findClearenceById(id);
            if (clearenceData.isPresent()) {
                System.out.println("Approved called");
                Clearence _clearence = clearenceData.get();
                _clearence.setIs_supervisor_approved(true);
                _clearence.setClearence_status("Processing");
                return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            Optional<Clearence> clearenceData = clearenceRepo.findClearenceById(id);
            if (clearenceData.isPresent()) {
                System.out.println("Rejected Called");
                Clearence _clearence = clearenceData.get();
                _clearence.setIs_supervisor_approved(false);;
                _clearence.setClearence_status("Rejected");
                _clearence.setSupervisor_rejection_reason(clearence.getSupervisor_rejection_reason());
                return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

//    hr approval
    @PutMapping("/checked/by/hr/{id}")
    public ResponseEntity<?> hrClearence(@PathVariable("id") long id, @RequestBody Clearence clearence){
        Optional<Clearence> clearenceData = clearenceRepo.findClearenceById(id);
        if (clearenceData.isPresent()) {
            Clearence _clearence = clearenceData.get();
//            Check if supervisor has approve
            if (_clearence.getIs_supervisor_approved()){
                String status = clearence.getClearence_status();
                if(status.matches("Approved")){
                         _clearence.setIs_hr_approved(true);;
                         _clearence.setClearence_status("Processing");
                        return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
                }else {
                        _clearence.setIs_hr_approved(false);;
                        _clearence.setClearence_status("Rejected");
                        _clearence.setHr_rejection_reason(clearence.getHr_rejection_reason());
                        return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
                }
            }else{
                return ResponseEntity.badRequest().body(new MessageResponse("Supervisor has not approved! Kindly you may consult the responsible supervisor"));
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    director approval
    @PutMapping("/checked/by/director/{id}")
    public ResponseEntity<?> directorClearence(@PathVariable("id") long id, @RequestBody Clearence clearence) throws ParseException {
        Optional<Clearence> clearenceData = clearenceRepo.findClearenceById(id);
        if (clearenceData.isPresent()) {
            Clearence _clearence = clearenceData.get();
//            Check if supervisor has approve
            if (_clearence.getIs_supervisor_approved()){
                //            Check if hr has approve
                if (_clearence.getIs_hr_approved()){
                    Boolean status = clearence.getIs_Director_approved();
                    if(status){
                        _clearence.setIs_Director_approved(true);
                        System.out.println("Approved here");
                        Optional<Resignationamt> resignationamt = resignationamtRepo.findResignationByEmployeeId(_clearence.getEmployee_id());
                        if (resignationamt.isPresent()){
                            Resignationamt _resignationamt = resignationamt.get();
                            _resignationamt.setIs_approved(true);
                            _clearence.setClearence_status("Approved");
                            _resignationamt.setUpdated_at(LocalDateTime.now());
                            resignationamtRepo.save(_resignationamt);
                        }else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                        return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
                    }else {
                        _clearence.setIs_Director_approved(false);;
                        _clearence.setClearence_status("Rejected");
                        _clearence.setDirector_rejection_reason(clearence.getDirector_rejection_reason());
                        return new ResponseEntity<>(clearenceRepo.save(_clearence), HttpStatus.OK);
                    }
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("Supervisor has not approved! Kindly you may consult the responsible HR"));
                }

            }else{
                return ResponseEntity.badRequest().body(new MessageResponse("Supervisor has not approved! Kindly you may consult the responsible supervisor"));
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}


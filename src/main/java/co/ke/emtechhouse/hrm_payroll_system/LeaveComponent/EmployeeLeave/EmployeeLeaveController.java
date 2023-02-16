package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType.LeaveType;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/employee/leave")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class EmployeeLeaveController {
    private final EmployeeLeaveService employeeLeaveService;
    @Autowired
    EmployeeLeaveRepo employeeLeaveRepo;
    @Autowired
    LeaveTypeRepo leaveTypeRepo;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;

    public EmployeeLeaveController(EmployeeLeaveService employeeLeaveService) {
        this.employeeLeaveService = employeeLeaveService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addEmployeeLeave(@RequestBody EmployeeLeave employeeLeave)  {
        Long employeeId = employeeLeave.getEmployeeId();
//        Get employee days from their date of registration
        Employee _employee = employeeService.findById(employeeId);
//            EmployeeEntity _employee = employee.get();
        LocalDateTime onboard_on = _employee.getCreated_at();
        LocalDateTime current_date = LocalDateTime.now();
        long days_difference = ChronoUnit.DAYS.between(onboard_on, current_date);
//            Check if employee has got a pending application or on active leave request
        Optional<EmployeeLeave> leaveRequest = employeeLeaveRepo.findActiveApplicationLeaveByEmployeeId(employeeId);
        if(leaveRequest.isPresent()) {
//                Reject Leave
            return ResponseEntity.badRequest().body(new MessageResponse("You have a pending leave applications!"));
        }else {
//                Go For Leave
            //        Get Leave type from employee
            Long leave_type_id = employeeLeave.getLeave_type_id();
//        Find Leave  Type details by Leave type
            Optional<LeaveType> _leaveType = leaveTypeRepo.findLeaveTypeById(leave_type_id);
            if (_leaveType.isPresent()) {
                LeaveType leavetypedetails = _leaveType.get();
                EmployeeLeave _employeeLeave = new EmployeeLeave();
                _employeeLeave.setLeave_type_id(leavetypedetails.getId());
                _employeeLeave.setLeave_type(leavetypedetails.getLeaveType());
                _employeeLeave.setDeduction_percentage(leavetypedetails.getDeductionPercentage());
                _employeeLeave.setAllowed_duration(leavetypedetails.getDuration());
                _employeeLeave.setEmployee_work_days(days_difference);
//            getting from the form
                _employeeLeave.setEmployeeId(employeeId);
                _employeeLeave.setDepartment_id(_employee.getDepartmentId());
                _employeeLeave.setStart_date(employeeLeave.getStart_date());
                _employeeLeave.setEnd_date(employeeLeave.getEnd_date());
                _employeeLeave.setEmployee_reason_for_leave(employeeLeave.getEmployee_reason_for_leave());
                return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.CREATED);
            }
        }

        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeLeave>> getAllEmployees () {
        List<EmployeeLeave> employeesLeaves = employeeLeaveService.findAllEmployeeLeave();
        return  new ResponseEntity<>(employeesLeaves, HttpStatus.OK);
    }
    @GetMapping("/all/active/employee/leave/detail")
    public ResponseEntity<List<EmployeeLeaveRepo.ActiveEmployeeLeave>> findActiveLeaveDetails() {
        List<EmployeeLeaveRepo.ActiveEmployeeLeave> employeesLeaves =  employeeLeaveRepo.findActiveLeaveDetails();
        return  new ResponseEntity<>(employeesLeaves, HttpStatus.OK);
    }

    @GetMapping("/all/active/employee/leave/detail/by/status")
    public ResponseEntity<List<EmployeeLeaveRepo.ActiveEmployeeLeave>> findActiveLeaveDetailsByStatus(@RequestParam String leave_status) {
        List<EmployeeLeaveRepo.ActiveEmployeeLeave> employeesLeaves =  employeeLeaveRepo.findLeaveDetailsByLeaveStatus(leave_status);
        return  new ResponseEntity<>(employeesLeaves, HttpStatus.OK);
    }
    @GetMapping("/all/active/employee/leave/detail/by/employee/and/status")
    public ResponseEntity<List<EmployeeLeaveRepo.ActiveEmployeeLeave>> findActiveLeaveDetailsByStatusAndEmployeeId(@RequestParam Long employee_id, @RequestParam String leave_status) {
        System.out.println(employee_id);
        System.out.println(leave_status);
        List<EmployeeLeaveRepo.ActiveEmployeeLeave> employeesLeaves =  employeeLeaveRepo.findLeaveDetailsByLeaveStatusAndEmployeeId(employee_id, leave_status);
        return  new ResponseEntity<>(employeesLeaves, HttpStatus.OK);
    }

//    findLeaveDetailsByStatus


    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeLeave> getEmployeeById (@PathVariable("id") Long id){
        EmployeeLeave employee = employeeLeaveService.findEmployeeLeaveById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/find/active/leaves/by/department/{department_id}")
    public ResponseEntity<?> findActiveLeaveDetailsByDepartment(@PathVariable("department_id") Long department_id){
        List<EmployeeLeaveRepo.ActiveEmployeeLeave> employee = employeeLeaveRepo.findActiveLeaveDetailsByDepartment(department_id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/find/inactive/leaves/by/department/{department_id}")
    public ResponseEntity<?> findInactiveLeaveDetailsByDepartment(@PathVariable("department_id") Long department_id){
        List<EmployeeLeaveRepo.ActiveEmployeeLeave> employee = employeeLeaveRepo.findInactiveLeaveDetailsByDepartment(department_id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @GetMapping("/find/by/employee/{employee_id}")
    public ResponseEntity<?> getLeaveByEmployeeById (@PathVariable("employee_id") Long id){
        List<EmployeeLeave> employee = employeeLeaveService.getLeaveByEmployeeId(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/employee/update/{id}")
    public ResponseEntity<?> employeeUpdateEmployee(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
        if (employeeLeaveData.isPresent()) {
            EmployeeLeave newData = employeeLeaveData.get();
            //        Get Leave type from employee
            Long leave_type_id = employeeLeave.getLeave_type_id();
//        Find Leave  Type details by Leave type
            Optional<LeaveType> _leaveType = leaveTypeRepo.findLeaveTypeById(leave_type_id);
            if (_leaveType.isPresent()) {
                if (employeeLeaveData.get().getStatus().matches("Generated")){
                    LeaveType leavetypedetails = _leaveType.get();
                    EmployeeLeave _employeeLeave = new EmployeeLeave();
                    newData.setLeave_type_id(leavetypedetails.getId());
                    newData.setLeave_type(leavetypedetails.getLeaveType());
                    newData.setDeduction_percentage(leavetypedetails.getDeductionPercentage());
                    newData.setAllowed_duration(leavetypedetails.getDuration());
//                _employeeLeave.setEmployee_work_days(days_difference);
//            getting from the form
//                _employeeLeave.setEmployeeId(employeeId);
//                _employeeLeave.setDepartment_id(_employee.getDepartmentId());
                    newData.setStart_date(employeeLeave.getStart_date());
                    newData.setEnd_date(employeeLeave.getEnd_date());
                    newData.setEmployee_reason_for_leave(employeeLeave.getEmployee_reason_for_leave());
                    newData.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(employeeLeaveRepo.save(newData), HttpStatus.OK);
                }else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Your leave request is already in process!"));
                }
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        System.out.println("Got called");
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findById(id);
        if (employeeLeaveData.isPresent()) {

//            Get Status
            if (employeeLeaveData.get().getStatus().matches("Generated")){
                EmployeeLeave _employeeLeave = employeeLeaveData.get();
                _employeeLeave.setDirector_approval(employeeLeave.getDirector_approval());
                _employeeLeave.setHr_approval(employeeLeave.getHr_approval());
                _employeeLeave.setSupervisor_approval(employeeLeave.getSupervisor_approval());
                _employeeLeave.setRejection_reason(employeeLeave.getRejection_reason());
                _employeeLeave.setIs_application_open(employeeLeave.getIs_application_open());
                _employeeLeave.setOn_leave(employeeLeave.getOn_leave());
                _employeeLeave.setStatus(employeeLeave.getStatus());
                _employeeLeave.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Your leave request is already processed!"));
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/director/approval/{id}")
    public ResponseEntity<EmployeeLeave> directorApproval(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
        if (employeeLeaveData.isPresent()) {
            EmployeeLeave _employeeLeave = employeeLeaveData.get();
            _employeeLeave.setDirector_approval(employeeLeave.getDirector_approval());
            _employeeLeave.setDirector_reason_for_rejection(employeeLeave.getDirector_reason_for_rejection());
            _employeeLeave.setStatus(employeeLeave.getStatus());
            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/hr/close/leave/{id}")
    public ResponseEntity<EmployeeLeave> closeEmployeeLeave(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
        if (employeeLeaveData.isPresent()) {
            EmployeeLeave _employeeLeave = employeeLeaveData.get();
            _employeeLeave.setStatus("Completed");
            _employeeLeave.setIs_application_open(false);
            _employeeLeave.setOn_leave(false);
            _employeeLeave.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/supervisor/approval/{id}")
    public ResponseEntity<EmployeeLeave> supervisorApproval(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
        if (employeeLeaveData.isPresent()) {
            EmployeeLeave _employeeLeave = employeeLeaveData.get();
            _employeeLeave.setSupervisor_approval(employeeLeave.getSupervisor_approval());
            _employeeLeave.setSupervisor_reason_for_rejection(employeeLeave.getSupervisor_reason_for_rejection());
            _employeeLeave.setStatus(employeeLeave.getStatus());
            _employeeLeave.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/hr/approval/{id}")
    public ResponseEntity<EmployeeLeave> hrApproval(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
        if (employeeLeaveData.isPresent()) {
            EmployeeLeave _employeeLeave = employeeLeaveData.get();
            _employeeLeave.setSupervisor_approval(employeeLeave.getSupervisor_approval());
            _employeeLeave.setHr_approval(employeeLeave.getHr_approval());
            _employeeLeave.setHr_reason_for_rejection(employeeLeave.getHr_reason_for_rejection());
            _employeeLeave.setDirector_approval(employeeLeave.getDirector_approval());
            _employeeLeave.setSupervisor_reason_for_rejection(employeeLeave.getSupervisor_reason_for_rejection());
            _employeeLeave.setStatus(employeeLeave.getStatus());
            _employeeLeave.setOn_leave(employeeLeave.getOn_leave());
            _employeeLeave.setRejection_reason(employeeLeave.getRejection_reason());
            _employeeLeave.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//
//    @PutMapping("/director/update/{id}")
//    public ResponseEntity<EmployeeLeave> directorUpdateEmployee(@PathVariable("id") long id, @RequestBody EmployeeLeave employeeLeave){
//        Optional<EmployeeLeave> employeeLeaveData = employeeLeaveRepo.findEmployeeLeaveById(id);
//        if (employeeLeaveData.isPresent()) {
//            EmployeeLeave _employeeLeave = employeeLeaveData.get();
//            _employeeLeave.setDirectorApproval(employeeLeave.getDirectorApproval());
//            _employeeLeave.setDirectorReasonForRejection(employeeLeave.getDirectorReasonForRejection());
//            return new ResponseEntity<>(employeeLeaveRepo.save(_employeeLeave), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        System.out.println(id);
//        check if leave is beyond generated, then reject
        Optional<EmployeeLeave> _employeeLeave = employeeLeaveRepo.findById(id);
        if (_employeeLeave.isPresent()){
            EmployeeLeave employeeLeave = _employeeLeave.get();
            if(!employeeLeave.getStatus().matches("Generated")){
                return ResponseEntity.badRequest().body(new MessageResponse("Your leave request is already in process!"));
            }else{
                employeeLeaveRepo.deleteById(id);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

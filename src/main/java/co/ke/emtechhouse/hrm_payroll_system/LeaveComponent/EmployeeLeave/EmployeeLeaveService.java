package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave;

import co.ke.emtechhouse.hrm_payroll_system._exception.EmployeeLeaveNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeLeaveService {
    private final EmployeeLeaveRepo employeeLeaveRepo;
    @Autowired
    public EmployeeLeaveService(EmployeeLeaveRepo employeeLeaveRepo) {
        this.employeeLeaveRepo = employeeLeaveRepo;
    }

    public EmployeeLeave addEmpployeeLeave(EmployeeLeave employeeLeave){
        return employeeLeaveRepo.save(employeeLeave);
    }
    public List<EmployeeLeave> findAllEmployeeLeave(){
        return employeeLeaveRepo.findAll();
    }
    public EmployeeLeave findEmployeeLeaveById(Long id){
        return employeeLeaveRepo.findEmployeeLeaveById(id).orElseThrow(()-> new EmployeeLeaveNotFoundException("Employee " + id +"was not found"));
    }
    public EmployeeLeave findLeaveByEmployeeId(Long employee_id){
        return employeeLeaveRepo.findEmployeeLeaveById(employee_id).orElseThrow(()-> new EmployeeLeaveNotFoundException("Employee " + employee_id +"was not found"));
    }

    public List<EmployeeLeave> getLeaveByEmployeeId(Long employee_id){
        return employeeLeaveRepo.getLeaveByEmployeeId(employee_id);
    }

    public EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave){
        return employeeLeaveRepo.save(employeeLeave);
    }
    public void deleteEmployeeLeave(Long id){
        employeeLeaveRepo.deleteEmployeeLeaveById(id);
    }

}

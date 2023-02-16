package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeave;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class Leavedeductions {
    private final EmployeeLeaveRepo employeeLeaveRepo;

    public Leavedeductions(EmployeeLeaveRepo employeeLeaveRepo) {
        this.employeeLeaveRepo = employeeLeaveRepo;
    }

    public Double getNhifdeduction(Long emp_no, Double salary){
        Double percentageDeduction = 1.0;
        Double leaveDeduction = 0.00;
        try {
            Optional<EmployeeLeave> employeeLeaves = employeeLeaveRepo.findEmployeeOPenLeaves(emp_no);
            if (employeeLeaves.isPresent()){
//                get deduction percentage
                EmployeeLeave leaveDetails = employeeLeaves.get();
                Double deduction_rate = leaveDetails.getDeduction_percentage();
                percentageDeduction = deduction_rate/100;
                leaveDeduction = percentageDeduction * salary;
                return  leaveDeduction;
            }
        }catch (Exception e) {
            log.info("Leave Deductions Error {} "+e);
        }
        return leaveDeduction;
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary.AdvanceSalary;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary.AdvanceSalaryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class Advancedeductions {
    private final AdvanceSalaryRepo advanceSalaryRepo;

    public Advancedeductions(AdvanceSalaryRepo advanceSalaryRepo) {
        this.advanceSalaryRepo = advanceSalaryRepo;
    }

    public Double getAdvancededuction(Long emp_no) {
        Double advanceDeductions = 0.00;
        try {
            Optional<AdvanceSalary> employeeAdvance = advanceSalaryRepo.findPaidOpenedAdvanceSalaryByEmployeeId(emp_no);
            if (employeeAdvance.isPresent()){
                AdvanceSalary currentAdvance = employeeAdvance.get();
                advanceDeductions = currentAdvance.getSalary_amount();
                currentAdvance.setIs_advance_salary_closed(true);
                advanceSalaryRepo.save(currentAdvance);
                return  advanceDeductions;
//            AdvanceSalary newState = new
//            newState.setIs_advance_salary_closed(true);
            }else {
                advanceDeductions = 0.00;
                return  advanceDeductions;
            }
        } catch (Exception e) {
            log.info("Advancedeductions Error {} "+e);
        }
        return advanceDeductions;
    }
    }

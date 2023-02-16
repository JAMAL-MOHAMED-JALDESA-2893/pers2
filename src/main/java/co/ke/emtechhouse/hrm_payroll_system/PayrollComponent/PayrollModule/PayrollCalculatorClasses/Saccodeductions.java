package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.Sacco;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Slf4j
@Component
public class Saccodeductions {
    private final EmployeeRepository employeeRepository;
    private final SaccoRepo saccoRepo;

    public Saccodeductions(EmployeeRepository employeeRepository, SaccoRepo saccoRepo) {
        this.employeeRepository = employeeRepository;
        this.saccoRepo = saccoRepo;
    }

    public Double getSaccodeduction(Long emp_no, Double netPay) {
        Double sacco_deductions = 0.00;
        try {
            Optional<Employee> employee = employeeRepository.findEmployeeById(emp_no);
            Boolean is_enrolled =   employee.get().getIs_sacco_enrolled();
            if (is_enrolled){
//            deduct the amount based on his percentage and add to sacco wallet
                Double sacco_deduction_percentage = employee.get().getSacco_deduction_percentage();
                sacco_deductions = netPay * sacco_deduction_percentage/100;
                //            save deductions to sacco wallet
                Sacco _sacco= new Sacco();
                LocalDate currentDate= LocalDate.now();
                Month monthDate = currentDate.getMonth();
                String nowMonth = monthDate.toString();
                // Get year from date
                int yearDate = currentDate.getYear();
                _sacco.setEmployee_id(emp_no);
                _sacco.setDepartment_id(employee.get().getDepartmentId());
                _sacco.setContribution_amount(sacco_deductions);
                _sacco.setEnrolled_on(employee.get().getEnrolled_on());
                _sacco.setMonth(nowMonth );
                _sacco.setYear(yearDate);
                saccoRepo.save(_sacco);
                return sacco_deductions;
            }
        }catch (Exception e) {
            log.info("Sacco Deductions Error {} "+e);
        }
        return sacco_deductions;
    }
    }

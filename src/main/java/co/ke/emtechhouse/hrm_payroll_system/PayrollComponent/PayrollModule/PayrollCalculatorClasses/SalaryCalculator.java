package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SalaryCalculator {
    @Autowired
    EmployeeRepository employeeRepo;


    //    TODO: Get employees basic salary
    public Double getEmployeeBasicSalary(Long emp_no){
        Double basic_salary=0.0;
        try {
            Optional<Employee> employee=employeeRepo.findEmployeeById(emp_no);
            if(employee.isPresent()){
                Employee emp =employee.get();
                basic_salary=emp.getBasic_salary();
            }

        }catch (Exception e){
            log.info("Salary Error {} "+e);
        }
        return basic_salary;
    }

    //    TODO: Calculate gross salary
    public Double calculateGrossSalary(Double basic_salary, Double taxable_non_cash_benefits, Double taxable_allowances){
        System.out.println("Got Called for calculating gross salary");
        Double grossSalary = 0.00;
        try {
            grossSalary=basic_salary+taxable_non_cash_benefits+taxable_allowances;
        }catch (Exception e){
            log.info("Salary Error {} "+e);
        }
        return grossSalary;
    }
    //    TODO: Calculate gross salary without non cash benefits
    public Double calculateGrossSalaryWithoutNonCashBenefits(Double basic_salary, Double taxable_allowances){
        System.out.println("Got Called for calculating gross salary");
        Double grossSalary = 0.00;
        try {
            grossSalary=basic_salary+taxable_allowances;
        }catch (Exception e){
            log.info("Salary Error {} "+e);
        }
        return grossSalary;
    }
    //    TODO: Calculate taxable salary
    public Double calculateTaxableSalary(Double grossSalary,Double mortgageInterest, Double pensionContribution){
        System.out.println("Got Called for calculating gross salary");
        Double taxableSalary = 0.00;
        try {
            taxableSalary=grossSalary-mortgageInterest-pensionContribution;
        }catch (Exception e){
            log.info("Salary Error {} "+e);
        }
        return taxableSalary;
    }
    //    TODO: Calculate net salary
    public Double calculateNetSalaryAfterTax(Double taxableIncome, Double paye, Double insuranceRelief){
        System.out.println("Got Called for calculating gross salary");
        Double netSalary = 0.00;
        try {
            netSalary= taxableIncome-paye+insuranceRelief;
        }catch (Exception e){
            log.info("Salary Error {} "+e);
        }
        return netSalary;
    }


}

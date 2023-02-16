package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent.Deduction;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent.DeductionRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses.*;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdvanceSalaryService {
    @Autowired
    AdvanceSalaryRepo advanceSalaryRepo;
    @Autowired
    Payedeductions payedeductions;
    @Autowired
    SalaryCalculator salaryCalculator;
    @Autowired
    NonCashBenefitsCalculator nonCashBenefitsCalculator;
    @Autowired
    Allowancesdeductions allowancesdeductions;
    @Autowired
    Nssfdeductions nssfdeductions;

    @Autowired
    DeductionRepo deductionRepo;

    public AdvanceSalary addAdvanceSalary(AdvanceSalary advanceSalary){
        return advanceSalaryRepo.save(advanceSalary);
    }
    public List<AdvanceSalaryRepo.AdvanceSalaryDetail> findAllAdvanceSalarys(){
        return advanceSalaryRepo.findAllAdvancedSalaryDetail();
    }
    public List<AdvanceSalaryRepo.AdvanceSalaryDetail> findAllDepartmentAdvancedSalaryDetail(Long department_id){
        return advanceSalaryRepo.findAllDepartmentAdvancedSalaryDetail(department_id);
    }
    public AdvanceSalary findAdvanceSalaryById(Long id){
        return advanceSalaryRepo.findAdvanceSalaryById(id).orElseThrow(()-> new DataNotFoundException("AdvanceSalary " + id +"was not found"));
    }
    public List<AdvanceSalary> findAdvanceSalaryByEmployeeId(Long employee_id){
        return advanceSalaryRepo.findAdvanceSalaryByEmployeeId(employee_id);
    }


    //    public List<AdvanceSalary> findAdvanceSalaryByUserId(String user_id){
//
//        return advanceSalaryRepo.findByUserId(user_id);
//    }
    public AdvanceSalary updateAdvanceSalary(AdvanceSalary advanceSalary){

        return advanceSalaryRepo.save(advanceSalary);
    }
    public void deleteAdvanceSalary(Long id){
        advanceSalaryRepo.deleteById(id);
    }

    //TODO: Get employee net salary after paye
    public Double employeeSalaryAfterPayeDeduction(Long emp_no){
        Double netpayAfterTaxDeductionsSalary=0.0;
        try{
            Double basic_salary=salaryCalculator.getEmployeeBasicSalary(emp_no);
            Double nssfDeductions = nssfdeductions.getNssfdeduction(basic_salary);
            Double taxableDeductionsAllowance = allowancesdeductions.getTaxableAllowancededuction1(emp_no);
            Double taxableNonCashBenefits=nonCashBenefitsCalculator.getTaxableNonCashBenefits(emp_no);
            Double gross_salary= salaryCalculator.calculateGrossSalary(basic_salary,taxableNonCashBenefits,taxableDeductionsAllowance);
            Double payeDeductions = payedeductions.getPayededuction(0.0,basic_salary,gross_salary,0.0);
            Double taxableIncome= salaryCalculator.calculateTaxableSalary(gross_salary,0.0, nssfDeductions);
            Double taxRelief=payedeductions.getPersonalRelief();
            netpayAfterTaxDeductionsSalary= salaryCalculator.calculateNetSalaryAfterTax(taxableIncome, payeDeductions, 0.0);

        }catch (Exception e){
            log.info("Error {} "+e);
        }
        return netpayAfterTaxDeductionsSalary;
    }

    //TODO: Adding disbursed advance salary to deductions component
    public Deduction addAdvanceSalaryDeduction(AdvanceSalary advanceSalary){
        try{
            Deduction deduction=new Deduction();
            deduction.setEmployee_id(advanceSalary.getEmployee_id());
            deduction.setDepartment_id(advanceSalary.getDepartment_id());
            deduction.setAmount(advanceSalary.getSalary_amount());
            deduction.setDeduction_for("Advance Salary");
            deduction.setIs_deductable(true);
            deduction.setIs_approved(true);
            deduction.setIs_active(true);
            deduction.setStart_period_m(advanceSalary.getMonth());
            deduction.setStart_period_y(advanceSalary.getYear());
            deduction.setEnd_period_m(advanceSalary.getMonth());
            deduction.setEnd_period_y(advanceSalary.getYear());
            deduction.setRelationship_id(advanceSalary.getId());
            deductionRepo.save(deduction);
            return deduction;
        }catch (Exception e){
            log.info("Error {} "+e);
            return null;
        }
    }
}


package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent.Deduction;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent.DeductionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DeductionCalculator {
    @Autowired
    private DeductionRepo deductionRepo;

    //    TODO: 1.Get  active deductions
    public Double getAllActiveDeductions(Long empId){
        System.out.println("Got Called for deductions");
        Double activeDeductions = 0.00;
        try {
            List<Deduction> deductionList =deductionRepo.findActiveDeductionsByEmployeeId(empId);
            if(deductionList.size()>0){
                for(int i=0;i<deductionList.size();i++){
                    Deduction deduction=deductionList.get(i);
                    activeDeductions=activeDeductions+deduction.getAmount();
                }

            }
        }catch (Exception e){
            //
        }
        return activeDeductions;

    }
}

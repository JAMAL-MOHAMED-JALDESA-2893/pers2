package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_custom;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_customRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class Allowancesdeductions {
    private final Allowance_customRepo allowance_customRepo;

    public Allowancesdeductions(Allowance_customRepo allowance_customRepo) {
        this.allowance_customRepo = allowance_customRepo;
    }

    //    TODO: 1.Get  Taxable Allowances
    public Double getTaxableAllowancededuction(Long emp_no){
        System.out.println("Got Called for Taxable allowances");
        Double taxableAllowance = 0.00;
        try {
            Optional<Allowance_custom> allowance_custom = allowance_customRepo.findPayableCustomAllowanceByEmployeeId(emp_no);
            if (allowance_custom.isPresent()){
                Allowance_custom allowanceCustomData = allowance_custom.get();
                Boolean taxable = allowanceCustomData.getIs_taxable();
                if (taxable){
//                add to gross salary
                    taxableAllowance = allowanceCustomData.getAmount();
//                Close the allowances
                    allowanceCustomData.setIs_payable(false);
                    allowance_customRepo.save(allowanceCustomData);
                }else{
                    taxableAllowance = 0.00;
                }
            }
        } catch (Exception e) {
            log.info("Taxable Allowances Error {} "+e);
        }
        return taxableAllowance;
    }

    public Double getTaxableAllowancededuction1(Long emp_no){
        System.out.println("Got Called for Taxable allowances");
        Double taxableAllowance = 0.00;
        try {
            List<Allowance_custom> allowance_custom = allowance_customRepo.findPayableCustomAllowanceByEmployeeId1(emp_no);
            if (allowance_custom.size()>0){
                for(int i=0;i<allowance_custom.size(); i++){
                    Allowance_custom allowanceCustomData = allowance_custom.get(i);
                    Boolean taxable = allowanceCustomData.getIs_taxable();
                    if (taxable){
                        taxableAllowance=taxableAllowance+allowanceCustomData.getAmount();
                        allowanceCustomData.setIs_payable(false);
                        allowance_customRepo.save(allowanceCustomData);
                    }
                }
            }
        } catch (Exception e) {
            log.info("Taxable Allowances Error {} "+e);
        }
        return taxableAllowance;
    }

    //    TODO: 1.Get Non Taxable Allowances
    public Double getNonTaxableAllowancededuction(Long emp_no){
        System.out.println("Got Called for Non Taxable allowances");

        Double nonTaxableAllowance = 0.00;
        try {
            Optional<Allowance_custom> allowance_custom = allowance_customRepo.findPayableCustomAllowanceByEmployeeId(emp_no);
            if (allowance_custom.isPresent()){
                Allowance_custom allowanceCustomData = allowance_custom.get();
                Boolean taxable = allowanceCustomData.getIs_taxable();
                if (!taxable){
//                add to gross salary
                    nonTaxableAllowance = allowanceCustomData.getAmount();
//                Close the allowances
                    allowanceCustomData.setIs_payable(false);
                    allowance_customRepo.save(allowanceCustomData);
                    log.info("************Non allowances closed*************");
                }else{
                    nonTaxableAllowance = 0.00;
                }
            }
        } catch (Exception e) {
            log.info("Non Taxable Allowances Error {} "+e);
        }
        return nonTaxableAllowance;
    }

    public Double getNonTaxableAllowancededuction1(Long emp_no){
        System.out.println("Got Called for Non Taxable allowances");

        Double nonTaxableAllowance = 0.00;
        try {
            List<Allowance_custom> allowance_custom = allowance_customRepo.findPayableCustomAllowanceByEmployeeId1(emp_no);
            if (allowance_custom.size()>0){
                for (int i=0; i<allowance_custom.size();i++){
                    Allowance_custom allowanceCustomData = allowance_custom.get(i);
                    Boolean taxable = allowanceCustomData.getIs_taxable();
                    if (!taxable){
//                        add to gross salary
                        nonTaxableAllowance = nonTaxableAllowance+allowanceCustomData.getAmount();
//                        Close the allowances
                        allowanceCustomData.setIs_payable(false);
                        allowance_customRepo.save(allowanceCustomData);
                        log.info("************Non allowances closed*************");
                    }else{
                        nonTaxableAllowance = 0.00;
                    }
                }
            }
        } catch (Exception e) {
            log.info("Non Taxable Allowances Error {} "+e);
        }
        return nonTaxableAllowance;
    }
}

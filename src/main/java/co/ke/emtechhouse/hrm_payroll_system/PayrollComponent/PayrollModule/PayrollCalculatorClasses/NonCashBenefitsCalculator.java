package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits.Benefit_non_cash_custom;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits.Benefit_non_cash_customRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NonCashBenefitsCalculator {
    private final Benefit_non_cash_customRepo benefit_non_cash_customRepo;

    public NonCashBenefitsCalculator(Benefit_non_cash_customRepo benefit_non_cash_customRepo) {
        this.benefit_non_cash_customRepo = benefit_non_cash_customRepo;
    }

    //    TODO: 1.Get Payable Taxable Non cash benefits
    public Double getTaxableNonCashBenefits(Long emp_no){
        System.out.println("Got Called for Taxable non cash benefits");
        Double taxableNonCashBenefits = 0.00;
        try {
            List<Benefit_non_cash_custom> payableTaxableBenefits= benefit_non_cash_customRepo.findPayableCustomNonCashBenefitsByEmployeeId(emp_no);
//            TODO: Check if there are any
            if(payableTaxableBenefits.size()>0){
                for(int i=0;i<payableTaxableBenefits.size(); i++){
                    Benefit_non_cash_custom benefitNonCash= payableTaxableBenefits.get(i);
                    Boolean taxable = benefitNonCash.getIs_taxable();
                    if(taxable){
                        taxableNonCashBenefits=taxableNonCashBenefits+benefitNonCash.getMonthly_valuation();

//                        benefitNonCash.setIs_payable(false);
//                        benefit_non_cash_customRepo.save(benefitNonCash);
                    }
                }
            }
        }catch (Exception e){
            log.info("Non cash benefit Error {} "+e);
        }
        return taxableNonCashBenefits;
    }
}

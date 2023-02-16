package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NetpayAfterTaxDeductions {

    public Double getNetpayAfterTaxDeductions(
            Double taxablePayDeductions,
            Double nonTaxableDeductionsAllowance,
            Double advanceSalary,
            Double saccoDeductions
    ) {
        Double afterTaxPayDeductions = 0.00;
        try {
            afterTaxPayDeductions = taxablePayDeductions + nonTaxableDeductionsAllowance - advanceSalary - saccoDeductions;
            return afterTaxPayDeductions;
        } catch (Exception e) {
            log.info("NetpayAfterTaxDeductions Error {} "+e);
        }
        return afterTaxPayDeductions;
}
}

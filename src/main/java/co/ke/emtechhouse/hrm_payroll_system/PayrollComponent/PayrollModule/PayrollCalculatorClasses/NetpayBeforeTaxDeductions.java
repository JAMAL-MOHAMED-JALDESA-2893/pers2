package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NetpayBeforeTaxDeductions {
    public Double getNetpayBeforeTaxDeductions(
            Double salary,
            Double nhifDeductions,
            Double payeDeductions,
            Double leaveDeductions
    ) {
        Double taxablePayDeductions = 0.00;
        try {
            taxablePayDeductions = (salary - nhifDeductions - payeDeductions - leaveDeductions);
            return taxablePayDeductions;
        } catch (Exception e) {
            log.info("NetpayBeforeTaxDeductions Error {} "+e);
        }
        return taxablePayDeductions;
    }
}

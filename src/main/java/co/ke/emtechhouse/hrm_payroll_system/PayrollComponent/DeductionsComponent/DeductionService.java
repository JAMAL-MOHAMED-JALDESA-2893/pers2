package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Slf4j
public class DeductionService {
    @Autowired
    private DeductionRepo deductionRepo;
    public Deduction addDeduction(Deduction deduction) {
        try {
            return deductionRepo.save(deduction);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.NhifRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Nhifdeductions {
    private final NhifRepo nhifRepo;

    public Nhifdeductions(NhifRepo nhifRepo) {
        this.nhifRepo = nhifRepo;
    }

    public Double getNhifdeduction(Double salary){
        double nhifDeduction = 0.00;
        try {
            //            Get Band threshold
            co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.Nhif nhif_max_query = nhifRepo.findHighestThreshold();
            Double highest_threshold = nhif_max_query.getMax_threshold();
            Double Highest_deductions = nhif_max_query.getNhif_deduction();

            if(salary >=0 && salary < highest_threshold){
//                get actual deductions
                co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.Nhif rate_nhif_deductions = nhifRepo.findNhifDeduction(salary);
                nhifDeduction = rate_nhif_deductions.getNhif_deduction();
            }else if (salary>= highest_threshold){
                nhifDeduction =  Highest_deductions;
            }
        }catch (Exception e) {
            log.info("NHIF Deductions Error {} "+e);
        }
        return nhifDeduction;
    }
}

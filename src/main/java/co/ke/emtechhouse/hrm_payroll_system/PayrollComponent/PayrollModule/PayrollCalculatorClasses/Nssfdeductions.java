package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.Nssf;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.NssfRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class Nssfdeductions {
    private final NssfRepo nssfRepo;

    public Nssfdeductions(NssfRepo nssfRepo) {
        this.nssfRepo = nssfRepo;
    }

//    public Double getNssfdeduction(Double salary) {
////            360 shillings to a maximum of 1080 shillings
//        double nssfDeductions = 0;
//        try {
//            Optional<Nssf> nssf_data = nssfRepo.findNssf();
//            if (nssf_data.isPresent()){
//                Double min_nssf = nssf_data.get().getMin_nssf();
//                Double max_nssf = nssf_data.get().getMax_nssf();
//                Double nssf_total_rate = nssf_data.get().getTotal_nssf_rate();
//                double nssfRate = nssf_total_rate * salary;
//                if(nssfRate > max_nssf){
//                    nssfDeductions = max_nssf;
//                }else if(nssfRate >= min_nssf && nssfRate < max_nssf ){
//                    nssfDeductions = nssfRate;
//                }else if(nssfRate < min_nssf){
//                    nssfDeductions = min_nssf;
//                }
//            }else{
//                nssfDeductions = 0.00;
//            }
//        }catch (Exception e) {
//            log.info("NSSF Deductions Error {} "+e);
//        }
//        return  nssfDeductions;
//    }

//
    public Double getNssfdeduction(Double salary){
        double nssfDeductions = 0;
        try {
            Optional<Nssf> nssf_data = nssfRepo.findNssfItem(salary);
            if(nssf_data.isPresent()){
                Nssf nssf_datails=nssf_data.get();
                // check if pensionable amount is specified
                //pensionable amount for uel i.e those earning 18000 and obove is always 18000
                if(nssf_datails.getIs_pensionable_earnings_specified()){
                    Double pensionableAmount= nssf_datails.getPensionable_earnings();
                    Double employeePaymentRate=nssf_datails.getEmployee_payment_rate();
                    nssfDeductions= (employeePaymentRate/100)*pensionableAmount;
                }else {
                    Double pensionableAmount= salary;
                    Double employeePaymentRate=nssf_datails.getEmployee_payment_rate();
                    nssfDeductions= (employeePaymentRate/100)*pensionableAmount;
                }
            }
        }catch (Exception e) {
            log.info("NSSF Deductions Error {} "+e);
        }
        return  nssfDeductions;
    }

}

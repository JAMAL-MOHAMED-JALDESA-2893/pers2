package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DcrsCalculator {
    @Autowired
    private Nssfdeductions nssfdeductions;

    //    TODO: 1.Calculate dcrs E1 i.e 30% of the basic salary
    public Double calculateDcrsE1(Double basic_salary){
        System.out.println("calculating dcrs e1");
        Double dcrsE1 = 0.00;
        try {
            dcrsE1=(0.3* basic_salary);
        }catch (Exception e){
            log.info("DCRS Error {} "+e);
        }
        return dcrsE1;
    }

    //    TODO: 1.Calculate dcrs E2 Actual nssf contribution
    public Double calculateDcrsE2(Double basic_salary){
        System.out.println("calculating dcrs e2");
        Double dcrsE2 = 0.00;
        try {
            dcrsE2 = nssfdeductions.getNssfdeduction(basic_salary);
        }catch (Exception e){
            log.info("DCRS Error {} "+e);
        }
        return dcrsE2;
    }

    //    TODO: 1.Calculate dcrs E3 Fixed
    public Double calculateDcrsE3(){
        System.out.println("calculating dcrs e3");
        Double dcrsE3 = 20000.00;
        return dcrsE3;
    }
    //    TODO:  calculate Owner- Occupied Interests
    public Double calculateOwnerOccupiedInterest(){
        System.out.println("calculating Owner- Occupied Interests");
        Double ownerOccupiedInterest = 0.00;
        return ownerOccupiedInterest;
    }

    //    TODO: 1.Calculate Retirement Contribution & Owner Occupied
    public Double calcRitirementContrAndOwnerOccupiedInterest(Double dcrs_e1, Double dcrs_actual_e2, Double dcrs_fixed_e3,Double owner_occupied_interests){
        System.out.println("calculating dcrs e2");
        Double retirement_and_owner_occupied_interests = 0.00;
        try {
            Double smallest_dcrs = Math.min(dcrs_e1, Math.min(dcrs_actual_e2, dcrs_fixed_e3));
            retirement_and_owner_occupied_interests = smallest_dcrs + owner_occupied_interests;
        }catch (Exception e){
            log.info("DCRS Error {} "+e);
        }
        return retirement_and_owner_occupied_interests;
    }

}

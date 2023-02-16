package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
@Slf4j
public class Benefit_non_cash_customService {
    @Autowired
    private Benefit_non_cash_customRepo benefit_noncash_customRepo;

    public Benefit_non_cash_custom addBenefit_custom(Benefit_non_cash_custom benefit_noncash_custom) {
        try {
            return benefit_noncash_customRepo.save(benefit_noncash_custom);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public List<Benefit_non_cash_custom> getBenefit_custom() {
        try {
            return benefit_noncash_customRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public Benefit_non_cash_custom getBenefit_customById(Long id) {
        try {
            return benefit_noncash_customRepo.findById(id).orElse(null);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public Benefit_non_cash_custom updateBenefit_custom(Benefit_non_cash_custom benefit_noncash_custom) {
        try {
            return benefit_noncash_customRepo.save(benefit_noncash_custom);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    //TODO: deactivate non cash benefit
    public void deactivateNonCashBenefit(Long id){
        try {
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom=benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                Benefit_non_cash_custom newBenefit_non_cash_custom=benefit_non_cash_custom.get();
                newBenefit_non_cash_custom.setIs_payable(false);
                benefit_noncash_customRepo.save(newBenefit_non_cash_custom);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }

    //TODO: get all benefits by different approval status from director, hr ,supervisor
    public List<Benefit_non_cash_custom> findNonCashBenefitsByApprovalStatus(String supervisorApproval, String hrApproval, String directorApproval) {
        try {
            return benefit_noncash_customRepo.findNonCashBenefitsByApprovalStatus(supervisorApproval,hrApproval,directorApproval);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    //TODO: get all benefits by supervisor approval status
    public List<Benefit_non_cash_custom> findNonCashBenefitsBySupervisorApprovalStatus(String supervisorApproval) {
        try {
            return benefit_noncash_customRepo.findNonCashBenefitsBySupervisorApprovalStatus(supervisorApproval);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    //TODO: get all benefits by hr status
    public List<Benefit_non_cash_custom> findNonCashBenefitsByHRApprovalStatus(String hrApproval) {
        try {
            return benefit_noncash_customRepo.findNonCashBenefitsByHRApprovalStatus(hrApproval);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    //TODO: get all benefits by director approval status
    public List<Benefit_non_cash_custom> findNonCashBenefitsByDirectorApprovalStatus(String directorApproval) {
        try {
            return benefit_noncash_customRepo.findNonCashBenefitsByHRApprovalStatus(directorApproval);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

}

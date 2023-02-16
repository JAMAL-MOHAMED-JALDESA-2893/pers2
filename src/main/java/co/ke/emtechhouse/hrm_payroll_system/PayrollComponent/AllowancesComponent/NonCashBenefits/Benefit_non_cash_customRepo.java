package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Benefit_non_cash_customRepo extends JpaRepository<Benefit_non_cash_custom, Long> {
    @Query(value = "SELECT * FROM `benefit_non_cash_custom` WHERE `benefit_non_cash_custom`.employee_id=:employee_id AND `benefit_non_cash_custom`.is_payable=true", nativeQuery = true)
    List<Benefit_non_cash_custom> findPayableCustomNonCashBenefitsByEmployeeId(Long employee_id);

    @Query(value = "SELECT * FROM `benefit_non_cash_custom` WHERE `is_supervisor_approved`=:supervisorApproval AND`is_hrm_approved`=:hrApproval AND `is_director_approved`=:directorApproval", nativeQuery = true)
    List<Benefit_non_cash_custom> findNonCashBenefitsByApprovalStatus(String supervisorApproval, String hrApproval, String directorApproval);

    @Query(value = "SELECT * FROM `benefit_non_cash_custom` WHERE `is_supervisor_approved`=:supervisorApproval", nativeQuery = true)
    List<Benefit_non_cash_custom> findNonCashBenefitsBySupervisorApprovalStatus(String supervisorApproval);

    @Query(value = "SELECT * FROM `benefit_non_cash_custom` WHERE `is_hrm_approved`=:hrApproval", nativeQuery = true)
    List<Benefit_non_cash_custom> findNonCashBenefitsByHRApprovalStatus(String hrApproval);

    @Query(value = "SELECT * FROM `benefit_non_cash_custom` WHERE `is_director_approved`=:directorApproval", nativeQuery = true)
    List<Benefit_non_cash_custom> findNonCashBenefitsByDirectorApprovalStatus(String directorApproval);

    //used for deactivating all benefits that are due
//    @Query(value = "UPDATE `benefit_non_cash_custom` SET `is_payable`=false WHERE `id` IN(SELECT id FROM `benefit_non_cash_custom` WHERE `end_date`<=CURDATE() OR `is_director_approved`=false AND `is_payable`=true)", nativeQuery = true)
//    void deactivateAllPayableExpiredAndNotApprovedBenefits();
}

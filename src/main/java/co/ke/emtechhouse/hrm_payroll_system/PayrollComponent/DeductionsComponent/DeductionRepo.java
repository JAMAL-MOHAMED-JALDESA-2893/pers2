package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeductionRepo extends JpaRepository<Deduction, Long> {
    @Query(value = "SELECT * FROM `deduction` WHERE `is_active`=true AND `employee_id`=:employee_id", nativeQuery = true)
    List<Deduction> findActiveDeductionsByEmployeeId(Long employee_id);
}

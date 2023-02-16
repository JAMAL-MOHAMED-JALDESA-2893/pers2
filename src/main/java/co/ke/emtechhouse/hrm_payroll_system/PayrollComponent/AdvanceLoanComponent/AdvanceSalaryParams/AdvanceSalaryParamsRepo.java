package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalaryParams;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvanceSalaryParamsRepo extends JpaRepository<AdvanceSalaryParams,Long> {
    Optional<AdvanceSalaryParams> findAdvanceSalaryParamsById(Long id);
    void deletePm_DeliverablesById(Long id);
//    @Query(value = "SELECT * FROM document WHERE document.filename LIKE :filename", nativeQuery = true)
//    Optional<Document> findByFilename(String filename);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);

    void deleteParameterById(Long id);
}

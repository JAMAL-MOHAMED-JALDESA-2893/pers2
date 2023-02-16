package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Deliverables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Pm_DeliverablesRepo extends JpaRepository<Pm_Deliverables,Long> {
    Optional<Pm_Deliverables> findPm_DeliverablesById(Long id);
    void deletePm_DeliverablesById(Long id);
//    @Query(value = "SELECT * FROM document WHERE document.filename LIKE :filename", nativeQuery = true)
//    Optional<Document> findByFilename(String filename);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);
}

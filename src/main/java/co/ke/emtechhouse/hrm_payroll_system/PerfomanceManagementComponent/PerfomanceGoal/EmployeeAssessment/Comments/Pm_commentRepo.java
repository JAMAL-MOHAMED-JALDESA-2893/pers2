package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Pm_commentRepo extends JpaRepository<Pm_comment,Long> {
    Optional<Pm_comment> findCommentById(Long id);
    void deleteCommentById(Long id);
//    @Query(value = "SELECT * FROM document WHERE document.filename LIKE :filename", nativeQuery = true)
//    Optional<Document> findByFilename(String filename);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);
}
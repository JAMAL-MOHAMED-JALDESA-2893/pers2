package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Pm_TasksRepo extends JpaRepository<Pm_Tasks,Long> {
    Optional<Pm_Tasks> findPm_TasksById(Long id);
    void deletePm_TasksById(Long id);

    @Query(value = "SELECT * FROM `pm_tasks` WHERE pm_tasks.employee_id = :id", nativeQuery = true)
    List<Pm_Tasks> findPm_TasksByEmployeeId(Long id);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);
}

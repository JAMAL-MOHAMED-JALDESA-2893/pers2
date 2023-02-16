package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Pm_ParametersRepo  extends JpaRepository<Pm_Parameters,Long> {
    Optional<Pm_Parameters> findPm_ParametersById(Long id);
    void deletePm_DeliverablesById(Long id);
    @Query(value = "SELECT pm_parameters.* FROM  pm_parameters LEFT JOIN pm_goal on pm_parameters.id = pm_goal.parameter_id WHERE pm_goal.employee_id =:employee_id", nativeQuery = true)
    List<Pm_Parameters> findByEmployeeId(Long employee_id);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);
}
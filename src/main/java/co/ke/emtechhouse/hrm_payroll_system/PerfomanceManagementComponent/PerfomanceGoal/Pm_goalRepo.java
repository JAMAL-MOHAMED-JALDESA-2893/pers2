package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Pm_goalRepo extends JpaRepository<Pm_goal,Long> {
    Optional<Pm_goal> findPm_goalById(Long id);
    void deleteEmployee_assessmentById(Long id);
//    @Query(value = "SELECT * FROM document WHERE document.filename LIKE :filename", nativeQuery = true)
//    Optional<Document> findByFilename(String filename);
//
    @Query(value = "SELECT pm_goal.id AS Id, pm_goal.goal AS Goal, pm_goal.month AS  Month,pm_goal.review_enabled AS Reviewenabled, pm_goal.year AS Year, pm_goal.next_month_review_delay AS Next_month_review_delay, pm_goal.status AS STATUS, pm_goal.created_at AS Created_at, pm_goal.updated_at AS Updated_at, pm_goal.next_review_date AS next_review_date, pm_parameters.parameter_name AS Parameter_name, pm_parameters.measurement_type as Measurement_type FROM pm_goal LEFT JOIN pm_parameters ON pm_goal.parameter_id = pm_parameters.id  WHERE pm_goal.employee_id=:employee_id", nativeQuery = true)
    List<GoalParameter> findByEmployeeId(Long employee_id);
    public interface GoalParameter{
        Long getId();
        Long getEmployee_id();
        String getGoal();
        String getMonth();
        Integer getYear();
        Integer getNext_month_review_delay();
        String getStatus();
        Boolean getReviewenabled();
        LocalDateTime getCreated_at();
        LocalDateTime getUpdated_at();
        LocalDateTime getNext_review_date();
        String getParameter_name();
        String getMeasurement_type();
        Double getPerfomance();
    }
    @Query(value = "SELECT pm_goal.id AS Id,pm_goal.employee_id AS Employee_id, pm_goal.goal AS Goal, pm_goal.review_enabled AS Reviewenabled, pm_goal.month AS  Month, pm_goal.year AS Year, pm_goal.next_month_review_delay AS Next_month_review_delay, pm_goal.status AS STATUS, pm_goal.created_at AS Created_at,pm_goal.next_review_date AS next_review_date, pm_goal.updated_at AS Updated_at, pm_parameters.parameter_name AS Parameter_name, pm_parameters.measurement_type as Measurement_type FROM pm_goal LEFT JOIN pm_parameters ON pm_goal.parameter_id = pm_parameters.id  WHERE pm_goal.id=:goal_id", nativeQuery = true)
    Optional<GoalParameter> findByDetailsByGoalId(Long goal_id);

    @Query(value = "SELECT pm_goal.*, SUM(pm_review.percentage_score) AS Perfomance FROM pm_review join pm_goal ON pm_goal.id = pm_review.pm_goal_id WHERE pm_goal.employee_id=:employee_id GROUP BY pm_goal.id  ", nativeQuery = true)
    Optional<GoalParameter> findGoalScoreByEmployeeId(Long employee_id);
}



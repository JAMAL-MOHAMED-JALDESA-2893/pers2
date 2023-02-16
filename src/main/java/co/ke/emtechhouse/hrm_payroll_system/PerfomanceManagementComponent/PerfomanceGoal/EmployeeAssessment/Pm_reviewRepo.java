package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Pm_reviewRepo extends JpaRepository<Pm_review,Long> {
    Optional<Pm_review> findPm_reviewById(Long id);
    void deleteEmployee_assessmentById(Long id);
//    @Query(value = "SELECT * FROM document WHERE document.filename LIKE :filename", nativeQuery = true)
//    Optional<Document> findByFilename(String filename);
//
    @Query(value = "SELECT * FROM pm_review WHERE pm_review.employee_id =:employee_id AND pm_review.pm_goal_id =:goal_id ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Pm_review> findLastReviewByEmployeeGoalId(Long employee_id, Long goal_id);

    @Query(value = "SELECT * FROM pm_review WHERE pm_review.employee_id =:employee_id ORDER BY pm_review.id", nativeQuery = true)
    List<Pm_review> findReviewByEmployeeId(Long employee_id);

    @Query(value = "SELECT *, AVG(pm_review.percentage_score) as Balancescore, count(*) from pm_review WHERE pm_review.employee_id =:employee_id GROUP BY pm_review.parameter_id;", nativeQuery = true)
    List<Pm_review> findAverageReviewByEmployeeId(@Param("employee_id") Long employee_id);

//    Fetch Financial Service
    @Query(value = "SELECT * FROM pm_review WHERE pm_review.parameter_id=:parameter_id AND pm_review.employee_id =:employee_id AND pm_review.is_reviewed = true, ORDER BY pm_review.id ", nativeQuery = true)
    List<Pm_review> findReviewByParameterandEmployeeId(Long parameter_id, Long employee_id);
//    Fetch Customer Service
//    Fetch Learning and Growth
//    Fetch

    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, AVG(pm_review.percentage_score) as Balancescore, count(*) from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true GROUP BY pm_review.employee_id ORDER BY pm_review.percentage_score DESC", nativeQuery = true)
    List<Employeereview> rankAllEmployees();
    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, AVG(pm_review.percentage_score) as Balancescore, count(*) from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true GROUP BY pm_review.employee_id ORDER BY pm_review.percentage_score DESC LIMIT 1", nativeQuery = true)
    Employeereview bestEmployee();
    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, AVG(pm_review.percentage_score) as Balancescore, count(*) from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true GROUP BY pm_review.employee_id ORDER BY pm_review.percentage_score DESC LIMIT 3", nativeQuery = true)
    List<Employeereview> top3Employees();
    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, AVG(pm_review.percentage_score) as Balancescore, count(*) from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true GROUP BY pm_review.employee_id ORDER BY pm_review.percentage_score ASC LIMIT 3", nativeQuery = true)
    List<Employeereview> last3Employees();
    @Query(value = "SELECT COUNT(*) FROM pm_review WHERE pm_review.is_reviewed = true", nativeQuery = true)
    List<Employeereview> countAllGoals();
    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, pm_review.* from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true AND pm_review.employee_id=:employee_id", nativeQuery = true)
    List<Employeereview> findByEmployeeId(@Param("employee_id") Long employee_id);
    @Query(value = "SELECT employee.id AS Employeeid, employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, employee.occupation AS Occupation, department.department_name AS Departmentname, AVG(pm_review.percentage_score) as Balancescore, count(*) from employee JOIN department on department.id = employee.department_id JOIN pm_review on pm_review.employee_id = employee.id WHERE pm_review.is_reviewed = true AND pm_review.employee_id=:employee_id", nativeQuery = true)
    Employeereview sumByEmployeeId(@Param("employee_id") Long employee_id);
    @Query(value = "SELECT pm_review.month, pm_review.year, AVG(pm_review.percentage_score) AS employeeperfomance FROM pm_review WHERE employee_id =:employee_id AND pm_review.is_reviewed = true GROUP by pm_review.month", nativeQuery = true)
    List<employeeAverageReview> findAvgPmReviewByEmployeeId(@Param("employee_id") Long employee_id);

    @Query(value = "SELECT pm_review.month, pm_review.year, AVG(pm_review.percentage_score) AS employeeperfomance FROM pm_review WHERE employee_id=:employee_id AND pm_review.year=:year AND pm_review.is_reviewed = true GROUP by pm_review.month", nativeQuery = true)
    List<employeeAverageReview> findAvgPmReviewByEmployeeIdAndYear(Long employee_id,Integer year);

    @Query(value = "SELECT AVG(pm_review.percentage_score) AS Total_reviews, pm_review.month AS Month FROM `pm_review` WHERE pm_review.year=:year AND pm_review.is_reviewed = true GROUP BY pm_review.month;\n", nativeQuery = true)
    List<employeeAverageReview> findAvgMonthlyPerfomancePerYear(Integer year);

    @Query(value = "SELECT AVG(pm_review.percentage_score) AS Total_reviews, pm_review.month AS Month, pm_review.year AS Year FROM `pm_review` WHERE pm_review.is_reviewed = true GROUP BY pm_review.year\n;", nativeQuery = true)
    List<employeeAverageReview> groupPerfomanceByYear();


    public interface employeeAverageReview{
        String getMonth();
        Integer getYear();
        Double getEmployeeperfomance();
        Double getTotal_reviews();
    }

    public interface Employeereview{
        Long getId();
        Long getEmployeeid();
        String getFirstname();
        String getMiddlename();
        String getLastname();
        String getOccupation();
        String getDepartmentname();
        String getBalancescore();
        String getMonth();
        Integer getYear();
        Double getEmployeeperfomance();
    }

}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdvanceSalaryRepo extends JpaRepository<AdvanceSalary,Long> {
    Optional<AdvanceSalary> findAdvanceSalaryById(Long id);


    @Query(value = "SELECT * FROM advance_salary WHERE advance_salary.employee_id =:employee_id", nativeQuery = true)
    List<AdvanceSalary> findAdvanceSalaryByEmployeeId(Long employee_id);
    void deletePm_DeliverablesById(Long id);

//    @Query(value = "SELECT employee_entity.first_name AS FirstName, employee_entity.middle_name AS MiddleName, employee_entity.last_name AS LastName, department.department_name AS  DepartmentName, advance_salary.* FROM employee_entity JOIN department ON employee_entity.department_id = department.id JOIN advance_salary on employee_entity.id = advance_salary.employee_id", nativeQuery = true)
//    List<AdvanceSalaryDetail> findAllAdvancedSalaryDetail();
    @Query(value = "SELECT emp.first_name AS FirstName, emp.middle_name AS MiddleName, emp.last_name AS LastName, department.department_name AS  DepartmentName, advance_salary.* FROM employee emp JOIN department ON emp.department_id = department.id JOIN advance_salary on emp.id = advance_salary.employee_id", nativeQuery = true)
    List<AdvanceSalaryDetail> findAllAdvancedSalaryDetail();

    @Query(value = "SELECT emp.first_name AS FirstName, emp.middle_name AS MiddleName, emp.last_name AS LastName, department.department_name AS  DepartmentName, advance_salary.* FROM employee emp JOIN department ON emp.department_id = department.id JOIN advance_salary on emp.id = advance_salary.employee_id WHERE emp.department_id=:department_id", nativeQuery = true)
    List<AdvanceSalaryDetail> findAllDepartmentAdvancedSalaryDetail(Long department_id);

    @Query(value = "SELECT * FROM advance_salary WHERE advance_salary.employee_id =:employee_id AND advance_salary.month =:month AND advance_salary.year=:year", nativeQuery = true)
    Optional<AdvanceSalary> findEmployeeAdvancedSalaryMonthly(Long employee_id, String month, Integer year);

    @Query(value = "SELECT emp.first_name AS FirstName, emp.middle_name AS MiddleName, emp.last_name AS LastName, department.department_name AS  DepartmentName, advance_salary.* FROM employee emp JOIN department ON emp.department_id = department.id JOIN advance_salary on emp.id = advance_salary.employee_id WHERE advance_salary.is_advance_salary_closed = false;", nativeQuery = true)
    List<AdvanceSalaryDetail> findAllOpenAdvancedSalary();

    @Query(value = "SELECT * FROM `advance_salary` WHERE advance_salary.employee_id =:employee_id AND advance_salary.is_advance_salary_closed=false AND advance_salary.is_paid=true;", nativeQuery = true)
    Optional<AdvanceSalary> findPaidOpenedAdvanceSalaryByEmployeeId(Long employee_id);

    public interface AdvanceSalaryDetail{
        Long getId();
        Long getEmployee_id();
        Long getDepartment_id();
        String getUser_identity();
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getDepartmentName();
        Double getSalary_amount();
        String getMonth();
        Integer getYear();
        String getStatus();
        Boolean getIs_advance_salary_closed();
        Boolean getIs_deleted();
        Boolean getIs_processing();
        Boolean getIs_approved();
        Boolean getIs_disbursed();
        Boolean getIs_executive();
        LocalDateTime getCreated_at();
        LocalDateTime getUpdated_at() ;
        LocalDateTime getDeleted_at();
    }

    @Query(value = "SELECT * FROM advance_salary WHERE advance_salary.employee_id =:employee_id AND advance_salary.is_advance_salary_closed = false", nativeQuery = true)
    Optional<AdvanceSalary> findOpenAdvancedSalary(Long employee_id);
//
//    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
//    List<Document> findByUserId(String user_id);
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Allowance_customRepo extends JpaRepository<Allowance_custom,Long> {
    Optional<Allowance_custom> findAllowance_customById(Long id);
    void deleteAllowance_customById(Long id);

    @Query(value = "SELECT a.`id`, a.`allowance_for`, a.`amount`, a.`created_at`, a.`deleted_at`, a.`department_id`,d.department_name, a.`employee_id`, e.first_name,e.last_name, a.`end_period_m`, a.`end_period_y`, a.`is_deleted`, a.`is_director_approved`, a.`is_hrm_approved`, a.`is_paid`, a.`is_payable`, a.`is_supervisor_approved`, a.`is_taxable`, a.`rejection_reason_director`, a.`start_period_m`, a.`start_period_y`, a.`updated_at` FROM `allowance_custom` a JOIN employee e ON a.employee_id=e.id JOIN department d ON a.department_id=d.id", nativeQuery = true)
    List<Allowance_custom_detail> findAllowanceCustomDetail();
    @Query(value = "SELECT * FROM allowance_custom WHERE allowance_custom.employee_id=:employee_id AND allowance_custom.is_payable=true", nativeQuery = true)
    Optional<Allowance_custom> findPayableCustomAllowanceByEmployeeId(Long employee_id);

    @Query(value = "SELECT * FROM allowance_custom WHERE allowance_custom.employee_id=1 AND allowance_custom.is_payable=true", nativeQuery = true)
    List<Allowance_custom> findPayableCustomAllowanceByEmployeeId1(Long employee_id);

//    SELECT employee_entity.first_name AS Firstname, employee_entity.middle_name AS Middlename, employee_entity.last_name AS Lastname, department.department_name AS Departmentname, allowance_custom.* FROM allowance_custom LEFT JOIN employee_entity ON allowance_custom.employee_id = employee_entity.id LEFT JOIN department on employee_entity.department_id = department.id
    @Query(value = "SELECT employee.first_name AS Firstname, employee.middle_name AS Middlename, employee.last_name AS Lastname, department.department_name AS Departmentname, allowance_custom.* FROM allowance_custom LEFT JOIN employee ON allowance_custom.employee_id = employee.id LEFT JOIN department on employee.department_id = department.id", nativeQuery = true)
    List<Allowance_detail> findAllowanceDetailed();
    interface Allowance_detail{
        Long getId();
        Long getEmployee_id();
        Long getDepartment_id();
        String getUser_identity();
        String getFirstname();
        String getMiddlename();
        String getLastname();
        String getDepartmentname();
        Double getAmount();
        String getAllowance_for();
        String getMonth();
        String getYear();

        Boolean getIs_taxable();
        Boolean getIs_paid();
        String  getIs_director_approved();
        String  getIs_supervisor_approvved();
        String  getIs_hrm_approved();
        Boolean getIs_payable();

        Boolean getIs_deleted();
        LocalDateTime getCreated_at();
        LocalDateTime getUpdated_at();
        LocalDateTime getDeleted_at();
    }
}


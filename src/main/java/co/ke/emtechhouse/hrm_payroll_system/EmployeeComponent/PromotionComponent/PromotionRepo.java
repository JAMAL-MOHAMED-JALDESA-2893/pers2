package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionRepo extends JpaRepository<Promotion,Long> {
    Optional<Promotion> findPromotionById(Long id);
    void deletePromotionById(Long id);

//    @Query(value = "SELECT employee_entity.id AS EmployeeId, employee_entity.first_name AS FirstName, employee_entity.middle_name AS MiddleName, employee_entity.last_name as LastName, employee_entity.personal_phone AS PersonalPhone, department.department_name, promotion.* from employee_entity JOIN department on department.id = employee_entity.department_id JOIN promotion on promotion.employee_id = employee_entity.id", nativeQuery = true)
//    List<Promotion> findAllPromotionData();

    @Query(value = "SELECT employee_entity.id AS EmployeeId, employee_entity.first_name AS FirstName, employee_entity.middle_name AS MiddleName, employee_entity.last_name as LastName, employee_entity.personal_phone AS PersonalPhone, department.department_name AS DepartmentName, promotion.id AS Id, promotion.prev_basic_pay as PrevBasicPay, promotion.new_basic_pay AS NewBasicPay, promotion.prev_position AS PrevPosition, promotion.new_position AS NewPosition, promotion.reason AS Reason, promotion.hrm_approved AS HrmApproved, promotion.supervisor_approved AS SupervisorApproved, promotion.director_approved AS DirectorApproved, promotion.updated_at as PromotedAt from employee_entity JOIN department on department.id = employee_entity.department_id JOIN promotion on promotion.employee_id = employee_entity.id", nativeQuery = true)
    List<TotalDeductions> findAllPromotionsData();

    @Query(value = "SELECT employee_entity.id AS EmployeeId, employee_entity.first_name AS FirstName, employee_entity.middle_name AS MiddleName, employee_entity.last_name as LastName, employee_entity.personal_phone AS PersonalPhone, department.department_name AS DepartmentName, promotion.id AS Id, promotion.prev_basic_pay as PrevBasicPay, promotion.new_basic_pay AS NewBasicPay, promotion.prev_position AS PrevPosition, promotion.new_position AS NewPosition, promotion.reason AS Reason, promotion.hrm_approved AS HrmApproved, promotion.supervisor_approved AS SupervisorApproved, promotion.director_approved AS DirectorApproved, promotion.updated_at as PromotedAt from employee_entity JOIN department on department.id = employee_entity.department_id JOIN promotion on promotion.employee_id = employee_entity.id WHERE promotion.status=:status", nativeQuery = true)
    List<TotalDeductions> findAllPromotionsDataPerStatus(String status);

    public interface TotalDeductions{
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getNationalId();
        Double getGrossPay();
        String getJobGroup();

        Long getId();
        Long getEmployeeId();
        String getDepartmentId();
        Double getPrevBasicPay();
        Double getNewBasicPay ();
        String getPrevPosition ();
        String getNewPosition ();
        String getReason ();
        Boolean getDeleted ();
        Boolean getHrmApproved ();
        Boolean getSupervisorApproved ();
        Boolean getDirectorApproved ();
        LocalDateTime getRegistaredOn ();
        LocalDateTime getPromotedAt();
    }

    
//    SELECT employee_entity.id AS EmployeeId, employee_entity.first_name AS FirstName, employee_entity.middle_name AS MiddleName, employee_entity.last_name as LastName, employee_entity.personal_phone AS PersonalPhone, department.department_name, promotion.* from employee_entity JOIN department on department.id = employee_entity.department_id JOIN promotion on promotion.employee_id = employee_entity.id;
}

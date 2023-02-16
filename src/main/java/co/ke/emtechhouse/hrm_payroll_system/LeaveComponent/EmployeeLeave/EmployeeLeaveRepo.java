package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeLeaveRepo extends JpaRepository<EmployeeLeave, Long> {
    Optional<EmployeeLeave> findEmployeeLeaveById(Long id);
    void deleteEmployeeLeaveById(Long id);

    @Query(value = "SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.status =:leave_status LIMIT 7", nativeQuery = true)
    List<EmployeeLeaveRepo.ActiveEmployeeLeave> findLeaveDetailsByLeaveStatus(String leave_status);

    @Query(value = "SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.employee_id=:employee_id and employee_leave.status =:leave_status", nativeQuery = true)
    List<EmployeeLeaveRepo.ActiveEmployeeLeave> findLeaveDetailsByLeaveStatusAndEmployeeId(Long employee_id, String leave_status);

    @Query(value = "SELECT * FROM employee_leave WHERE employee_id =:employee_id", nativeQuery = true)
    List<EmployeeLeave> findLeaveByEmployeeId(Long employee_id);

    @Query(value = "SELECT * FROM employee_leave WHERE employee_id =:employee_id AND employee_leave.is_application_open=true", nativeQuery = true)
    Optional<EmployeeLeave> findActiveApplicationLeaveByEmployeeId(@Param("employee_id") Long employee_id);

    @Query(value = "SELECT * FROM employee_leave WHERE employee_id = :employee_id", nativeQuery = true)
    List<EmployeeLeave> getLeaveByEmployeeId(@Param("employee_id") Long employee_id);
//    Get employee Leave by ID which are Open
    @Query(value = "SELECT * FROM employee_leave WHERE employee_leave.employee_id =:employee_id AND employee_leave.on_leave = true", nativeQuery = true)
    Optional<EmployeeLeave> findEmployeeOPenLeaves(Long employee_id);

    @Query(value = "SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.department_id=:department_id\n", nativeQuery = true)
    List<ActiveEmployeeLeave> findLeaveDetailsByDepartment(Long department_id);

//    Active Employee Leave Per Department
@Query(value = " SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.on_leave = 'true' and employee_leave.department_id=:department_id\n", nativeQuery = true)
List<ActiveEmployeeLeave> findActiveLeaveDetailsByDepartment(Long department_id);
//    Closed Employee Leaves per department
@Query(value = " SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.on_leave = 'false' and employee_leave.department_id=:department_id\n", nativeQuery = true)
List<ActiveEmployeeLeave> findInactiveLeaveDetailsByDepartment(Long department_id);

    @Query(value = "SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id WHERE employee_leave.status =:status;\n", nativeQuery = true)
    List<ActiveEmployeeLeave> findLeaveDetailsByStatus(String status);


    @Query(value = "SELECT employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name AS LastName, department.department_name AS DepartmentName, employee_leave.* FROM employee JOIN department ON employee.department_id = department.id JOIN employee_leave on employee.id = employee_leave.employee_id;\n", nativeQuery = true)
    List<ActiveEmployeeLeave> findActiveLeaveDetails();
    public interface ActiveEmployeeLeave{
        Long getEmployee_id();
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getDepartmentName();
        Long getId();
        Long getEmployee_Id();
        Long getDepartment_id();
        Long getLeave_type_id();
        Long getEmployee_work_days();
        String getLeave_type();
        String getAllowed_duration();
        String getStart_date();
        String getEnd_date();
        String getStatus();
        Double getDeduction_percentage();
        String getEmployee_reason_for_leave();
        String getSupervisor_reason_for_rejection();
        String getSupervisor_approval();
        String getHr_reason_for_rejection();
        String getHr_approval();
        String getDirector_approval();
        String getDirector_reason_for_rejection();
        Boolean getIs_executive();
        Boolean getOn_leave();
        LocalDateTime getCreated_at();
        LocalDateTime getUpdated_at();
        LocalDateTime getDeleted_at();
    }
}

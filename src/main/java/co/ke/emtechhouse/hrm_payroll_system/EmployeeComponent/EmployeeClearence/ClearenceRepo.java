package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClearenceRepo extends JpaRepository<Clearence, Long> {
    Optional<Clearence> findClearenceById(Long id);

    @Query(value = "SELECT * FROM clearence WHERE employee_id = :employee_id", nativeQuery = true)
    Optional<Clearence> findClearenceByEmployeeId(Long employee_id);

    @Query(value = "SELECT * FROM clearence WHERE is_cleared = false AND clearence.clearence_status != 'Rejected' ", nativeQuery = true)
    List<Clearence> findUnCleared();
//    Get Supervisor Approval
    @Query(value = "SELECT * FROM clearence WHERE is_cleared = false AND clearence.supervisor_approved = 'Approved' ", nativeQuery = true)
    Optional<Clearence> findSupervisorsApproval();
//    Get HR Aproval
    @Query(value = "SELECT * FROM clearence WHERE is_cleared = false AND clearence.hr_approved = 'Approved' ", nativeQuery = true)
    Optional<Clearence> findHrApproval();
//    Get Directors Approval
    @Query(value = "SELECT * FROM clearence WHERE is_cleared = false AND clearence.director_approved = 'Approved' ", nativeQuery = true)
    Optional<Clearence> findDirectorsApproval();
    @Query(value = "SELECT * FROM clearence WHERE clearence.department_id=:department_id", nativeQuery = true)
    List<Clearence> findResignationByDepartment(Long department_id);


    @Query(value = " SELECT employee.id AS Employeeid,employee.first_name as Firstname, employee.middle_name as Middlename, employee.last_name as Lastname, employee.occupation as Occupation, employee.created_at as Registeredon,\n" +
            "    department.department_name as Departmentname,\n" +
            "    clearence.*\n" +
            "    from\n" +
            "    department JOIN employee on department.id = employee.department_id\n" +
            "    JOIN clearence on clearence.employee_id = employee.id WHERE clearence.employee_id =:employee_id LIMIT 1", nativeQuery = true)

    ClearenceDetail findClearenceDetailedByEmployeeId(Long employee_id);



    @Query(value = " SELECT employee.id AS Employeeid,employee.first_name as Firstname, employee.middle_name as Middlename, employee.last_name as Lastname, employee.occupation as Occupation, employee.created_at as Registeredon,\n" +
            "    department.department_name as Departmentname,\n" +
            "    clearence.*\n" +
            "    from\n" +
            "    department JOIN employee on department.id = employee.department_id\n" +
            "    JOIN clearence on clearence.employee_id = employee.id", nativeQuery = true)

    List<ClearenceDetail> findAllClearenceDetailed();

    @Query(value = " SELECT employee.id AS Employeeid,employee.first_name as Firstname, employee.middle_name as Middlename, employee.last_name as Lastname, employee.occupation as Occupation, employee.created_at as Registeredon,\n" +
            "    department.department_name as Departmentname,\n" +
            "    clearence.*\n" +
            "    from\n" +
            "    department JOIN employee on department.id = employee.department_id\n" +
            "    JOIN clearence on clearence.employee_id = employee.id WHERE clearence.department_id =:department_id ", nativeQuery = true)
    List<ClearenceDetail> findAllClearenceDetailedByDepartment(Long department_id);




    interface ClearenceDetail{
// Employee Details
        String getFirstname();
        String getMiddlename();
        String getLastname();
        String getOccupation();
//                Department Detail
        String getDepartmentname();


        //      Clearence Table
        Long getId();
        Long getEmployee_id();
        Long getDepartment_id();
        String getReason();
        String getIs_supervisor_approved();
        String getSupervisor_rejection_reason();
        String getIs_hr_approved();
        String getHr_rejection_reason();
        String getIs_Director_approved();
        String getDirector_rejection_reason();
        String getClearence_status();
        String getMonitor_from_date();
        String getExit_date();
        String getSalary();
        String getIs_cleared();
        String getCreated_at();
        String getUpdated_at();
        String getDeleted_at();
    }



    void deleteClearenceById(Long id);

//    Double getNhifTotals();
//    Double getNssfTotals();
//    Double getHelbTotals();
//    Double getLeaveTotals();
//
//    String getFirstname
//    String getMiddlename
//    String getLastname
//    String getOccupation
//    String getDepartmentname

//    String getStatus
//    String getSupervisorapproval
//    String getHrapproval
//    String getDirectorapproval
//    String getCreatedon
//    String getUpdatedon
//


//    Long getId();
//    Long getEmployee_id();
//    Long getDepartment_id();
//    String getReason();
//    String getIs_supervisor_approved();
//    String getSupervisor_rejection_reason();
//    String getIs_hr_approved();
//    String getHr_rejection_reason();
//    String getIs_Director_approved();
//    String getDirector_rejection_reason();
//    String getClearence_status();
//    String getMonitor_from_date();
//    String getExit_date();
//    String getSalary();
//    String getIs_cleared();
//    String getCreated_at();
//    String getUpdated_at();
//    String getDeleted_at();

}

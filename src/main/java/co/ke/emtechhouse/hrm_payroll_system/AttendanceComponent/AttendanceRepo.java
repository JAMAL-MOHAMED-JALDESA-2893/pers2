package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepo  extends JpaRepository<Attendance,Long> {
    Optional<Attendance> findAttendanceById(Long id);
    void deleteAttendanceById(Long id);
    /*
    find by employee id and date
     */
    @Query(value = "SELECT * FROM `attendance` WHERE attendance.employee_id=:employee_id AND attendance.registar_date=:registar_date\n", nativeQuery = true)
   AttendanceDetail findEmployeeAttendance(Long employee_id, LocalDate registar_date);

    @Query(value = "SELECT * FROM `attendance` WHERE attendance.registar_date=:registar_date\n", nativeQuery = true)
    List<Attendance> findEmployeeAttendance(LocalDate registar_date);

    @Query(value = "SELECT employee_entity.id AS Employeeid, employee_entity.first_name AS Firstname, employee_entity.middle_name AS Middlename, employee_entity.last_name AS Lastname, employee_entity.occupation AS Occupation, department.department_name AS Departmentname, attendance.* FROM attendance LEFT JOIN employee_entity ON attendance.employee_id = employee_entity.id LEFT JOIN department ON employee_entity.department_id=department.id WHERE attendance.registar_date=:registar_date\n", nativeQuery = true)
    List<AttendanceDetail> findTodaysAttendance(LocalDate registar_date);

    @Query(value = "SELECT employee_entity.id AS Employeeid, employee_entity.first_name AS Firstname, employee_entity.middle_name AS Middlename, employee_entity.last_name AS Lastname, employee_entity.occupation AS Occupation, department.department_name AS Departmentname, attendance.* FROM attendance LEFT JOIN employee_entity ON attendance.employee_id = employee_entity.id LEFT JOIN department ON employee_entity.department_id=department.id WHERE attendance.attendance_status=:attendance_status and attendance.registar_date=:registar_date\n", nativeQuery = true)
    List<AttendanceDetail> filterByAttendanceStatus(String attendance_status, LocalDate registar_date);

    @Query(value = "SELECT employee_entity.id AS Employeeid, employee_entity.first_name AS Firstname, employee_entity.middle_name AS Middlename, employee_entity.last_name AS Lastname, employee_entity.occupation AS Occupation, department.department_name AS Departmentname, attendance.* FROM attendance LEFT JOIN employee_entity ON attendance.employee_id = employee_entity.id LEFT JOIN department ON employee_entity.department_id=department.id", nativeQuery = true)
    List<AttendanceDetail> findAttendanceDetail();

    @Query(value = "SELECT employee_entity.id AS Employeeid, employee_entity.first_name AS Firstname, employee_entity.middle_name AS Middlename, employee_entity.last_name AS Lastname, employee_entity.occupation AS Occupation, department.department_name AS Departmentname, attendance.* FROM attendance LEFT JOIN employee_entity ON attendance.employee_id = employee_entity.id LEFT JOIN department ON employee_entity.department_id=department.id WHERE attendance.department_id=:department_id", nativeQuery = true)
    List<AttendanceDetail> findAttendanceDetailPerDepartment(Long department_id);



    public  interface  AttendanceDetail{
            Long getId();
            Long getEmployeeid();
            String getFirstname();
            String getMiddlename();
            String getLastname();
            String getOccupation();
            String getDepartmentname();
            String getBalancescore();
            Double getEmployeeperfomance();
            Long getEmployee_id();
            Long getDepartment_id();
            Integer getYear();
            String getMonth();
            LocalDateTime getRegister_on();
            String getDay();
            LocalTime getTime_in();
            LocalTime getIn_late_by();
            LocalTime getIn_early_by();
            String getTime_in_status();
            LocalTime getTime_out();
            LocalTime getOut_late_by();
            LocalTime getOut_early_by();
            String getTime_out_status();
            String getAttendance_status();
            String getPartial_attendance_reason();
            Boolean getIs_supervisor_approved();
            Boolean getIs_hr_approved();
            String getAction_location();
            Boolean getIs_deleted();
            Boolean getIs_holiday();
            String getDay_identity();
            Boolean getIs_weekend();
            LocalDateTime getCreate_at();
            LocalDateTime getUpdated_at();
            LocalDateTime getDeleted_at();
        }
}

package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.stream.Location;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;

    private String day;
    private LocalDateTime register_on;
    private LocalDate registar_date;
    private LocalTime time_in;
    private LocalTime in_late_by;
    private LocalTime in_early_by;
    private String time_in_status;
    private LocalTime time_out;
    private LocalTime out_late_by;
    private LocalTime out_early_by;
    private String time_out_status;
    private String attendance_status = "Absent";
    private String partial_attendance_reason;
    private Boolean is_supervisor_approved = false;
    private Boolean is_hr_approved = false;
    private String action_location;
    private Boolean is_deleted = false;
    private Boolean is_holiday = false;
    private String day_identity = "Work Day";
    private Boolean is_weekend = false;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

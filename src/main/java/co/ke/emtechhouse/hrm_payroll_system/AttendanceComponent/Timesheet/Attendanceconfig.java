package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Attendanceconfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String day;
    private LocalTime open_hours;
    private LocalTime closing_hours;
    private Boolean is_open = true;
    private Boolean is_approved = false;
    private Boolean is_deleted = false;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}


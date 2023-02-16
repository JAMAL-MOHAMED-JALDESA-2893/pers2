package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Holidayconfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String holiday_name;
    private Integer day_of_month;
    private String day_of_week;
    private String month;
    private Integer year;
    private LocalDate holiday_date;
    private LocalDateTime detailed_long_date;

    private Boolean is_approved = false;
    private Boolean is_active = true;
    private Boolean is_deleted = false;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}


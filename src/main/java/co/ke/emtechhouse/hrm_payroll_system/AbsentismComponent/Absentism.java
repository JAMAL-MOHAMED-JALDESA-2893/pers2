package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Absentism {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;
    private String Occupation = "Not provided";
    private String day = "N/A";
    private String month = "Not rovided";
    private Integer year = 2021;
    private LocalDateTime absent_from;
    private LocalDateTime absent_to;
    private Double total_absent_accrued_hours = 0.00;
    private Boolean is_still_absent = true;
    private Boolean deleted = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent;

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
public class Deduction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;
    private Double amount;
    private String deduction_for;
    private Boolean is_deductable = true;
    private Boolean is_deleted = false;
    private Boolean is_approved = false;
    private Boolean is_active = false;
    //start paying
    private String start_period_m;
    private Integer start_period_y;
    //end paying
    private String end_period_m;
    private Integer end_period_y;

    //when deduction has other relationship example advance deduction
    private Long relationship_id=0L;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP NULL")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP NULL")

    private LocalDateTime deleted_at;
}

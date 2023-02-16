package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent;

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
public class Sacco {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;
    private Long salary_id;

    private Double Contribution_amount;
    private Boolean is_withdrawn = false;
    private Boolean is_cleared = false;
    private LocalDateTime enrolled_on;
    private LocalDateTime cleared_on;
    private String month;
    private Integer year;

    private String status = "unApproved";
    private Boolean deleted = false;

     //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}
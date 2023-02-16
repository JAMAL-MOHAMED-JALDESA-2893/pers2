package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig;

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
public class Saccoconfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Double min_deduction_Percentage;
    private Double max_deduction_Percentage;
    private Integer year;
    private String status = "unApproved";
    private Boolean deleted = false;
    private String employeeId;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP NULL")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP NULL")

    private LocalDateTime deleted_at;
}

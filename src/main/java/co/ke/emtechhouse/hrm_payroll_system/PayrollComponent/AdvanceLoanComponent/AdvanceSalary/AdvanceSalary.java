package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class AdvanceSalary {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;
    private Double salary_amount = 0.00;
    private String month ="Not set";
    private Integer year = 2022;
    private String status = "Generated";
    private String user_identity="user";
    private String supervisor_approval = "N/A";
    private String hr_approval = "N/A";
    private String director_approval = "N/A";
    private Boolean is_advance_salary_closed = false;
    private Boolean is_deleted = false;
    private Boolean is_processing = false;
    private Boolean is_approved = false;
    private Boolean is_disbursed = false;
    private Boolean is_executive = false;
    private Boolean is_paid = false;

    private String reason;

//    TODO:CLOSE THE ADVANCE ON COMMIT
    //*****************Timestamps *********************
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

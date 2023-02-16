package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Clearence {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long employee_id;
    private Long branch_code;
    private Long department_id;
    private String reason = "End of Contract";
    private Boolean is_supervisor_approved = false;
    private String  supervisor_rejection_reason = "Contractual Terms not allowed";
    private Boolean is_hr_approved = false;
    private String  hr_rejection_reason = "Contractual Terms not allowed";
    private Boolean is_Director_approved = false;
    private String  director_rejection_reason = "Contractual Terms not allowed";
    private String clearence_status = "pending";
    @Column(name = "monitor_from_date", columnDefinition = "DATE")
    private LocalDateTime monitor_from_date;
    @Column(name = "exit_date", columnDefinition = "DATE")
    private LocalDateTime exit_date;
    private Double salary = 0.00;
    private Boolean is_cleared = false;

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

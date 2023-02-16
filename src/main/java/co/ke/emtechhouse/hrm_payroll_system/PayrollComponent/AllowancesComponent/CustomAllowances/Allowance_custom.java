package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

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
public class Allowance_custom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private String department_id;
    private Double amount;
    private String allowance_for;
//    private String month;
//    private String year;
    private Boolean is_taxable = false;
    private String is_director_approved = "Pending";
    private String rejection_reason_director = "Pending";
    private String is_supervisor_approved = "Pending";
    private String is_hrm_approved = "Pending";
    private Boolean is_payable = false;
    private Boolean is_paid = false;
    private Boolean is_deleted = false;
//    private LocalDate start_date;
//    private LocalDate end_date;
    //start paying
    private String start_period_m;
    private String start_period_y;
    //end paying
    private String end_period_m;
    private String end_period_y;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP NULL")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP NULL")

    private LocalDateTime deleted_at;
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.EmployeeInfo;

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
public class Employeeinfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long emp_no;
    private String first_name;
    private String other_names;
    private String id_no;
    private String acct_no;
    private String bank;
    private String nssf_no;
    private String nhif_no;
    private String pin_no;
    private Double salary;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

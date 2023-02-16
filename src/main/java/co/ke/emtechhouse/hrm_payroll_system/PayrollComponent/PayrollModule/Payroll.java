package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

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
public class Payroll {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long department_id;
    private Long employee_id;
    private String first_name;
    private String other_names;
    private String id_no;
    private String acct_no;
    private String bank;
    private String nssf_no;
    private String nhif_no;
    private String pin_no;
    private String period_m;
    private String period_y;
    private Double salary;
    private Double nssf;
    private Double nhif;
    private Double paye;
    private Double helb;
    private Double total_deductions_net_salary;
    private Double net_salary;
    private String deleted_flag = "N";
    private Double total_non_cash_benefit = 0.00;
    private Double value_of_quarters = 0.00;
    private Double gross_pay = 0.00;
    private Double dcrs_e1 = 0.00;
    private Double dcrs_actual_e2 = 0.00;
    private Double dcrs_fixed_e3 = 0.00;
    private Double owner_occupied_interests = 0.00;
    private Double retirement_and_owner_occupied_interests = 0.00;
    private Double chargeable_pay = 0.00;
    private Double tax_charged = 0.00;
    private Double total_relief = 0.00;
    private Boolean is_active = true;
    private Boolean is_salary_committed = false;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

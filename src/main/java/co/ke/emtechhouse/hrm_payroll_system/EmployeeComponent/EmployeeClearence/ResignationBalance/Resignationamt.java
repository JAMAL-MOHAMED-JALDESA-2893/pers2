package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance;

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
public class Resignationamt {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long resignation_notice;

    private Double monthly_basic_salary = 0.00;
    private Double basic_salary = 0.00;
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
    private Double total_relief= 0.00;
    private Double pension = 0.0;
    private Double nhif_deductions = 0.0;
    private Double nssf_deductions = 0.0;
    private Double paye_deductions = 0.0;
    private Double helb_deductions = 0.0;
    private Double leave_deduction = 0.0;
    private Double net_pay = 0.0;
    private Long employee_id;
    private Long department_id;
    private String month;
    private String year = "2021";
    private Boolean deleted;
    private Double allowance_custom = 0.0;
    private Double allowance_per_deam = 0.0;
    private Double allowance_field = 0.0;
    private Double allowance_overtime = 0.0;
    private Double taxable_income = 0.0;
    private Double relief_personal = 0.0;
    private Double relief_insurance = 0.0;
    private Double advanced_deductions = 0.00;
    private Double sacco_contributions = 0.00;
    private Boolean paid = false;
    private Boolean is_commited = false;
    private Boolean is_pay_btn_enabled = false;
    private Boolean is_approved = false;

    @Column(name = "paid_on")
    private LocalDateTime paid_on;


    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

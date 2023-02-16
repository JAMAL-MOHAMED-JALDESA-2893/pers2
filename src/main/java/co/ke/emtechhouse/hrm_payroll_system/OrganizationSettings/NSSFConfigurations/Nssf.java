package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations;

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
public class Nssf {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private String tax_band = "NSSF 2022";
    // minmum earnings
    private Double min_nssf = 0.00;
    // maximum earnings
    private Double max_nssf = 0.00;
    //private Double employee_payment_rate = 0.00;
    //private Double company_payment_rate = 0.00;
    //private Double total_nssf_rate = 0.00;

    private  Double min_earnings;
    private Double max_earnings;
    private Double pensionable_earnings;
    private Boolean is_pensionable_earnings_specified=false;
    // UEL sal>=18000
    private String earning_class;
    // employee payment rate =6%
    private Double employee_payment_rate = 0.00;
    //employer payment rate = 6%
    private Double company_payment_rate = 0.00;
    // employer plus employee payment rate total = 12%
    private Double total_nssf_rate = 0.00;



    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at= LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
    private String status = "unApproved";
    private Boolean deleted = false;
    private Boolean is_approved = false;
}
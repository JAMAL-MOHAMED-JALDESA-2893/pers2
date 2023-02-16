package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations;

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
public class Kra {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private String tax_band = "No Configurations";
    private Double amount_annual = 0.00;
    private Double amount_monthly = 0.00;
    private Double rate = 0.00;
    private Double personal_relief_monthly = 0.00;
    private Double personal_relief_annualy = 0.00;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
    private String status = "unApproved";
    private Boolean deleted = false;
}

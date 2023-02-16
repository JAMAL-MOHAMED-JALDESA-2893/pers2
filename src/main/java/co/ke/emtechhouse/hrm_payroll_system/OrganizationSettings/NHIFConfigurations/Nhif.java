package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations;

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
public class Nhif {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private String tax_band = "NHIF 2021";
    private Double min_threshold = 0.00;
    private Double max_threshold = 0.00;
    private Double nhif_deduction = 0.00;

    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
    private String status = "unApproved";
    private Boolean deleted = false;
    private Boolean is_approved = false;
}

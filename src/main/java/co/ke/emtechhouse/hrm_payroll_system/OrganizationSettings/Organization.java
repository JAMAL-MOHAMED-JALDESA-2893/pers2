package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings;

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
public class Organization {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String organization_name = "No Configurations";
    private String organization_street = "No Configurations";
    private String organization_building = "No Configurations";
    private String organization_floor = "No Configurations";
    private String organization_kra_pin = "No Configurations";
    private String organization_business_no = "No Configurations";
    private String organization_bank1_name = "No Configurations";
    private String organization_bank1_account = "No Configurations";
    private String organization_bank2_name = "No Configurations";
    private String organization_bank2_account = "No Configurations";
    private String organization_pay_bill = "No Configurations";
    private String organization_till = "No Configurations";
    @Lob
    private String organization_address = "No Configurations";
    private String organization_telephone = "No Configurations";
    private String organization_email = "No Configurations";
    @Lob
    private String organization_location = "No Configurations";
    private String organization_county = "No Configurations";
    private String organization_country = "No Configurations";
    @Lob
    private String organization_website = "No Configurations";
    @Lob
    private String organization_map_link = "No Configurations";
    private String status = "unApproved";
    private Boolean deleted = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

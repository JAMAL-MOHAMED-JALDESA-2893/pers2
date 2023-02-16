package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations;

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
public class Emailconfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String email_type = "No Configurations";
//    Payslip & P9form, Notification
    private String email_salutation = "No Configurations";
    private String email_subject = "No Configurations";
    private String email_heading = "No Configurations";
    @Lob
    @Column(name="email_message", length=1000)
    private String email_message = "No Configurations";
    private String email_remarks = "No Configurations";
    private String email_regards_from = "No Configurations";
    private String email_organization_name = "No Configurations";
    private String email_organization_phone = "No Configurations";
    private String email_organization_mail = "No Configurations";
    @Lob
    private String email_organization_address = "No Configurations";
    @Lob
    private String email_organization_location = "No Configurations";
    @Lob
    private String email_organization_website = "No Configurations";
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

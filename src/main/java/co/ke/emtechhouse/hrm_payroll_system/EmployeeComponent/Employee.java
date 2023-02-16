package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent.Promotion;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.Payroll;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.Sacco;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.Pm_goal;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_custom;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long emp_no;
    private Long branch_code;
    private String firstName = "Not Provided";
    private String middleName = "Not Provided";
    private String lastName = "Not Provided";
    private String Other_names = "";
    private String gender = "Not Provided";
    private String religion = "Not Provided";
    private String nationalId = "Not Provided";
    private String birthCertNo = "Not Provided";
    private String postalAddress = "Not Provided";
    private String postalCode = "Not Provided";
    private String city = "Not Provided";
    private String personalPhone = "Not Provided";
    private String specialNeeds = "No";
    private String specialNeedsCertificate = "Without Special Need Attention";
    private String specialNeedDescription = "Without Special Need Attention";
    private String workMail = "Not Provided";
    private String nationality = "Not Provided";
    private String county = "Not Provided";
    private String subCounty = "Not Provided";
    private String residentialCountry = "Not Provided";
    private String residentialCounty = "Not Provided";
    private String residentialSubCounty = "Not Provided";
    private String homeAddress = "Not Provided";
    private String alternativePhone = "Not Provided";
    private String homeNumber = "Not Provided";
    private String personalEmail = "Not Provided";
    private String alternativeEmail = "Not Provided";
    private String highestEducation = "Not Provided";
    private String courseProgram = "Not Provided";
    private String enrollmentStatus = "Not Provided";
    private String timeTaken = "Not Provided";
    private String firstRefName = "Not Provided";
    private String firstRefCompany = "Not Provided";
    private String firstRefEmail = "Not Provided";
    private String firstRefPhone = "Not Provided";
    private String secondRefName = "Not Provided";
    private String secondRefCompany = "Not Provided";
    private String secondRefEmail = "Not Provided";
    private String secondRefPhone = "Not Provided";
    private String bankName = "Not Provided";
    private String bankAccount = "Not Provided";
    private String kraNo = "Not Provided";
    private String nssfNo = "Not Provided";
    private String nhifNo = "Not Provided";
    //basic salary
    private Double basic_salary =0.00;

    private Double gross_salary = 0.00;
    private Double total_non_cash_benefit = 0.00;
    private Double value_of_quarters = 0.00;
    private Double owner_occupied_interests = 0.00;
    private Double personal_relief= 0.00;
    private Double insurance_relief = 0.00;
    private Double dcrs_actual_e2 = 0.00;
    private String reportingTo = "Human Resource Manager";
    private String occupation = "Not Provided";
    private String jobGroup = "Not Provided";
    private Long departmentId;
    private String salary_status = "Opened";
    private String employee_status = "Inactive";
    private String position = "Entry-level";
    private Boolean is_salary_closed = false;
    private Boolean permanently_cleared = false;
    private Boolean is_approved = false;
    private Boolean have_account = false;
    private Boolean is_activated = false;
    private Boolean is_trashed = false;
    private Boolean is_deleted = false;
    private Boolean requested_resignation = false;
    private Boolean has_dummy_salary = false;
    private Boolean is_evaluation_enrolled = false;
//added field to cover the employee clearence fee
    private Double cash_payment_amount = 0.00;
    private String cash_payment_means = "Cash";
    private String cash_payment_reason = "Not Provided";
    //***************** Employee Sacco Details *********************
    @OneToMany(targetEntity = Sacco.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Sacco> sacco_contributions;
//    Sacco Settings
    private Boolean is_sacco_enrolled = false;
    private Double sacco_deduction_percentage = 0.00;
    private LocalDateTime enrolled_on;
//***************** Employee Education Details *********************
    @OneToMany(targetEntity = EmployeeEducation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<EmployeeEducation> employeeEducation;

//***************** Employee Work Details *********************
    @OneToMany(targetEntity = EmployeeWorkExperience.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<EmployeeWorkExperience> employeeWorkExperience;

//***************** Payroll Details *********************
    @OneToMany(targetEntity = Payroll.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Payroll> employeePayroll;

    //***************** Allowances Details *********************
    @OneToMany(targetEntity = Allowance_custom.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Allowance_custom> allowance_customs;

//    Employee Promotion
//***************** Employee Leave Details *********************
    @OneToMany(targetEntity = Promotion.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Promotion> promotions;
//    //    Work => Perfomance Management
//    @OneToOne(targetEntity = Pm_Tasks.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_id", referencedColumnName = "id")
//    private Department head_of_department_eid;
    //***************** Employee have many goals *********************
    @OneToMany(targetEntity = Pm_goal.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Pm_goal> pm_goals;
    //*****************Timestamps *********************
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
    @Column(name = "cleared_at")
    private LocalDateTime cleared_at;
    @Column(name = "promoted_at")
    private LocalDateTime promoted_at;
}

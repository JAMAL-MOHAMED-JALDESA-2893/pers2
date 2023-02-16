package co.ke.emtechhouse.hrm_payroll_system.JobPortalComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeEducation;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeWorkExperience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Application implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalId;
    private String postalAddress;
    private String postalCode;
    private String city;
    private String personalPhone;
    private String emailAddress;
    private String specialNeeds;
    private String specialNeedsCertificate;
    private String specialNeedDescription;
    private Boolean deleted;

    //***************** Employee Education Details *********************
    @OneToMany(targetEntity = EmployeeEducation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeEducationId", referencedColumnName = "id")
    private List<EmployeeEducation> employeeEducation;

    //***************** Employee Work Details *********************
    @OneToMany(targetEntity = EmployeeWorkExperience.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeWorkId", referencedColumnName = "id")
    private List<EmployeeWorkExperience> employeeWorkExperience;

    //*****************Timestamps *********************
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;
}

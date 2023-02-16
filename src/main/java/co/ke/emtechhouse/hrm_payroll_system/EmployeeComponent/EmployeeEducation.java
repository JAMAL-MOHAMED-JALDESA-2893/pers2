package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EmployeeEducation implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String institutionLevel;
    private String institutionName;
    private String awardCertificate;
    private String gpaScore;
    private String enrollOn;
    private String graduatedOn;
    @Lob
    private String certificateFile;
//    @OneToMany(targetEntity = EmployeeFilesEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "fileId", referencedColumnName = "id")
//    private List<EmployeeFilesEntity> employeeFiles;
}

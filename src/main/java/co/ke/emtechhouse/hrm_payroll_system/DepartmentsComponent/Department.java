package co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String departmentName = "Undefined Department";
    private Long head_of_department_eid;
    private String directorOfDepartment = "Undefined Director";
    private String departmentMail = "Mail Not Provided";
    private String status = "unApproved";
    private Boolean deleted = false;
    private String employeeId;

//    List Employees
    @OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private List<Employee> departmentEmployees;

    //*****************Timestamps *********************
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

}

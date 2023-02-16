package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models;

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
public class Pm_Tasks {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private Long employee_id;
    private String project_name = "Not Configured";
    private String project_participation = "Individual";
    private String component_name = "Not Configured";
    private String deliverable_name = "Not Configured";
    private String progress = "Generated";
    private LocalDateTime started_on;
    private LocalDateTime deadline;
    private LocalDateTime delivered_on;
    private Double score = 0.00;

    //***************** Task has Many Deliverables => Perfomance Management *********************
    @OneToMany(targetEntity = Pm_Deliverables.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pm_task_id", referencedColumnName = "id")
    private List<Pm_Deliverables> task_deliverables;
//
//    later add a link to excel sheet upload for the test cases;

    private Boolean is_approved = false;
    private Boolean is_deleted = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models;

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
public class Pm_Deliverables {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private Long pm_task_id;
    private String deliverable_name = "Not Configured";
    private String progress = "Not Configured";
    private LocalDateTime deadline;
    private LocalDateTime delivered_on;
    private Double score = 0.00;
//    //***************** Many Deliverables has 0ne Summary => Perfomance Management *********************
//    @ManyToOne(targetEntity = Pm_Summary.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "pm_delivarables_id", referencedColumnName = "id")
//    private List<Pm_Deliverables> deliverables_summary;

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

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
public class Pm_Summary {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;

    private Long pm_delivarables_id;
    private String summary_name = "Not Configured";
    private String work_status = "Not Configured";
    private String perfomance_status = "Not Configured";
    private Integer perfomance_rate = 0;
    private String remarks = "Not Configured";
    private String recommendation = "Not Configured";

    //***************** Task has Many Deliverables => Perfomance Management *********************
    @OneToMany(targetEntity = Pm_Deliverables.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pm_task_id", referencedColumnName = "id")
    private List<Pm_Deliverables> task_deliverables;

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

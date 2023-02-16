package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments.Pm_comment;
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
public class Pm_review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long parameter_id;
    private String parameter_name;
    private String goal = "Initialization";
    private Long pm_goal_id;
    private String remarks = "Undefine";
    private String recommendations = "Undefine";
    private Double percentage_score = 0.00;
    private String month = "";
    private Integer year = 2021;
    private String status = "Pending";
    private Boolean is_reviewed = false;
    private Boolean is_approved = false;
    private Boolean is_deleted = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    //***************** Review has many Comments *********************
    @OneToMany(targetEntity = Pm_comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pm_review_id", referencedColumnName = "id")
    private List<Pm_comment> pm_comments;
}

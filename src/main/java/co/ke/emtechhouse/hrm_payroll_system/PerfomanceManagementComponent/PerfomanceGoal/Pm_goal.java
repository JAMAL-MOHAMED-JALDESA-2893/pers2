package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Pm_review;
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
public class Pm_goal {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long department_id;
    private Long parameter_id;
    private String parameter_name;
    private String goal = "Undefined";
    private String month = "";
    private Integer year = 2021;
    private Integer next_month_review_delay = 0;
    private LocalDateTime next_review_date;
    private Boolean review_enabled = false;
    private Boolean check_enabled = false;
    private String status = "Pending";
    private Boolean is_approved = false;
    private Boolean is_deleted = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    //***************** Goal Have many Reviews *********************
    @OneToMany(targetEntity = Pm_review.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pm_goal_id", referencedColumnName = "id")
    private List<Pm_review> pm_reviews;
}

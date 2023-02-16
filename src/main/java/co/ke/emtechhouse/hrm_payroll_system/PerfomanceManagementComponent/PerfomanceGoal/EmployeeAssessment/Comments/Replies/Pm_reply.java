package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments.Replies;

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
public class Pm_reply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employee_id;
    private Long pm_comment_id;
    private Long pm_reply_id;
    private String reply = "";
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

    //***************** Reply can have many replies *********************
    @OneToMany(targetEntity = Pm_reply.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pm_reply_id", referencedColumnName = "id")
    private List<Pm_reply> sub_replies;
}
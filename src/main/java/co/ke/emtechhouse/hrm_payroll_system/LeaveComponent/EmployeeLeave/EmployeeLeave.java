package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EmployeeLeave {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private Long employeeId;
    private Long department_id;
    private Long leave_type_id;
    private Long employee_work_days;
    private String leave_type = "Holiday Leave";
    private String allowed_duration = "0";
    private String start_date = "undefined";
    private String end_date = "undefined";
    private String status = "Generated";
    private Double deduction_percentage = 0.0;
    private String employee_reason_for_leave ="Holiday Leave";

    private String supervisor_approval = "Pending";
    private String supervisor_reason_for_rejection = "Leave Approved";

    private String hr_approval = "Pending";
    private String hr_reason_for_rejection = "Leave Approved";

    private String director_approval = "Pending";
    private String director_reason_for_rejection = "Leave Approved";

    private String rejection_reason = "Accepted";

    private Boolean is_executive = false;
    private Boolean is_application_open = true;
    private Boolean on_leave = false;
    private Boolean deleted = false;

    //*****************Timestamps *********************
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

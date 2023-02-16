package co.ke.emtechhouse.hrm_payroll_system.BranchMaintainanceComponenet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String phone_number;
    private String email;
    private String city;
    private Long branch_manager;
    private String calculation_days;
    private String calculation_hours;
    private String branch_type= "ordinary";
    private Boolean is_deleted = false;

    //*****************Timestamps *********************
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at= LocalDateTime.now();
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

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
public class Batch_processes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long branch_code;
    private String event_type;
    private Integer execution_date;
    private LocalDateTime executed_on;
    private LocalDateTime next_execution;
    private Boolean is_approved = false;
    private Boolean deleted = false;
    private Boolean is_enabled = true;
    private Boolean is_executed = false;
    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}

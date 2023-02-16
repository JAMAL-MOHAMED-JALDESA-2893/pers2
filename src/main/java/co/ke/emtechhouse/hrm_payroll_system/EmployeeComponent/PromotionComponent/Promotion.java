package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Promotion implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long employee_id;
    private Long branch_code;
    private Long department_id;
    private Double prev_basic_pay = 0.00;
    private Double new_basic_pay = 0.00;
    private String prev_position = "Not Provided";
    private String new_position = "Not Provided";
    private String reason = "Not Provided";
    private Boolean hrm_approved = false;
    private Boolean supervisor_approved = false;
    private Boolean director_approved = false;
    private Boolean is_executive = false;
    private Boolean is_head_of_department = false;
    private LocalDateTime registared_on;
    private String status = "Active";
    private Boolean is_trashed = false;
    private Boolean is_deleted = false;



    //*****************Timestamps *********************
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime promoted_at;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;
}
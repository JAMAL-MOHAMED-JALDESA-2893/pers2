//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.PerdeamComponent;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//public class Allowance_Perdeam implements Serializable {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(nullable = false, updatable = false)
//    private Long id;
//    private Long employee_id;
//    private String department_id;
//    private String site;
//    private String duties;
//    private Double amount;
//    private LocalDateTime date_from;
//    private LocalDateTime date_to;
//    private String month;
//    private String year;
//    private Boolean is_active = false;
//    private Boolean is_director_approved = false;
//    private Boolean is_supervisor_approvved = false;
//    private Boolean is_hrm_approved = false;
//    private Boolean is_deleted = false;
//    //*****************Timestamps *********************
//    @Column(name = "created_at")
//    private LocalDateTime created_at;
//    @Column(name = "updated_at")
//    private LocalDateTime promoted_at;
//    @Column(name = "deleted_at")
//    private LocalDateTime deleted_at;
//}
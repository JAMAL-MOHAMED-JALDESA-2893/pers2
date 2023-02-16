//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionSubComponent;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//public class Deductions implements Serializable {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(nullable = false, updatable = false)
//    private Long id;
//    private String name;
//    private String percentage;
//    private String description;
//    private String datedFrom;
//    private String updatedOn;
//
//    public Deductions() {
//    }
//
//    public Deductions(String name, String percentage, String description, String datedFrom, String updatedOn) {
//        this.name = name;
//        this.percentage = percentage;
//        this.description = description;
//        this.datedFrom = datedFrom;
//        this.updatedOn = updatedOn;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPercentage() {
//        return percentage;
//    }
//
//    public void setPercentage(String percentage) {
//        this.percentage = percentage;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getDatedFrom() {
//        return datedFrom;
//    }
//
//    public void setDatedFrom(String datedFrom) {
//        this.datedFrom = datedFrom;
//    }
//
//    public String getUpdatedOn() {
//        return updatedOn;
//    }
//
//    public void setUpdatedOn(String updatedOn) {
//        this.updatedOn = updatedOn;
//    }
//}

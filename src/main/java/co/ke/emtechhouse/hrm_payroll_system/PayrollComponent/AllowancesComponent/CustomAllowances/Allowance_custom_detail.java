package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

import java.time.LocalDateTime;

public interface Allowance_custom_detail {
    Long getid();
    String getallowance_for();
    Double getamount();
    LocalDateTime getcreated_at();
    LocalDateTime getdeleted_at();
    Long getdepartment_id();
    String getdepartment_name();
    Long getemployee_id();
    String getfirst_name();
    String getlast_name();
    String getend_period_m();
    String getend_period_y();
    Boolean getis_deleted();
    String getis_director_approved();
    String getis_hrm_approved();
    Boolean getis_paid();
    Boolean getis_payable();
    String getis_supervisor_approved();
    Boolean getis_taxable();
    String getrejection_reason_director();
    String getstart_period_m();
    String getstart_period_y();
    LocalDateTime getupdated_at();
}

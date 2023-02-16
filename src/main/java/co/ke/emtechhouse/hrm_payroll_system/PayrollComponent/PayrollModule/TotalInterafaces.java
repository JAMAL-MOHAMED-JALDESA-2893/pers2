package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

import java.time.LocalDateTime;

public interface TotalInterafaces {
    interface SalaryDetail{
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getNationalId();
        String getPersonalPhone();
        String getBankName();
        String getBankAccount();
        String getKraNo();
        String getNssfNo();
        String getNhifNo();
        Double getGross_salary();
        String getJobGroup();


        //      Salary Table
        Long getSalaryId();
        Double getCurrentBasicSalary();
        Double getNhifDeductions();
        Double getNssfDeductions();
        Double getPayeDeductions();
        Double getHelbDeductions();
        Double getLeaveDeduction();
        Double getNetPay();
        Long getEmployeeId();
        String getMonth();
        String getYear();
        Boolean getDeleted();
        Boolean getPaid();
        LocalDateTime getCreatedAt();
        LocalDateTime getUpdatedAt();
        LocalDateTime getDeletedAt();

    }

}

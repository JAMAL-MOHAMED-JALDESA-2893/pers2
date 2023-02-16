package co.ke.emtechhouse.hrm_payroll_system.MailComponent;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface PayrollInterface {

    public interface PayrollData{
        Long getSalaryId();
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getEmployeeEmail();
        Date getPayslipDate();
        int getEmployeeId();
    }
}

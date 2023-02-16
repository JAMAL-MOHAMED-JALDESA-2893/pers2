package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

public interface PayrollInterfaces {
    interface PayrollSummary {
        String getMonth();
        String getYear();
        String getTotalNssf();
        String getTotalNhif();
        String getTotalPaye();
        String getTotalGrosspay();
        String getTotalNetpay();
        String getTotalEmployees();
    }
    interface yearlyPayrollSummary {
        String getMonths();
        String getSalary();
    }
    interface employeeSummary {
        String getTotalEmployee();
        String getMonth();
    }
    interface payrollYears {
        String getYears();
    }
}

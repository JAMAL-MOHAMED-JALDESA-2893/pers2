package co.ke.emtechhouse.hrm_payroll_system._exception;

public class EmployeeNotFoundException  extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

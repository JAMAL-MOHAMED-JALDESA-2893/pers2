package co.ke.emtechhouse.hrm_payroll_system._exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

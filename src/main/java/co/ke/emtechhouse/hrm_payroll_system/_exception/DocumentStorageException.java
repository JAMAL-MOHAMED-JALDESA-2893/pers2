package co.ke.emtechhouse.hrm_payroll_system._exception;

public class DocumentStorageException extends RuntimeException {
    public DocumentStorageException(String message) {
        super(message);
    }
    public DocumentStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests;

import lombok.Data;

@Data
public class UserCredentials {
    public String username;
    public String password;
}

package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OTPCode {
    public Integer otp;
    public String username;
}

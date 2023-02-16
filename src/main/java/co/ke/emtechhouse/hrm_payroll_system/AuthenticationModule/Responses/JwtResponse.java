package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses;


import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String solCode;
    private Character firstLogin;
    private String roleClassification;
    private Employee employeeDetails;
    private Boolean isAcctActive;
    }

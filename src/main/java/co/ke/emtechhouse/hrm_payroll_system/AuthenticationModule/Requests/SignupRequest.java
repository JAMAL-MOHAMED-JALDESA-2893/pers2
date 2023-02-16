package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    private String modifiedBy;

    @NotBlank
    @Size(min = 10, max = 12)
    private String phoneNo;

    @NotBlank
    @Size(min=3, max = 5)
    private String solCode;

    @NotBlank
    @Size(min=3, max = 5)
    private Long employee_id;
    @NotBlank
    @Size(min=3, max = 30)
    private String roleClassification;
}

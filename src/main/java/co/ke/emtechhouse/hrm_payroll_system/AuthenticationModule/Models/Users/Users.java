package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "users")
public class Users {
    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "sn", updatable = false)
    private Long sn;
    @Column(name = "username", length = 20, unique = true, nullable = false)
    private String username;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;
    @Column(name = "phone")
    private String phoneNo;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @Column(name = "createdOn")
    private Date createdOn;
    @Column(name = "sol_code", length = 5)
    private String solCode;
    @Column(name = "modifiedBy")
    private String modifiedBy;
    @Column(name = "modifiedOn")
    private Date modifiedOn;
    @Column(name = "verifiedBy")
    private String verifiedBy;
    @Column(name = "verifiedOn")
    private Date verifiedOn;
    @Column(name = "verifiedFlag")
    private String verifiedFlag;
    @Column(name = "deleteFlag")
    private String deleteFlag;
    @Column(name = "active")
    private boolean isAcctActive;
    @Column(name = "first_login")
    private Character firstLogin;
    @Column(name = "locked")
    private boolean isAcctLocked;
    @Column(name = "roleClassification")
    private String roleClassification;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private Long employee_id;


}

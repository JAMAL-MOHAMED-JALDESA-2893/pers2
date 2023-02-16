package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Resources;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.MailService.MailService;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.OTP.OTP;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.OTP.OTPRepository;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.OTP.OTPService;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.ERole;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.Role;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.RoleRepository;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.Users;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.UsersRepository;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests.LoginRequest;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests.OTPCode;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests.PasswordResetRequest;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests.SignupRequest;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.JwtResponse;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Security.jwt.JwtUtils;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Security.services.UserDetailsImpl;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.utils.PasswordGeneratorUtil;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;
//    @Autowired
//    Securessl securessl;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OTPService otpService;

    @Autowired
    MailService mailService;

    @Autowired
    OTPRepository otpRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws MessagingException {
        System.out.println("Authentication----------------------------------------------------------------------");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Users user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        String otp = "Your otp code is " + otpService.generateOTP(userDetails.getUsername());

//        mailService.sendEmail(userDetails.getEmail(), otp, "OTP Code");
        JwtResponse response = new JwtResponse();
        response.setToken(jwt);
        response.setType("Bearer");
        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        response.setRoles(roles);
        response.setSolCode(user.getSolCode());
        response.setFirstLogin(user.getFirstLogin());
        response.setEmployeeDetails(employeeRepository.findById(user.getEmployee_id()).orElse(null));
        response.setRoleClassification(user.getRoleClassification());
        response.setIsAcctActive(userDetails.getAcctActive());

        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
//        TODO: Add empty Fields
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        Users user = new Users();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setEmployee_id(signUpRequest.getEmployee_id());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_SUPERVISOR.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            signUpRequest.getRole().forEach(role -> {
                Role userRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });
        }
        user.setRoles(roles);
        user.setCreatedOn(new Date());
        user.setDeleteFlag("N");
        user.setModifiedOn(new Date());
        user.setAcctActive(true);
        user.setAcctLocked(false);
        user.setVerifiedFlag("Y");
        user.setFirstLogin('Y');
        user.setVerifiedOn(new Date());
        user.setModifiedBy(signUpRequest.getModifiedBy());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNo(signUpRequest.getPhoneNo());
        user.setSolCode(signUpRequest.getSolCode());
        user.setRoleClassification(signUpRequest.getRoleClassification());

        userRepository.save(user);
        String mailMessage = "Dear " + user.getFirstName() + " your account has been successfully created using username " + user.getUsername()
                + " and password " + signUpRequest.getPassword() + ". Login in to change your password.";

        System.out.println(mailMessage);
//        mailService.sendEmail(user.getEmail(), mailMessage, "Account Successfully Created");
        return ResponseEntity.ok(new MessageResponse("User " + user.getUsername() + " registered successfully!"));
    }

    @GetMapping(path = "/users")
    public List<Users> allUsers(){
        return userRepository.findByDeleteFlag("N");
    }

    @GetMapping(path = "/all/per/department")
    public List<UsersRepository.EmployeeAccount> allUsers(@RequestParam Long department_id){
        return userRepository.findByUserPerDepartment(department_id);
    }


    @GetMapping(path = "/users/{username}")
    public Users getUserByUsername(@PathVariable String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    @PutMapping(path = "/users/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Users user){

//        Set<String> strRoles = new HashSet<>();
//        for (Role role1 : user.getRoles()) {
//            strRoles.add(role1.getName());
//            log.info(role1.getName());
//        }
//
//        Set<Role> roles = new HashSet<>();
//
//        if (user.getRoles().size() < 1 ) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER.toString())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                Role userRole = roleRepository.findByName(role)
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                roles.add(userRole);
//            });
//        }
//        For each role find by name and get id and set each with id


        Set<Role> strRoles = new HashSet<>();
        Set<Role> roles = user.getRoles();
         roles.forEach(role -> {
                Role userRole = roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
             strRoles.add(userRole);
            });
         user.setRoles(strRoles);
         user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Information has been successfully updated"));
    }
    @PostMapping(path = "/verifyOTP")
    public ResponseEntity<?> validateOTP(@RequestBody OTPCode otpCode) {
        OTP otp = otpRepository.validOTP(otpCode.username);
        if (Objects.isNull(otp) || !Objects.equals(otp.getOtp(), otpCode.otp)) {
            return ResponseEntity.badRequest().body(new MessageResponse("OTP is not valid!"));
        } else {
            return ResponseEntity.ok(new MessageResponse("Welcome, OTP valid!"));
        }
    }
    @GetMapping(path = "/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok().body(roleRepository.findAll());
    }
    @PostMapping(path = "/reset")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest) throws MessagingException {
        if (!(userRepository.existsByEmail(passwordResetRequest.getEmailAddress()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("User with give email address does not exist."));
        } else {
            PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtil();
            String generatedPassword = passwordGeneratorUtil.generatePassayPassword();
            String pathToReset = "Your Password has been successfully reset. Use the following password to login. Password " + generatedPassword;
            Users user = userRepository.findByEmail(passwordResetRequest.getEmailAddress()).orElse(null);
            assert user != null;
            user.setPassword(encoder.encode(generatedPassword));
            user.setFirstLogin('Y');
            userRepository.save(user);
            mailService.sendEmail(passwordResetRequest.getEmailAddress(), pathToReset, "Password Reset Link");
            return ResponseEntity.ok().body(new MessageResponse("Password Reset Successful."));
        }
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        if (!userRepository.existsByEmail(passwordResetRequest.getEmailAddress())) {
            return ResponseEntity.badRequest().body(new MessageResponse("No such user exists"));
        } else {
            Users user = userRepository.findByEmail(passwordResetRequest.getEmailAddress()).orElse(null);
            if (BCrypt.checkpw(passwordResetRequest.getOldPassword(), user.getPassword())) {
                if (passwordResetRequest.getPassword().equals(passwordResetRequest.getConfirmPassword())) {
                    user.setPassword(encoder.encode(passwordResetRequest.getPassword()));
                    user.setFirstLogin('N');
                    userRepository.save(user);
                    return ResponseEntity.ok().body(new MessageResponse("User Password updated successfully"));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Password mismatch. Try Again"));
                }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("We could not recognise your old password. Try Again"));
            }
        }
    }
}

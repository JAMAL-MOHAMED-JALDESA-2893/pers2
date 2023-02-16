package co.ke.emtechhouse.hrm_payroll_system;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.ERole;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.Role;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.RoleRepository;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Roles.Roleclassification;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.UsersRepository;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Requests.SignupRequest;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Resources.AuthController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;

@SpringBootApplication
@EnableScheduling
public class Hrm_Payroll_System {
	private final UsersRepository usersRepository;
	private final RoleRepository roleRepository;
	private final AuthController authController;
	public Hrm_Payroll_System(UsersRepository usersRepository, RoleRepository roleRepository, AuthController authController) {
		this.usersRepository = usersRepository;
		this.roleRepository = roleRepository;
		this.authController = authController;
	}
	public static void main(String[] args) {
		SpringApplication.run(Hrm_Payroll_System.class, args);
	}
	@Bean
	CommandLineRunner runner() {
		return args -> {
//			Create Roles
			if (roleRepository.findAll().size() == 0) {
				List<Role> roleList = new ArrayList<>();
				Role directorRole = new Role();
				directorRole.setName(ERole.ROLE_DIRECTOR.toString());
				Role hrRole = new Role();
				hrRole.setName(ERole.ROLE_HR.toString());
				Role supervisorRole = new Role();
				supervisorRole.setName(ERole.ROLE_SUPERVISOR.toString());
				Role employeeRole = new Role();
				employeeRole.setName(ERole.ROLE_EMPLOYEE.toString());
				Role superuserRole = new Role();
				superuserRole.setName(ERole.ROLE_SUPERUSER.toString());
//					Creating Roles
				roleList.add(directorRole);
				roleList.add(hrRole);
				roleList.add(supervisorRole);
				roleList.add(employeeRole);
				roleList.add(superuserRole);
				roleRepository.saveAll(roleList);
			}
			//Create super admin for new system installation and database initialization
			if (Objects.isNull(usersRepository.findByUsername("Superuser").orElse(null))) {
				Set<String> suRoles = new HashSet<>();
				SignupRequest signupRequest = new SignupRequest();
				signupRequest.setEmail("developer@emtechhouse.co.ke");
				signupRequest.setFirstName("Superuser");
				signupRequest.setLastName("Admin");
				signupRequest.setEmployee_id(Long.parseLong("0"));
				signupRequest.setEmail("no-reply@emtechhouse.co.ke");
				signupRequest.setPassword("p?12345678");
				signupRequest.setPhoneNo("2547xxxxxxxx");
				signupRequest.setSolCode("SYS");
				signupRequest.setModifiedBy("SYSTEM");
				signupRequest.setUsername("Director");
				signupRequest.setRoleClassification(Roleclassification.Superuser_privilege.toString());
				suRoles.add(ERole.ROLE_SUPERUSER.toString());
				signupRequest.setRole(suRoles);
				authController.registerUser(signupRequest);
				SignupRequest signupRequest1 = new SignupRequest();
				signupRequest1.setEmail("developer@emtechhouse.co.ke");
				signupRequest1.setFirstName("Director");
				signupRequest1.setLastName("Director");
				signupRequest1.setEmployee_id(Long.parseLong("01"));
				signupRequest1.setEmail("ckibet@emtechhouse.co.ke");
				signupRequest1.setPassword("p?12345678");
				signupRequest1.setPhoneNo("2547xxxxxxxx");
				signupRequest1.setSolCode("SYS");
				signupRequest1.setModifiedBy("SYSTEM");
				signupRequest1.setUsername("Director");
				signupRequest1.setRoleClassification(Roleclassification.Administration_privilege.toString());
				suRoles.add(ERole.ROLE_DIRECTOR.toString());
				signupRequest1.setRole(suRoles);
				authController.registerUser(signupRequest1);

			}

		};
	}
}

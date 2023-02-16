package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNo(String phone);

    List<Users> findByDeleteFlag(String deleteFlag);

    Optional<Users> findByEmail(String email);

    @Query(value = "SELECT users.sn as UserId, employee.id as Employeeid, employee.department_id as Departmentid, employee.first_name as Firstname, employee.middle_name as Middlename, employee.last_name as Lastname FROM employee JOIN users ON employee.id = users.employee_id WHERE employee.department_id=:department_id",nativeQuery = true)
    List<EmployeeAccount> findByUserPerDepartment(Long department_id);
    interface EmployeeAccount{
        Long getUserId();
        Long getEmployeeid();
        Long getDepartmentid();
        String getFirstname();
        String getMiddlename();
        String getLastname();
    }
}

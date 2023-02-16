package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeEducationRepo extends JpaRepository<EmployeeEducation, Long> {
    Optional<EmployeeEducation> findEmployeeEducationById(Long id);
    void deleteEmployeeById(Long id);
}




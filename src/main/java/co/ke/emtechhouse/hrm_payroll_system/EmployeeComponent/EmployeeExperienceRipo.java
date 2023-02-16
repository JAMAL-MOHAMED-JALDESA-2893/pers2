package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeExperienceRipo extends JpaRepository<EmployeeWorkExperience, Long> {
    Optional<EmployeeWorkExperience> findEmployeeExperienceById(Long id);
    void deleteEmployeeById(Long id);
}
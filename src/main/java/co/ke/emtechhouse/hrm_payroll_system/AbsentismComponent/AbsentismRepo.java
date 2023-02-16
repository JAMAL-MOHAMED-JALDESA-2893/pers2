package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbsentismRepo extends JpaRepository<Absentism,Long> {
    Optional<Absentism> findAbsentismById(Long id);
    void deleteAbsentismById(Long id);
}

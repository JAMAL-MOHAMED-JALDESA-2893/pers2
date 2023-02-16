package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent.AbsentismConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbsentismconfigRepo extends JpaRepository<Absentismconfig,Long> {
    Optional<Absentismconfig> findAbsentismconfigById(Long id);
    void deleteAbsentismconfigById(Long id);
}

package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaccoRepo extends JpaRepository<Sacco,Long> {
    Optional<Sacco> findSaccoById(Long id);
    void deleteSaccoById(Long id);
}
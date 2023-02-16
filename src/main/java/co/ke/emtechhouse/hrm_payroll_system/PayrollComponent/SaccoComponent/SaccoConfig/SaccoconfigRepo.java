package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaccoconfigRepo extends JpaRepository<Saccoconfig,Long> {
    Optional<Saccoconfig> findSaccoconfigById(Long id);

    @Query(value = "SELECT * FROM saccoconfig LIMIT 1", nativeQuery = true)
    Optional<Saccoconfig> findSaccoConfiguration();


    void deleteSaccoconfigById(Long id);
}

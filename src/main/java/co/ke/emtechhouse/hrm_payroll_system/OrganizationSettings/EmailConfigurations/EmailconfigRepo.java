package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailconfigRepo extends JpaRepository<Emailconfig,Long> {
    Optional<Emailconfig> findEmailconfigById(Long id);

    @Query(value = "SELECT* FROM emailconfig WHERE emailconfig.email_type=:email_type", nativeQuery = true)
    Optional<Emailconfig> findByEmailType(String email_type);
}

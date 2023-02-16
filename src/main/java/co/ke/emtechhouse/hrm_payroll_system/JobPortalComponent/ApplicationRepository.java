package co.ke.emtechhouse.hrm_payroll_system.JobPortalComponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findApplicationById(Long id);
    void deleteApplicationById(Long id);
}

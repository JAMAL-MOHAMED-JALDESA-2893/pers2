package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepo extends JpaRepository<Organization,Long> {
        Optional<Organization> findOrganizationById(Long id);
        void deleteOrganizationById(Long id);
        }
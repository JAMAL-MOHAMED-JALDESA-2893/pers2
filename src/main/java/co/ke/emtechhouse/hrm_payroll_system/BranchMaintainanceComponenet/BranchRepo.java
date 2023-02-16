package co.ke.emtechhouse.hrm_payroll_system.BranchMaintainanceComponenet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {
}

package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveTypeRepo extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findLeaveTypeById(Long id);
    void deleteLeaveTypeById(Long id);

    @Query(value = "SELECT * FROM leave_type WHERE leave_type.is_enabled = true;", nativeQuery = true)
    List<LeaveType> findAllActiveLeaveTypes();
}


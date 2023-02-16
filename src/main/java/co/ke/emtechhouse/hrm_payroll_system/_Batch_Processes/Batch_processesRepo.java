package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Batch_processesRepo  extends JpaRepository<Batch_processes,Long> {
    Optional<Batch_processes> findBatch_processesById(Long id);
    void deleteBatch_processesById(Long id);

    @Query(value = "SELECT * FROM batch_processes WHERE batch_processes.event_type=:event_type", nativeQuery = true)
    Optional<Batch_processes> findByEventType(String event_type);
}

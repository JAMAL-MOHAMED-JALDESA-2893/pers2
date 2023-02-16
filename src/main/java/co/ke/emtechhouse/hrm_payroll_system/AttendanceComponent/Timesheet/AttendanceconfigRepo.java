package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttendanceconfigRepo  extends JpaRepository<Attendanceconfig,Long> {
    Optional<Attendanceconfig> findAttendanceconfigById(Long id);
    @Query(value = "SELECT * FROM attendanceconfig WHERE attendanceconfig.day=:day", nativeQuery = true)
    Optional<Attendanceconfig> findByDay(String day);
}

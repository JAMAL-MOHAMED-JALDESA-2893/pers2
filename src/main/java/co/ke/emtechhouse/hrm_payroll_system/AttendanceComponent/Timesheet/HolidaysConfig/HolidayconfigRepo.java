package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayconfigRepo  extends JpaRepository<Holidayconfig,Long> {
    Optional<Holidayconfig> findHolidayconfigById(Long id);
    @Query(value = "SELECT * FROM holidayconfig WHERE holidayconfig.is_active = true", nativeQuery = true)
    List<Holidayconfig> findAllActiveHolidays();
    @Query(value = "SELECT * FROM holidayconfig WHERE holidayconfig.holiday_date=:holiday_date", nativeQuery = true)
    Optional<Holidayconfig> findByDate(LocalDate holiday_date);
}

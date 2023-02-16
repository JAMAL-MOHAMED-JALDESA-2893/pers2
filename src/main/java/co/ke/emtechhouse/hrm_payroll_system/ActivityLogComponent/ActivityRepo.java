package co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity,Long> {
    @Query(value = "SELECT * FROM activity WHERE activity.activity_category =:activity_category AND activity.month=:month AND activity.year =:year", nativeQuery = true)
    List<Activity> findActivity(String activity_category, String month, String year);
}
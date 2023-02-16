package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NssfRepo extends JpaRepository<Nssf,Long> {
    Optional<Nssf> findNssfById(Long id);
    void deleteNssfById(Long id);
    //        find the actual deductions
    @Query(value = "SELECT * FROM `nssf` LIMIT 1", nativeQuery = true)
    Optional<Nssf> findNssf();

    @Query(value = "SELECT * FROM `nssf` WHERE `is_approved` AND :salary BETWEEN `min_earnings` AND `max_earnings`", nativeQuery = true)
    Optional<Nssf> findNssfItem(Double salary);




//    @Query(value = "SELECT * FROM nhif WHERE :grosspay BETWEEN min_threshold AND max_threshold", nativeQuery = true)
//    Nhif findNhifDeduction(Double grosspay);
//    //        find maximum threshold
//    @Query(value = "Select * FROM nhif WHERE max_threshold = (select max(max_threshold) from nhif)", nativeQuery = true)
//    Nhif findHighestThreshold();


}

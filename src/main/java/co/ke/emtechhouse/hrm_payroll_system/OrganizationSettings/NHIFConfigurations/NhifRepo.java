package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NhifRepo extends JpaRepository<Nhif,Long> {
        Optional<Nhif> findNhifById(Long id);
        void deleteNhifById(Long id);
//        find the actual deductions
        @Query(value = "SELECT * FROM nhif WHERE :grosspay BETWEEN min_threshold AND max_threshold", nativeQuery = true)
        Nhif findNhifDeduction(Double grosspay);
//        find maximum threshold
        @Query(value = "Select * FROM nhif WHERE max_threshold = (select max(max_threshold) from nhif)", nativeQuery = true)
        Nhif findHighestThreshold();
//        find last threshold
        @Query(value = "SELECT * FROM nhif ORDER BY ID DESC LIMIT 1", nativeQuery = true)
        Nhif findLastTaxBand();
        @Query(value = "SELECT * FROM nhif ORDER BY ID DESC LIMIT 1", nativeQuery = true)
        Optional<Nhif> findLastTaxBandOptional();
        @Query(value = "SELECT * FROM nhif WHERE `nhif`.`max_threshold`=:max_threshold LIMIT 1", nativeQuery = true)
        Optional<Nhif> findByMaxThreshold(Double max_threshold);
        @Query(value = "SELECT * FROM nhif WHERE `nhif`.`min_threshold`=:min_threshold LIMIT 1", nativeQuery = true)
        Optional<Nhif> findByMinThreshold(Double min_threshold);
        }

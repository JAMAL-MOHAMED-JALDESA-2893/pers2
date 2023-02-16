package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KraRepo extends JpaRepository<Kra,Long> {
    Optional<Kra> findKraById(Long id);
    void deleteKraById(Long id);
//    find personal relief
    @Query(value = "SELECT * FROM `kra` LIMIT 1", nativeQuery = true)
    Kra findKra();
//    Get Tax Band 1
    @Query(value = "SELECT * FROM `kra` WHERE kra.tax_band = \"First Salary\" LIMIT 1", nativeQuery = true)
    Kra findTaxBand1Kra();
//    Get Tax Band 2
    @Query(value = "SELECT * FROM `kra` WHERE kra.tax_band = \"Next Salary\" LIMIT 1", nativeQuery = true)
    Kra findTaxBand2Kra();
//    Get Tax Band 3
    @Query(value = "SELECT * FROM `kra` WHERE kra.tax_band = \"In Excess of Amount\" LIMIT 1", nativeQuery = true)
    Kra findTaxBand3Kra();
//    checkifbandexist
    @Query(value = "SELECT * FROM `kra` WHERE kra.tax_band =:tax_band LIMIT 1", nativeQuery = true)
    Optional<Kra> checkIFTaxBandexist(String tax_band);

}
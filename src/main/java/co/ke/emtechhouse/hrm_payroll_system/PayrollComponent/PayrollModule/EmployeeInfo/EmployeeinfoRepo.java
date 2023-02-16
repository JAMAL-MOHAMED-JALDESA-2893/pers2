package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.EmployeeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeinfoRepo extends JpaRepository<Employeeinfo, Long> {
    @Query(value = "SELECT * FROM employeeinfo WHERE employeeinfo.id_no=:id_no", nativeQuery = true)
    Optional<Employeeinfo> findById_no(String id_no);
}

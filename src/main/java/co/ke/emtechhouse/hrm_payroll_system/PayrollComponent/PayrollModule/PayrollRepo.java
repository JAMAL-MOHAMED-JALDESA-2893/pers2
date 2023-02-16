package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
    @Query(value = "SELECT * FROM payroll WHERE payroll.period_m=:period_m AND payroll.period_y=:period_y", nativeQuery = true)
    List<Payroll> findByYearMonth(String period_m, String period_y);


    @Query(value = "SELECT * FROM payroll WHERE payroll.id_no=:id_no AND payroll.period_y=:period_y", nativeQuery = true)
    List<Payroll> findByEmpIdNoYear(String id_no,String period_y);

    @Query(value = "SELECT * FROM payroll WHERE payroll.emp_no=:emp_no AND payroll.period_m=:period_m AND payroll.period_y=:period_y", nativeQuery = true)
    Optional<Payroll> findByEmpYearMonth(Long emp_no, String period_m, String period_y);

    @Query(value = "SELECT * FROM payroll GROUP BY payroll.id_no", nativeQuery = true)
    List<Payroll> findBasicEmployeeDetails();

    @Query(value = "SELECT * FROM `payroll` WHERE payroll.is_active = 1 GROUP BY payroll.emp_no ORDER BY payroll.id DESC LIMIT  1;", nativeQuery = true)
    List<Payroll> findActiveEmployeeDetails();

    //    Get current Month salary with Join Employee and salary data
    @Query(value = "SELECT * FROM `payroll` WHERE `payroll`.`is_salary_committed` = false;", nativeQuery = true)
    List<Payroll> findCurrentUncommittedPayroll();
    @Query(value = "SELECT * FROM payroll WHERE payroll.period_m=:month AND payroll.period_y=:year AND payroll.is_salary_committed=:false", nativeQuery = true)
    List<Payroll> findCurrentUncommittedMonthSalaryDetail(String month, String year);

    //    Get current Month salary with Join Employee and salary data
    @Query(value = "SELECT * FROM payroll WHERE payroll.period_m=:month AND payroll.period_y=:year AND payroll.is_salary_committed=:commitedStatus", nativeQuery = true)
    List<Payroll> findCurrentMonthSalaryDetails(String month, String year,Boolean commitedStatus);

    //    Get current Month salary with Join Employee and salary data
    @Query(value = "SELECT * FROM payroll WHERE payroll.period_m=:month AND payroll.period_y=:year AND payroll.is_salary_committed=true", nativeQuery = true)
    List<Payroll> findCurrentCommittedMonthSalaryDetail(String month, String year);

    @Query(value = "SELECT * FROM payroll WHERE payroll.period_m=:period_m AND payroll.period_y=:period_y AND payroll.is_salary_committed=false", nativeQuery = true)
    Optional<Payroll> findUncommittedMonthSalaryDetail(String period_m, String period_y);


//    New Queries
    @Query(value = "SELECT \n" +
            "payroll.period_m as Month, \n" +
            "payroll.period_y as Year, \n" +
            "SUM(payroll.nssf) as totalNssf, \n" +
            "SUM(payroll.nhif) as totalNhif, \n" +
            "SUM(payroll.paye) as totalPaye, \n" +
            "SUM(payroll.salary) as totalGrosspay, \n" +
            "SUM(payroll.net_salary) as totalNetpay, \n" +
            "COUNT(*) as totalEmployees \n" +
            "FROM `payroll` WHERE payroll.period_m=:period_m and payroll.period_y=:period_y GROUP BY payroll.period_m;", nativeQuery = true)
    Optional<PayrollInterfaces.PayrollSummary> monthlySummary(String period_m, String period_y);

    @Query(value = "SELECT payroll.period_m as Months, SUM(payroll.salary) as salary FROM `payroll` WHERE payroll.period_y=:period_y GROUP BY payroll.period_m ORDER BY payroll.id ASC", nativeQuery = true)
    List<PayrollInterfaces.yearlyPayrollSummary> yearlyGrossSummary(String period_y);

    @Query(value = "SELECT COUNT(*) as totalEmployee, payroll.period_m as Month FROM `payroll` WHERE payroll.period_y=period_y GROUP BY payroll.period_m ORDER BY payroll.id ASC;", nativeQuery = true)
    List<PayrollInterfaces.employeeSummary> getEmployeeSummary(String period_y);

    @Query(value = "SELECT payroll.period_y as Years FROM `payroll` Group BY payroll.period_y ORDER BY payroll.id ASC", nativeQuery = true)
    List<PayrollInterfaces.payrollYears> getYears();


    @Query(value = "SELECT * FROM payroll WHERE employee_id=:employee_id", nativeQuery = true)
    List<Payroll> findByEmployee_id(@Param("employee_id") Long employee_id);

    @Query(value = "SELECT * FROM payroll WHERE employee_id=:employee_id", nativeQuery = true)
    List<Payroll> findByEmployee_idLastSixMonths(@Param("employee_id") Long employee_id);
}

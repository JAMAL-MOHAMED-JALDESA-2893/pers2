package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import java.util.List;
import java.util.Optional;

import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.TotalInterafaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeById(Long id);

    Optional<EmployeeEducation> findEmployeeEducationById(Long id);

    void deleteEmployeeById(Long id);

//    @Query(value = "SELECT * FROM employee WHERE is_approved = 'false'", nativeQuery = true)
//    Optional<EmployeeEntity> findSaccoEnrolledEmployee();

    @Query(value = "SELECT * FROM employee WHERE department_id = :dp_Id", nativeQuery = true)
    List<Employee> findByDepartment(Long dp_Id);

    @Query(value = "SELECT employee.first_name as FirstName, employee.middle_name as MiddleName, employee.last_name as LastName, department.department_name as DepartmentName from department JOIN employee on employee.department_id = department.id WHERE employee.employee_status=:employee_status", nativeQuery = true)
    List<EmployeeDepartmentDetailed> filterByEmployeeStatus(String employee_status);

//    count all employees
    @Query(value = "SELECT COUNT(*) FROM employee", nativeQuery = true)
    public long countEmployee();
    //    count closed salary
    @Query(value = "SELECT COUNT(*) FROM employee WHERE employee_status = 'Active' AND is_salary_closed = true", nativeQuery = true)
    public long countClosedEmployee();
//    Fetch New Un Approved Employees
    @Query(value = "SELECT * FROM employee WHERE is_approved = 'false'", nativeQuery = true)
    List<Employee> findunAprovedEmployees();

//    Fetch Active
    @Query(value = "SELECT * FROM employee WHERE employee_status = 'Active'", nativeQuery = true)
    List<Employee> findActiveEmployees();

    //    Fetch Active and has dummy salary
    @Query(value = "SELECT * FROM employee WHERE employee_status = 'Active' AND has_dummy_salary = false", nativeQuery = true)
    List<Employee> findActiveEmployeesNoDummySalary();

    //    Fetch Active and closed salary Employees
    @Query(value = "SELECT * FROM employee WHERE employee_status = 'Active' AND is_deleted = false", nativeQuery = true)
    List<Employee> findActiveSalariedEmployees();
    //    Fetch Active and closed salary Employees
    @Query(value = "SELECT * FROM employee WHERE employee_status = 'Active' ", nativeQuery = true)
    Optional<Employee> findCurrentActiveEmployees();
//    Fetch InActive Employees
    @Query(value = "SELECT * FROM employee WHERE employee_status = 'Inactive' AND is_deleted = false ", nativeQuery = true)
    List<Employee> findInActiveEmployees();
//    Fetch Trashed Employees
    @Query(value = "SELECT * FROM employee WHERE employee.is_deleted = true AND permanently_cleared = false", nativeQuery = true)
    List<Employee> findTrashedEmployees();

//    find employee detailed data
//    Join Employee and salary data
    @Query(value = "SELECT employee.id AS EmployeeId employee.national_id as NationalId, employee.job_group as JobGroup, employee.basic_salary as Basic_salary, employee.first_name AS FirstName, employee.middle_name AS MiddleName, employee.last_name as LastName, employee.personal_phone AS PersonalPhone, employee.bank_account AS BankAccount, employee.bank_name AS BankName, employee.kra_no as KraNo, employee.nhif_no as NhifNo, employee.nssf_no as NssfNo, salary.id as SalaryId, salary.paye_deductions as PayeDeductions, salary.nssf_deductions as NssfDeductions,salary.nhif_deductions as NhifDeductions,salary.helb_deductions as HelbDeductions, salary.net_pay as NetPay, salary.created_at as CreatedAt,salary.month as Month,salary.year as Year, salary.paid as Paid from employee LEFT OUTER join salary on employee.id = salary.employee_id WHERE employee.id = :id", nativeQuery = true)
    List<TotalInterafaces.SalaryDetail> findEmployeeSalaryDetail(Long id);

//    fetch unenrolled employees and is active
    @Query(value = "SELECT * FROM employee WHERE  is_evaluation_enrolled = false AND employee_status = 'Active'", nativeQuery = true)
    List<Employee> findUnenrolledActiveEmployees();


    public interface EmployeeDepartmentDetailed {
        String getFirstName();
        String getMiddleName();
        String getLastName();
        String getNationalId();
        String getPersonalPhone();
        String getBankName();
        String getBankAccount();
        String getKraNo();
        String getNssfNo();
        String getNhifNo();
        Double getGross_salary();
        String getJobGroup();

        String getDepartmentName();

    }


//    long countCoursesByStudentId(@Param("id") long id);
//SELECT count(*) FROM salary WHERE (salary.month = 'AUGUST' AND salary.year='2021')

//    @Query ("SELECT COUNT(*) FROM employee")
//    List<EmployeeEntity> countMemberships();
}




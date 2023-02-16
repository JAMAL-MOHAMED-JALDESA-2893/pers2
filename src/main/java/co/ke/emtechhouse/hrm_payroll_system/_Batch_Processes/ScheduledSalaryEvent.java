package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.Activity;
import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.ActivityRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveService;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.KraRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.NhifRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.NssfRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary.AdvanceSalaryRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollGenerator;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoRepo;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveRepo;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType.LeaveTypeService;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_customRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Component
@Slf4j
public class ScheduledSalaryEvent {
    private final Allowance_customRepo allowance_customRepo;
    private final  AdvanceSalaryRepo advanceSalaryRepo;
    private final  SaccoRepo saccoRepo;
    private final  ActivityRepo activityRepo;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeService leaveTypeService;
    private final EmployeeLeaveService employeeLeaveService;
    private final EmployeeLeaveRepo employeeLeaveRepo;
    private final NhifRepo nhifRepo;
    private final NssfRepo nssfRepo;
    private final KraRepo kraRepo;
    private final PayrollRepo payrollRepo;
    private final PayrollGenerator payrollGenerator;
    public ScheduledSalaryEvent(Allowance_customRepo allowance_customRepo, AdvanceSalaryRepo advanceSalaryRepo, SaccoRepo saccoRepo, ActivityRepo activityRepo, EmployeeRepository employeeRepository, LeaveTypeService leaveTypeService, EmployeeLeaveService employeeLeaveService, EmployeeLeaveRepo employeeLeaveRepo, NhifRepo nhifRepo, NssfRepo nssfRepo, KraRepo kraRepo, PayrollRepo payrollRepo, PayrollGenerator payrollGenerator) {
        this.allowance_customRepo = allowance_customRepo;
        this.advanceSalaryRepo = advanceSalaryRepo;
        this.saccoRepo = saccoRepo;
        this.activityRepo = activityRepo;
        this.employeeRepository = employeeRepository;
        this.leaveTypeService = leaveTypeService;
        this.employeeLeaveService = employeeLeaveService;
        this.employeeLeaveRepo = employeeLeaveRepo;
        this.nhifRepo = nhifRepo;
        this.nssfRepo = nssfRepo;
        this.kraRepo = kraRepo;
        this.payrollRepo = payrollRepo;
        this.payrollGenerator = payrollGenerator;
    }
    //    TODO: Open the salary  on 1st of every month
//@Scheduled(cron="${cron.exp_open_salary}")
//    @Scheduled(fixedRate = 7000L)
    public void openSalary(){
        try {
            List<Employee> employees = employeeRepository.findActiveEmployees();
            for (int i =0; i<employees.size(); i++){
                Boolean is_salary_closed = false;
                Employee _employeeEntity = employees.get(i);
                _employeeEntity.setIs_salary_closed(is_salary_closed);
                _employeeEntity.setHas_dummy_salary(false);
                employeeRepository.save(_employeeEntity);
                String employee_name = employees.get(i).getFirstName();
                String middle_name = employees.get(i).getMiddleName();
                String last_name = employees.get(i).getLastName();
                Activity newActivity = new Activity();
                String activity_name = "Open Salary for " + employee_name + " " + middle_name + " " + last_name;
                String activity_category = "Batch Process";
                newActivity.setActivity_name(activity_name);
                newActivity.setActivity_category(activity_category);
                activityRepo.save(newActivity);
                log.info("*****************All active Employee Salary Opened!*****************");
            }
        } catch (Exception e) {
            log.info("Open Salary Error: {} "+e);
        }
    }
    //    TODO: Generate a dummy salary report of that particular month
//@Scheduled(cron="${cron.exp_generate_salary}")
//        @Scheduled(fixedRate = 7200L)
    public ResponseEntity<?> addNewSalary(){
        try {
            payrollGenerator.genPayroll();
        } catch (Exception e) {
            log.info("Add Salary Error: {} "+e);
        }
        return null;
    }
    //    TODO: Close the salary on date 26th of every month)
//@Scheduled(cron="${cron.exp_close_salary}")
//        @Scheduled(fixedRate = 7400L)
    public void closedSalary(){
        try {
            List<Employee> employees = employeeRepository.findActiveEmployees();
            for (int i =0; i<employees.size(); i++){
                Boolean is_salary_closed = true;
                Employee _employeeEntity = employees.get(i);
                _employeeEntity.setIs_salary_closed(is_salary_closed);
                employeeRepository.save(_employeeEntity);
                log.info("*****************Salary Closed*****************");
            }
        } catch (Exception e) {
            log.info("Close Salary Error: {} "+e);
        }
    }
    public void commitSalary(){
        try {
            LocalDateTime currentData = LocalDateTime.now();
            String period_m = currentData.getMonth().toString();
            String period_y = String.valueOf(currentData.getYear());
            payrollGenerator.approvePayroll(period_m, period_y);
            log.info("*****************Salary Closed*****************");
        } catch (Exception e) {
            log.info("Commit Salary Error: {} "+e);
        }

    }

}

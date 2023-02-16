package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ResignationamtRepo extends JpaRepository<Resignationamt, Long> {
    Optional<Resignationamt> findResignationById(Long id);
    void deleteClearenceById(Long id);

    @Query(value = "SELECT * FROM `resignationamt` WHERE `resignationamt`.`employee_id`=:employee_id", nativeQuery = true)
    Optional<Resignationamt> findResignationByEmployeeId(Long employee_id);

    @Query(value = "SELECT employee_entity.first_name as Firstname, employee_entity.middle_name as Middlename, employee_entity.last_name as Lastname, employee_entity.national_id as Nationalid, employee_entity.personal_phone as Personalphone, employee_entity.bank_name as Bankname, employee_entity.bank_account as Bankaccount, employee_entity.kra_no as Krano, employee_entity.nssf_no as Nssfno, employee_entity.nhif_no as Nhifno, employee_entity.gross_salary as Grosssalary, employee_entity.occupation as Occupation, department.department_name as Departmentname, \n" +
            "resignationamt.*, \n" +
            "clearence.clearence_status as clearence_status, clearence.exit_date as exit_date\n" +
            "from department join employee_entity on department.id = employee_entity.department_id join resignationamt on resignationamt.employee_id = employee_entity.id join clearence on clearence.employee_id = employee_entity.id;", nativeQuery = true)
    List<ResignationSalaryDetail> findResignationSalaryDetail();

    interface ResignationSalaryDetail{
        String getFirstname();
        String getMiddlename();
        String getLastname();
        String getNationalid();
        String getPersonalphone();
        String getBankname();
        String getBankaccount();
        String getKrano();
        String getNssfno();
        String getNhifno();
        Double getGrosssalary();
        String getOccupation();

//        Department Details
        String getDepartmentname();

        //      Salary Table
        Long getId();
        String getResignation_notice();
        Double getMonthly_basic_salary();
        Double getBasic_salary();
        Double getTotal_non_cash_benefit();
        Double getValue_of_quarters();
        Double getGross_pay();
        Double getDcrs_e1();
        Double getDcrs_actual_e2();
        Double getDcrs_fixed_e3();
        Double getOwner_occupied_interests();
        Double getRetirement_and_owner_occupied_interests();
        Double getChargeable_pay();
        Double getTax_charged();
        Double getTotal_relief();
        Double getPension();
        Double getNhif_deductions();
        Double getNssf_deductions();
        Double getPaye_deductions();
        Double getHelb_deductions();
        Double getLeave_deduction();
        Double getNet_pay();
        Long getEmployee_id();
        Long getDepartment_id();
        String getMonth() ;
        String getYear();
        Boolean getDeleted();
        Double  getAllowance_custom();
        Double  getAllowance_per_deam();
        Double getAllowance_field();
        Double  getAllowance_overtime();
        Double getTaxable_income();
        Double getRelief_personal();
        Double getRelief_insurance();
        Double getAdvanced_deductions();
        Double getSacco_contributions();
        Boolean getPaid();
        Boolean getIs_commited();
        Boolean getIs_pay_btn_enabled();
        Boolean getIs_approved();
        LocalDateTime getPaid_on();
        LocalDateTime getCreated_at();
        LocalDateTime getUpdated_at();
        LocalDateTime getDeleted_at();

        //      Clearence Table
//        String getReason();
//        String getIs_supervisor_approved();
//        String getSupervisor_rejection_reason();
//        String getIs_hr_approved();
//        String getHr_rejection_reason();
//        String getIs_Director_approved();
//        String getDirector_rejection_reason();
        String getClearence_status();
//        String getMonitor_from_date();
        String getExit_date();
//        String getSalary();
//        String getIs_cleared();

    }

}



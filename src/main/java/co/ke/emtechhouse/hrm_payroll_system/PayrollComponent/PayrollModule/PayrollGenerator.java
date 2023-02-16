package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.Activity;
import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.ActivityRepo;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollCalculatorClasses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PayrollGenerator {
    private final Advancedeductions advancedeductions;
    private final Allowancesdeductions allowancesdeductions;
    private final Leavedeductions leavedeductions;
    private final NetpayBeforeTaxDeductions netpayBeforeTaxDeductions;
    private final NetpayAfterTaxDeductions netpayAfterTaxDeductions;
    private final Nhifdeductions nhifdeductions;
    private final Nssfdeductions nssfdeductions;
    private final Payedeductions payedeductions;
    private final Saccodeductions saccodeductions;
    private final PayrollRepo payrollRepo;
    private final EmployeeRepository employeeRepository;
    private final ActivityRepo activityRepo;
    private final NonCashBenefitsCalculator nonCashBenefitsCalculator;

    private final DcrsCalculator dcrsCalculator;

    private final SalaryCalculator salaryCalculator;
    private final DeductionCalculator deductionCalculator;

    public PayrollGenerator(Advancedeductions advancedeductions, Allowancesdeductions allowancesdeductions, Leavedeductions leavedeductions, NetpayBeforeTaxDeductions netpayBeforeTaxDeductions, NetpayAfterTaxDeductions netpayAfterTaxDeductions, Nhifdeductions nhifdeductions, Nssfdeductions nssfdeductions, Payedeductions payedeductions, Saccodeductions saccodeductions, PayrollRepo payrollRepo, EmployeeRepository employeeRepository, ActivityRepo activityRepo, NonCashBenefitsCalculator nonCashBenefitsCalculator, DcrsCalculator dcrsCalculator, SalaryCalculator salaryCalculator, DeductionCalculator deductionCalculator) {
        this.advancedeductions = advancedeductions;
        this.allowancesdeductions = allowancesdeductions;
        this.leavedeductions = leavedeductions;
        this.netpayBeforeTaxDeductions = netpayBeforeTaxDeductions;
        this.netpayAfterTaxDeductions = netpayAfterTaxDeductions;
        this.nhifdeductions = nhifdeductions;
        this.nssfdeductions = nssfdeductions;
        this.payedeductions = payedeductions;
        this.saccodeductions = saccodeductions;
        this.payrollRepo = payrollRepo;
        this.employeeRepository = employeeRepository;
        this.activityRepo = activityRepo;
        this.nonCashBenefitsCalculator = nonCashBenefitsCalculator;
        this.dcrsCalculator = dcrsCalculator;
        this.salaryCalculator = salaryCalculator;
        this.deductionCalculator = deductionCalculator;
    }

    /*
     *
     * Function To get For Each Employee Details
     *
     */
    public void genPayroll(){
        List<Employee> allActiveEmployees = employeeRepository.findActiveEmployees();
        if (allActiveEmployees.size()>0){
            for (int i = 0; i<allActiveEmployees.size(); i++){
//                Loop Through Each and Call to set Payroll
                Employee employee = allActiveEmployees.get(i);
                Long emp_no = employee.getId();
                String first_name = employee.getFirstName();
                String other_names = employee.getMiddleName()+" "+employee.getLastName();
                String id_no = employee.getNationalId();
                String acct_no = employee.getBankAccount();
                String bank = employee.getBankName();
                String nssf_no = employee.getNssfNo();
                String nhif_no = employee.getNhifNo();
                String pin_no = employee.getKraNo();
                String period_m = LocalDateTime.now().getMonth().toString();
                String period_y = String.valueOf(LocalDateTime.now().getYear());
//                Double Salary = employee.getGross_salary();

                Double basic_salary= employee.getBasic_salary();

                Double helb_deductions = 0.00;
                Double Total_non_cash_benefit = employee.getTotal_non_cash_benefit();
                Double Dcrs_actual_e2 = employee.getDcrs_actual_e2();
                Double Owner_occupied_interests = employee.getOwner_occupied_interests();
                Double Value_of_quarters = employee.getValue_of_quarters();
                Double Total_relief = employee.getInsurance_relief();
                PayrollGenerator newPayroll = new PayrollGenerator(advancedeductions, allowancesdeductions, leavedeductions, netpayBeforeTaxDeductions, netpayAfterTaxDeductions, nhifdeductions, nssfdeductions, payedeductions, saccodeductions, payrollRepo, employeeRepository, activityRepo, nonCashBenefitsCalculator, dcrsCalculator, salaryCalculator, deductionCalculator);
                newPayroll.setSalary(emp_no,first_name,other_names,id_no,acct_no,bank,nssf_no,nhif_no,pin_no,period_m,
                        period_y,helb_deductions,Total_non_cash_benefit,Dcrs_actual_e2,Owner_occupied_interests,Total_relief, basic_salary);
            }
        }
    }
    /*
     *
     * Function To Set Payroll
     *
     */
    public ResponseEntity<?> setSalary(
            Long emp_no,
            String first_name,
            String other_names,
            String id_no,
            String acct_no,
            String bank,
            String nssf_no,
            String nhif_no,
            String pin_no,
            String period_m,
            String period_y,
            Double helb_deductions,
            Double Total_non_cash_benefit,
            Double Dcrs_actual_e2,
            Double Owner_occupied_interests,
            Double Total_relief,
            Double basic_salary
    ) {
        try {
        //Double taxableDeductionsAllowance = allowancesdeductions.getTaxableAllowancededuction(emp_no);
        Double taxableDeductionsAllowance = allowancesdeductions.getTaxableAllowancededuction1(emp_no);
        //Double nonTaxableDeductionsAllowance = allowancesdeductions.getNonTaxableAllowancededuction(emp_no);
        Double nonTaxableDeductionsAllowance = allowancesdeductions.getNonTaxableAllowancededuction1(emp_no);
        Double leaveDeductions = leavedeductions.getNhifdeduction(emp_no, basic_salary);
        Double nhifDeductions = nhifdeductions.getNhifdeduction(basic_salary);
        Double nssfDeductions = nssfdeductions.getNssfdeduction(basic_salary);
        Double advanceSalary = advancedeductions.getAdvancededuction(emp_no);

        Double dcrs1=dcrsCalculator.calculateDcrsE1(basic_salary);
        Double dcrs2=dcrsCalculator.calculateDcrsE2(basic_salary);
        Double dcrs3=dcrsCalculator.calculateDcrsE3();
        Double ownerOccupiedInterest=dcrsCalculator.calculateOwnerOccupiedInterest();
        Double retirement_and_owner_occupied_interests= dcrsCalculator.calcRitirementContrAndOwnerOccupiedInterest(dcrs1,dcrs2,dcrs3,ownerOccupiedInterest);

        Double taxableNonCashBenefits=nonCashBenefitsCalculator.getTaxableNonCashBenefits(emp_no);
        Double nonTaxableNonCashBenefits;


        //calculating gross salary i.e basic salary + all taxable allowances
        Double gross_salary= salaryCalculator.calculateGrossSalary(basic_salary,taxableNonCashBenefits,taxableDeductionsAllowance);
            Double gross_salary_without_non_cash_benefits= salaryCalculator.calculateGrossSalaryWithoutNonCashBenefits(basic_salary,taxableDeductionsAllowance);
        Double taxable_salary= salaryCalculator.calculateTaxableSalary(gross_salary,0.00,nssfDeductions);
        Double payeDeductions = payedeductions.getPayededuction(0.0,basic_salary,gross_salary,0.0);
        Double taxRelief=payedeductions.getPersonalRelief();
        //Double payeDeductions = payedeductions.getPayededuction(gross_salary, Total_non_cash_benefit, Dcrs_actual_e2, Owner_occupied_interests, Value_of_quarters, Total_relief, taxableDeductionsAllowance, nssfDeductions);
       // Double netPayBeforeDeductions = netpayBeforeTaxDeductions.getNetpayBeforeTaxDeductions(basic_salary, nhifDeductions, payeDeductions, leaveDeductions);
       // Double saccoDeductions = saccodeductions.getSaccodeduction(emp_no, netPayBeforeDeductions);
        //Double netPayAfterTaxDeductions = netpayAfterTaxDeductions.getNetpayAfterTaxDeductions(taxableDeductionsAllowance, nonTaxableDeductionsAllowance, advanceSalary, saccoDeductions);
        Double taxableIncome= salaryCalculator.calculateTaxableSalary(gross_salary,0.0, nssfDeductions);
        Double netpayAfterTaxDeductionsSalary= salaryCalculator.calculateNetSalaryAfterTax(gross_salary_without_non_cash_benefits, payeDeductions, 0.0);
        log.info("netpayAfterTaxDeductionsSalary-----"+netpayAfterTaxDeductionsSalary.toString());
        Double otherDeductions=deductionCalculator.getAllActiveDeductions(emp_no);
        Double netSalary=netpayAfterTaxDeductionsSalary-otherDeductions-nhifDeductions-nssfDeductions;
        //Double total_deductions = basic_salary - netPayBeforeDeductions;
            //  check if salary has been generated
        Optional<Payroll> payroll1 = payrollRepo.findByEmpYearMonth(emp_no,period_m,period_y);
        if (payroll1.isPresent()){
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Already Generated for this month!" + period_m));
        }else{
        Payroll newPayroll = new Payroll();
        newPayroll.setEmployee_id(emp_no);
        newPayroll.setFirst_name(first_name);
        newPayroll.setOther_names(other_names);
        newPayroll.setId_no(id_no);
        newPayroll.setAcct_no(acct_no);
        newPayroll.setBank(bank);
        newPayroll.setNssf_no(nssf_no);
        newPayroll.setNhif_no(nhif_no);
        newPayroll.setPin_no(pin_no);
        newPayroll.setPeriod_m(period_m);
        newPayroll.setPeriod_y(period_y);
        newPayroll.setSalary(basic_salary);
        newPayroll.setNssf(nssfDeductions);
        newPayroll.setNhif(nhifDeductions);
        newPayroll.setPaye(payeDeductions);
        newPayroll.setHelb(helb_deductions);
        newPayroll.setTotal_deductions_net_salary(otherDeductions);
        newPayroll.setNet_salary(netSalary);
        newPayroll.setDeleted_flag("N");
        newPayroll.setTotal_non_cash_benefit(taxableNonCashBenefits);
        newPayroll.setValue_of_quarters(taxableDeductionsAllowance);
        //newPayroll.setGross_pay(Salary);
        // setting gross pay
        newPayroll.setGross_pay(gross_salary);
        newPayroll.setDcrs_e1(dcrs1);
        newPayroll.setDcrs_actual_e2(dcrs2);
        newPayroll.setDcrs_fixed_e3(dcrs3);
        newPayroll.setOwner_occupied_interests(ownerOccupiedInterest);
        //
        newPayroll.setRetirement_and_owner_occupied_interests(retirement_and_owner_occupied_interests);
        newPayroll.setChargeable_pay(taxableIncome);
        newPayroll.setTax_charged(taxRelief+payeDeductions);
        newPayroll.setTotal_relief(taxRelief);
        payrollRepo.save(newPayroll);
//        Save Activity Log
        Activity newActivity = new Activity();
        String activity_name = "Generate Payroll For: "+first_name +" "+other_names;
        newActivity.setActivity_name(activity_name);
        newActivity.setActivity_category("Payroll Generation");
        activityRepo.save(newActivity);
                return new ResponseEntity<>(newPayroll, HttpStatus.CREATED);
            }
    }catch (Exception e) {
        log.info("Set Salary Error {} "+e);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND );
    }


    public void approvePayroll(String period_m, String period_y){
        Optional<Payroll> payroll = payrollRepo.findUncommittedMonthSalaryDetail(period_m,period_y);
        if (payroll.isPresent()){
            Payroll _payroll = payroll.get();
            _payroll.setIs_salary_committed(true);
            _payroll.setUpdated_at(LocalDateTime.now());
            payrollRepo.save(_payroll);
        }
    }
}

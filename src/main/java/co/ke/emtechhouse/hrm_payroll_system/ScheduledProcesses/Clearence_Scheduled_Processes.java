package co.ke.emtechhouse.hrm_payroll_system.ScheduledProcesses;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.Users;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.UsersRepository;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.Kra;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.KraRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.Nhif;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.NhifRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.Nssf;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.NssfRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.Clearence;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ClearenceRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ClearenceService;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.Resignationamt;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance.ResignationamtService;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveService;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType.LeaveTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Clearence_Scheduled_Processes {
    private final ClearenceRepo clearenceRepo;
    private final EmployeeRepository employeeRepository;
    private final ClearenceService clearenceService;
    private final LeaveTypeService leaveTypeService;
    private final EmployeeLeaveService employeeLeaveService;
    private final NhifRepo nhifRepo;
    private final NssfRepo nssfRepo;
    private final KraRepo kraRepo;
    private final UsersRepository userRepository;
    private final ResignationamtService resignationamtService;
    public Clearence_Scheduled_Processes(ClearenceRepo clearenceRepo, EmployeeRepository employeeRepository, ClearenceService clearenceService, LeaveTypeService leaveTypeService, EmployeeLeaveService employeeLeaveService, NhifRepo nhifRepo, NssfRepo nssfRepo, KraRepo kraRepo, UsersRepository userRepository, ResignationamtService resignationamtService) {
        this.clearenceRepo = clearenceRepo;
        this.employeeRepository = employeeRepository;
        this.clearenceService = clearenceService;
        this.leaveTypeService = leaveTypeService;
        this.employeeLeaveService = employeeLeaveService;
        this.nhifRepo = nhifRepo;
        this.nssfRepo = nssfRepo;
        this.kraRepo = kraRepo;
        this.userRepository = userRepository;
        this.resignationamtService = resignationamtService;
    }
    //        @Scheduled(fixedRate = 86_400_000)// One day
//        @Scheduled(fixedRate = 2000L) //for testing 2 min
        public void cleareEmployee() {
            Date my_date  = new Date();
            SimpleDateFormat  fm_date = new SimpleDateFormat("dd/MM/yyyy");
            fm_date.format(my_date);
            String current_str_date = fm_date.format(my_date);
//            loop through clearence table which has not been cleared
            List<Clearence> employees = clearenceRepo.findUnCleared();
            if (employees.size() > 0) {
//                loop through all the employees
                for (int i = 0; i < employees.size(); i++) {
//                    get individual element
                    Clearence employee_exiting = employees.get(i);
                    LocalDateTime exit_date = employee_exiting.getExit_date();
                    String exit_str_date = fm_date.format(exit_date);
                    if (current_str_date.equalsIgnoreCase(exit_str_date)) {
//                        get employee id from clearence table > to be used to get the employee data
                        Long employee_id = employee_exiting.getEmployee_id();

//                        calculate the salary and save in salary table
//                            fetch the Nhif Deduction
//                          find employee by id
                        Optional<Employee> employee = employeeRepository.findEmployeeById(employee_id);
                        if (employee.isPresent()){

                            Employee _employee = employee.get();
//                            get currentbasic salary as current basic salary
                            double current_gross_salary = _employee.getGross_salary();
                            double daily_pay = current_gross_salary/30;
//                            get the exit date on the date of exit of the employee
                            LocalDate currentDate= LocalDate.now();
                            // Get day from date
                            int days = currentDate.getDayOfMonth();
//                            get total amount
                            double basic_salary = daily_pay * days;
                            double total_non_cash_benefit = _employee.getTotal_non_cash_benefit();
                            double value_of_quarters = _employee.getValue_of_quarters();
                            double gross_pay = basic_salary + total_non_cash_benefit + value_of_quarters;

                            Double dcrs_e1 = (0.3 * basic_salary);
                            Double dcrs_actual_e2 = _employee.getDcrs_actual_e2();
                            Double dcrs_fixed_e3 = 20000.00;

                            Double smallest_dcrs = Math.min(dcrs_e1, Math.min(dcrs_actual_e2, dcrs_fixed_e3));

//            get Owner Occupied Interest
                            Double owner_occupied_interests = _employee.getOwner_occupied_interests();

//            Calculate the retirement & owner occupied interest
                            Double retirement_and_owner_occupied_interests = smallest_dcrs + owner_occupied_interests;

                            Double chargeable_pay = gross_pay - retirement_and_owner_occupied_interests;
                            Double personalRelief = _employee.getPersonal_relief();
                            Double insuranceRelief = _employee.getInsurance_relief();
                            Double total_relief = personalRelief + insuranceRelief;

                            //           Algorithim for PAYE
//            *******************************************************************
                            double taxRemainder = 0.00;
                            double firstTax = 0.00;
                            double secondTax = 0.00;
                            double thirdTax = 0.00;
                            double totalTax = 0.00;
                            double  taxDeductions = 0;
                            double tax_charged = 0;
                            double paye = 0;
                            //get personal relief
                            Kra kra_one_query = kraRepo.findKra();
                            Double personal_relief_monthly = kra_one_query.getPersonal_relief_monthly();
                            Double personal_relief_annually = kra_one_query.getPersonal_relief_annualy();

//            get Tax Band 1
                            Kra tax_band_1 = kraRepo.findTaxBand1Kra();
                            Double monthlyAmount1 = tax_band_1.getAmount_monthly();
                            Double rate_taxt_band_1 = tax_band_1.getRate()/100;
//            get Tax Band 2
                            Kra tax_band_2 = kraRepo.findTaxBand2Kra();
                            Double monthlyAmount2 = tax_band_2.getAmount_monthly();
                            Double rate_tax_band_2 = tax_band_2.getRate()/100;
//            get Tax Band 3
                            Kra tax_band_3 = kraRepo.findTaxBand3Kra();
                            Double monthlyAmount3 = tax_band_3.getAmount_monthly();
                            Double rate_tax_band_3 = tax_band_3.getRate()/100;
                            if (chargeable_pay>monthlyAmount1){
//                firstTax
                                firstTax = monthlyAmount1 * rate_taxt_band_1;
                                double firstRemainder = chargeable_pay - rate_taxt_band_1;
                                if (firstRemainder < monthlyAmount2 ){
                                    secondTax = firstRemainder * rate_tax_band_2;
                                }else {
                                    secondTax = monthlyAmount2 * rate_tax_band_2;
                                }
                                double thirdRemainder = chargeable_pay - monthlyAmount1 - monthlyAmount2;

                                thirdTax = thirdRemainder * rate_tax_band_3;

                                tax_charged = (firstTax + secondTax + thirdTax );

                                paye = tax_charged - total_relief;

                            }else {
                                taxDeductions = 0.00;
                            }
                            double nhifDeductions = 0.00;
//            ****************NHif Wrapper****************
//            get All_Min_threshold
                            Nhif nhif_max_query = nhifRepo.findHighestThreshold();
                            Double highest_threshold = nhif_max_query.getMax_threshold();
                            Double Highest_deductions = nhif_max_query.getNhif_deduction();
                            if(gross_pay >=0 && gross_pay < highest_threshold){
//                get actual deductions
                                Double grosspay = gross_pay;
                                Nhif rate_nhif_deductions = nhifRepo.findNhifDeduction(grosspay);
                                nhifDeductions = rate_nhif_deductions.getNhif_deduction();
                            }else if (gross_pay>= highest_threshold){
                                nhifDeductions =  Highest_deductions;
                            }
//            Algorithim for NSSF Calculations
//            *************************************************************
//            360 shillings to a maximum of 1080 shillings
                            double nssfDeductions = 0;
                            Optional<Nssf> nssf_data = nssfRepo.findNssf();
                            Double min_nssf = nssf_data.get().getMin_nssf();
                            Double max_nssf = nssf_data.get().getMax_nssf();
                            Double nssf_total_rate = nssf_data.get().getTotal_nssf_rate();
                            double nssfRate = nssf_total_rate * gross_pay;
                            if(nssfRate > max_nssf){
                                nssfDeductions = max_nssf;
                            }else if(nssfRate >= min_nssf && nssfRate < max_nssf ){
                                nssfDeductions = nssfRate;
                            }else if(nssfRate < min_nssf){
                                nssfDeductions = min_nssf;
                            }
//            Net pay calculations
//           ***************************************************************
                            double netPay = (gross_pay - nhifDeductions - nssfDeductions - paye);
//                            LocalDate currentDate= LocalDate.now();
//               // from date
                            // Get day from date
                            int day = currentDate.getDayOfMonth();
                            // Get month from date
                            Month month = currentDate.getMonth();
                            String getMonth = month.toString();
                            // Get year from date
                            int year = currentDate.getYear();
//                            Safe salary in Resignation amount bool
                            Resignationamt salary = new Resignationamt();
//            All entries
//            salary.setBasic_pay(gross_pay);
                            salary.setBasic_salary(basic_salary);
//            Pension notyet defined
                            salary.setPension(0.00);
                            salary.setEmployee_id(_employee.getId());
                            salary.setDepartment_id(_employee.getDepartmentId());
                            salary.setTotal_non_cash_benefit(total_non_cash_benefit);
                            salary.setValue_of_quarters(value_of_quarters);
                            salary.setGross_pay(gross_pay);
                            salary.setDcrs_e1(dcrs_e1);
                            salary.setDcrs_actual_e2(_employee.getDcrs_actual_e2());
                            salary.setDcrs_fixed_e3(dcrs_fixed_e3);
                            salary.setOwner_occupied_interests(owner_occupied_interests);
                            salary.setRetirement_and_owner_occupied_interests(retirement_and_owner_occupied_interests);
                            salary.setChargeable_pay(chargeable_pay);
                            salary.setTotal_relief(total_relief);
                            salary.setTax_charged(tax_charged);
                            salary.setPaye_deductions(paye);
                            salary.setNet_pay(netPay);
                            salary.setNhif_deductions(nhifDeductions);
                            salary.setNssf_deductions(nssfDeductions);
//            salary.setPaye_deductions(taxDeductions);
                            salary.setLeave_deduction(0.00);
                            salary.setHelb_deductions(0.00);
                            salary.setMonth(getMonth);
                            salary.setYear(String.valueOf(year));
//             updated to gross_pay
                            salary.setTaxable_income(gross_pay);
                            salary.setRelief_insurance(0.0);
                            salary.setAllowance_field(0.00);
                            salary.setAllowance_per_deam(0.00);
                            salary.setAllowance_overtime(0.00);
                            Resignationamt newSalary = resignationamtService.addResignationamt(salary);
//                  Update status to be inactive
                            Boolean is_salary_closed = true;
                            Boolean is_closed = true;
                            Boolean is_deleted = true;
                            Boolean permanently_cleared = true;
                            Boolean is_activated = false;
                            Boolean is_employee_approved = false;
                            Boolean is_approved = false;
                            Boolean have_account = false;
                            String employee_status = "Inactive";
                            _employee.setIs_salary_closed(is_salary_closed);
//                            _employee.setIs_closed(is_closed);
                            _employee.setIs_deleted(is_deleted);
                            _employee.setPermanently_cleared(permanently_cleared);
                            _employee.setIs_activated(is_activated);
//                            _employee.setIs_employee_approved(is_employee_approved);
                            _employee.setIs_approved(is_approved);
                            _employee.setHave_account(have_account);
                            _employee.setEmployee_status(employee_status);
                            employeeRepository.save(_employee);
//                        called to remove employee account
//                    find account by employee email
                            String employee_email = _employee.getPersonalEmail();
//                    get the account id and use jpa to remove
                            Optional<Users> user = userRepository.findByEmail(employee_email);
                            if (user.isPresent()){
                                Users _user = user.get();
//                        Delete by id
                                userRepository.deleteById(_user.getSn());
                            }else{
                            }
                            Boolean is_cleared = true;
                            employee_exiting.setIs_cleared(is_cleared);
                            employee_exiting.setUpdated_at(LocalDateTime.now());
                            clearenceRepo.save(employee_exiting);
                        }
                    }

                }
            }
        }

}

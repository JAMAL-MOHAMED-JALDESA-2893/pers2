package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance;

import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.ActivityRepo;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeave;
import co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.EmployeeLeave.EmployeeLeaveRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.Emailconfig;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.EmailconfigRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.EmailconfigService;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.Kra;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations.KraRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.Nhif;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations.NhifRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.Nssf;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations.NssfRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary.AdvanceSalary;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary.AdvanceSalaryRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_custom;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances.Allowance_customRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.Sacco;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoRepo;
import co.ke.emtechhouse.hrm_payroll_system._Batch_Processes.Batch_processesRepo;
import com.sun.istack.ByteArrayDataSource;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/resignation/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class ResignationController {
    @Autowired
    private ResignationamtRepo resignationamtRepo;
    @Autowired
    private ResignationamtService resignationamtService;
    @Autowired
    private Allowance_customRepo allowance_customRepo;
    @Autowired
    private AdvanceSalaryRepo advanceSalaryRepo;
    @Autowired
    private SaccoRepo saccoRepo;
    @Autowired
    private ActivityRepo activityRepo;
    @Autowired
    private EmployeeLeaveRepo employeeLeaveRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private KraRepo kraRepo;
    @Autowired
    private NhifRepo nhifRepo;
    @Autowired
    private NssfRepo nssfRepo;
    @Autowired
    private ResignationController resignationController;

    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${reports_absolute_path}")
    private String files_path;
    @Value("${company_logo_path}")
    private String company_logo_path;
    @Value("${image_banner}")
    private String banner_path;
    @Value("${from_mail}")
    private String from_mail;
    @Value("${cc_mail}")
    private String cc_mail;
    private final JavaMailSender mailSender;
    private final EmployeeService employeeService;
    private final EmailconfigService emailconfigService;
    private final EmailconfigRepo emailconfigRepo;
    private final Batch_processesRepo batch_processesRepo;

    LocalDate currentDate= LocalDate.now();

    public ResignationController(JavaMailSender mailSender, EmployeeService employeeService, EmailconfigService emailconfigService,  EmailconfigRepo emailconfigRepo, Batch_processesRepo batch_processesRepo) {
        this.mailSender = mailSender;
        this.employeeService = employeeService;
        this.emailconfigService = emailconfigService;
        this.emailconfigRepo = emailconfigRepo;
        this.batch_processesRepo = batch_processesRepo;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addResignationAmount(Long employee_id, LocalDateTime monitor_from_date, LocalDateTime exit_date){
      /*
* This process requires the following parameters
*       - Exit_date
*       - Date Monitored
*       - Basic Salary
*           From the two variables i can get the number of days, Where from days i can get the amount for each day from basic salary
* */


/*       ***************************************************************
  Terms Definitions
   1. basic_salary = this refer to raw basic salary from employee predefined salary before any additions or deductions
   2. all_basic_salary = this refers to new basic salary generated by adding allowances that are subjected to deductions.
 */
//        fetch the Nhif Deductions

        Optional<Employee> _employee = employeeRepository.findEmployeeById(employee_id);
        if (_employee.isPresent()){
            Employee employee = _employee.get();


//        check if salary has been generated for that month
        LocalDateTime currentData = LocalDateTime.now();
        String month = currentData.getMonth().toString();
        String year = String.valueOf(currentData.getYear());

//                long employee_id = employee.getId();
                Double percentageDeduction = 1.0;
                Double taxable_allowances = 0.00;
                Double non_taxable_allowances = 0.00;

                //        **********************************Get Allowances********************************************
//        get payable allowances
                Optional<Allowance_custom> allowance_custom = allowance_customRepo.findPayableCustomAllowanceByEmployeeId(employee_id);

                Double allowance_custom_amount = 0.00;
                if (allowance_custom.isPresent()){
                    Allowance_custom allowanceCustomData = allowance_custom.get();
                    allowance_custom_amount = allowanceCustomData.getAmount();
                    Boolean is_taxable = allowanceCustomData.getIs_taxable();
                    if (is_taxable){
//                add to basic salary
                        taxable_allowances = allowanceCustomData.getAmount();
//                Close the allowances
                        allowanceCustomData.setIs_payable(false);
                        allowance_customRepo.save(allowanceCustomData);
                        System.out.println("************Is taxable allowances closed*************");

                    }else {
//                add to netpay
                        non_taxable_allowances = allowanceCustomData.getAmount();
//                Close the allowances
                        allowanceCustomData.setIs_payable(false);
                        allowance_customRepo.save(allowanceCustomData);
                        System.out.println("************Non taxable allowances closed*************");
                    }
                }


//            *****************Check if employee is on a Leave*********************
                Optional<EmployeeLeave> employeeLeaves = employeeLeaveRepo.findEmployeeOPenLeaves(employee_id);
                if (employeeLeaves.isPresent()){
//                get deduction percentage
                    EmployeeLeave leaveDetails = employeeLeaves.get();
                    Double deduction_rate = leaveDetails.getDeduction_percentage();
                    percentageDeduction = deduction_rate/100;
                }

//                TODO: Calculate the amount for that days

            double current_basic_salary = employee.getGross_salary();
            double daily_pay = current_basic_salary / 30;
//                            get the exit date on the date of exit of the employee
            LocalDate currentDate = LocalDate.now();
//                get a number of days by making a difference in dates
            long diff_Days = ChronoUnit.DAYS.between(monitor_from_date, exit_date);
//                            get total amount
            double get_E_basic_salary = daily_pay * diff_Days;

                double basic_salary = get_E_basic_salary *  percentageDeduction;
                double leaveDeductionamt = get_E_basic_salary - basic_salary;

//            double basic_salary = employee.getBasic_salary();
                double total_non_cash_benefit = employee.getTotal_non_cash_benefit();
                double value_of_quarters = employee.getValue_of_quarters();
                double gross_pay = basic_salary + total_non_cash_benefit + value_of_quarters + taxable_allowances;

                System.out.println(get_E_basic_salary );
                System.out.println(basic_salary);
                System.out.println(total_non_cash_benefit);
                System.out.println(value_of_quarters);
                System.out.println(taxable_allowances);
                System.out.println(gross_pay);
                Double dcrs_e1 = (0.3 * basic_salary);
                Double dcrs_actual_e2 = employee.getDcrs_actual_e2();
                Double dcrs_fixed_e3 = 20000.00;

                Double smallest_dcrs = Math.min(dcrs_e1, Math.min(dcrs_actual_e2, dcrs_fixed_e3));

//            get Owner Occupied Interest
                Double owner_occupied_interests = employee.getOwner_occupied_interests();

//            Calculate the retirement & owner occupied interest
                Double retirement_and_owner_occupied_interests = smallest_dcrs + owner_occupied_interests;

                Double chargeable_pay = gross_pay - retirement_and_owner_occupied_interests;
                Double personalRelief = employee.getPersonal_relief();
                Double insuranceRelief = employee.getInsurance_relief();
                Double total_relief = personalRelief + insuranceRelief;
                //           Algorithim for PAYE
//            *******************************************************************
//            double personalRelief = 2400.00;
                double taxRemainder = 0.00;
                double firstTax = 0.00;
                double secondTax = 0.00;
                double thirdTax = 0.00;
                double totalTax = 0.00;
                double  taxDeductions = 0;
                double tax_charged = 0;
                double paye = 0;
                //            get personal relief
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
//                LocalDate currentDate= LocalDate.now();
//               // from date
                // Get day from date
                int day = currentDate.getDayOfMonth();

                // Get month from date
//        Month month = currentDate.getMonth();
                String getMonth = month.toString();
                // Get year from date
//        int year = currentDate.getYear();

//        check if employee had advance salary?
                Double advance_salary = 0.00;
                Optional<AdvanceSalary> employeeAdvance = advanceSalaryRepo.findPaidOpenedAdvanceSalaryByEmployeeId(employee_id);
                if (employeeAdvance.isPresent()){
                    System.out.println("Salary Found");
                    AdvanceSalary currentAdvance = employeeAdvance.get();
                    advance_salary = currentAdvance.getSalary_amount();
                    System.out.println(advance_salary);
                    currentAdvance.setIs_advance_salary_closed(true);
                    advanceSalaryRepo.save(currentAdvance);
//            AdvanceSalary newState = new
//            newState.setIs_advance_salary_closed(true);
                }
//        **************************check if employee is enrolled for sacco savings ****************************
                Double sacco_deductions = 0.00;
                Boolean is_enrolled =   employee.getIs_sacco_enrolled();
                System.out.println(is_enrolled);
                if (is_enrolled){
                    System.out.println("Employee Enrolled to sacco!");
//            deduct the amount based on his percentage and add to sacco wallet
                    Double sacco_deduction_percentage = employee.getSacco_deduction_percentage();
                    sacco_deductions = netPay * sacco_deduction_percentage/100;


                    //            save deductions to sacco wallet
                    Sacco _sacco= new Sacco();
                    Month monthDate = currentDate.getMonth();
                    String nowMonth = monthDate.toString();
                    // Get year from date
                    int yearDate = currentDate.getYear();
                    _sacco.setEmployee_id(employee_id);
                    _sacco.setDepartment_id(employee.getDepartmentId());
                    _sacco.setContribution_amount(sacco_deductions);
                    _sacco.setEnrolled_on(employee.getEnrolled_on());
                    _sacco.setMonth(nowMonth );
                    _sacco.setYear(yearDate);
                    saccoRepo.save(_sacco);

                }
                Double total_netpay = netPay + non_taxable_allowances - advance_salary - sacco_deductions;

//                Salary salary = new Salary ();
                Resignationamt salary  = new Resignationamt();
//            All entries
//            salary.setBasic_pay(gross_pay);
                salary.setBasic_salary(basic_salary);
//            Pension notyet defined

                salary.setPension(0.00);
                salary.setMonthly_basic_salary(current_basic_salary);
                salary.setResignation_notice(diff_Days);
                salary.setEmployee_id(employee.getId());
                salary.setDepartment_id(employee.getDepartmentId());
                salary.setTotal_non_cash_benefit(total_non_cash_benefit);
                salary.setValue_of_quarters(value_of_quarters);
                salary.setGross_pay(gross_pay);
                salary.setDcrs_e1(dcrs_e1);
                salary.setDcrs_actual_e2(employee.getDcrs_actual_e2());
                salary.setDcrs_fixed_e3(dcrs_fixed_e3);
                salary.setOwner_occupied_interests(owner_occupied_interests);
                salary.setRetirement_and_owner_occupied_interests(retirement_and_owner_occupied_interests);
                salary.setChargeable_pay(chargeable_pay);
                salary.setTotal_relief(total_relief);
                salary.setTax_charged(tax_charged);
                salary.setPaye_deductions(paye);
                salary.setNet_pay(total_netpay);
                salary.setAllowance_custom(allowance_custom_amount);
                salary.setNhif_deductions(nhifDeductions);
                salary.setNssf_deductions(nssfDeductions);
//            salary.setPaye_deductions(taxDeductions);
                salary.setLeave_deduction(leaveDeductionamt);
                salary.setHelb_deductions(0.00);
                salary.setMonth(getMonth);
                salary.setYear(String.valueOf(year));
//updated to gross_pay
                salary.setTaxable_income(gross_pay);
                salary.setRelief_insurance(0.0);
                salary.setAllowance_field(0.00);
                salary.setAllowance_per_deam(0.00);
                salary.setAllowance_overtime(0.00);
                salary.setSacco_contributions(sacco_deductions);
                salary.setAdvanced_deductions(advance_salary);

                resignationamtService.addResignationamt(salary);
//        Close dummy salary
                System.out.println("*******************************************Resignation Salary generated successfully **************************************");
        }
        return null;
//        Resignationamt newResignation = resignationamtService.addResignationamt(resignationamt);
//        return  new ResponseEntity<>(newResignation, HttpStatus.CREATED);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Resignationamt>> getAllResignationamts () {
        List<Resignationamt> resignationamts = resignationamtService.findAllResignationamt();
        return  new ResponseEntity<>(resignationamts, HttpStatus.OK);
    }

    @GetMapping("/all/detailed")
    public ResponseEntity<List<ResignationamtRepo.ResignationSalaryDetail>> getDetailedResignationamts () {
        List<ResignationamtRepo.ResignationSalaryDetail> resignationamts = resignationamtRepo.findResignationSalaryDetail();
        return  new ResponseEntity<>(resignationamts, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Resignationamt> getResignationamtById (@PathVariable("id") Long id){
        Resignationamt resignationamt = resignationamtService.findResignationamtById(id);
        return new ResponseEntity<>(resignationamt, HttpStatus.OK);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<?> payEmployee(@PathVariable("id") Long id) throws JRException, SQLException, MessagingException, FileNotFoundException {
        Optional<Resignationamt> _resignationamount = resignationamtRepo.findById(id);
        if (_resignationamount.isPresent()){
            if (_resignationamount.get().getIs_approved()){
                Resignationamt resignationamt = _resignationamount.get();
//            get employee id
                Long employee_id = resignationamt.getEmployee_id();
                Optional<Employee> employee = employeeRepository.findById(employee_id);
                if (employee.isPresent()){
                    String employee_enail = employee.get().getPersonalEmail();
                    String firstName = employee.get().getFirstName();
//                call to send email
                    System.out.println("Sending email");
                    resignationController.generateEmployeeSalaryReport(id,firstName, employee_enail);
                    resignationamt.setPaid(true);
                    resignationamt.setPaid_on(LocalDateTime.now());
                    resignationamt.setUpdated_at(LocalDateTime.now());
                    resignationamtRepo.save(resignationamt);
                    return new ResponseEntity<>(resignationamtRepo.save(resignationamt), HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }else {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("It has not been approved!"));
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void generateEmployeeSalaryReport(Long id,String firstName, String employee_email) throws FileNotFoundException, JRException, SQLException, MessagingException {
//    Get Email Confirurations
        String email_type = "Employee_Report";
        Optional<Emailconfig> emailconfig = emailconfigRepo.findByEmailType(email_type);
        if (emailconfig.isPresent()) {
            //        // Get month from date
            Month month = currentDate.getMonth();
            String getMonth = month.toString();
            Integer year = currentDate.getYear();
                Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
                JasperReport compilePayslipReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/Resignation_payroll.jrxml"));
                Map<String, Object> parameter = new HashMap<String, Object>();
                parameter.put("salaryid", id);
                JasperPrint payslipReport = JasperFillManager.fillReport(compilePayslipReport, parameter, connection);
                ByteArrayOutputStream baosPayslip = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(payslipReport, baosPayslip);
                DataSource payslip = new ByteArrayDataSource(baosPayslip.toByteArray(), "application/pdf");
//     TODO: End of payslip generation, begining p9 form;
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(employee_email);
                helper.setCc(from_mail);
                helper.setFrom(cc_mail);
                helper.setSubject(emailconfig.get().getEmail_subject());
                helper.setText(
                        "<!DOCTYPE html>\n" +
                                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                                "<head>\n" +
                                "  <meta charset=\"utf-8\">\n" +
                                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                                "  <title></title>\n" +
                                "  <!--[if mso]>\n" +
                                "  <style>\n" +
                                "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                                "    div, td {padding:0;}\n" +
                                "    div {margin:0 !important;}\n" +
                                "  </style>\n" +
                                "  <noscript>\n" +
                                "    <xml>\n" +
                                "      <o:OfficeDocumentSettings>\n" +
                                "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                                "      </o:OfficeDocumentSettings>\n" +
                                "    </xml>\n" +
                                "  </noscript>\n" +
                                "  <![endif]-->\n" +
                                "  <style>\n" +
                                "    table, td, div, h1, p {\n" +
                                "      font-family: Arial, sans-serif;\\n\"\n" +
                                "    }\n" +
                                "    @media screen and (max-width: 530px) {\n" +
                                "      .unsub {\n" +
                                "        display: block;\n" +
                                "        padding: 8px;\n" +
                                "        margin-top: 14px;\n" +
                                "        border-radius: 6px;\n" +
                                "        background-color: #555555;\n" +
                                "        text-decoration: none !important;\n" +
                                "        font-weight: bold;\n" +
                                "      }\n" +
                                "      .col-lge {\n" +
                                "        max-width: 100% !important;\n" +
                                "      }\n" +
                                "    }\n" +
                                "    @media screen and (min-width: 531px) {\n" +
                                "      .col-sml {\n" +
                                "        max-width: 27% !important;\n" +
                                "      }\n" +
                                "      .col-lge {\n" +
                                "        max-width: 73% !important;\n" +
                                "      }\n" +
                                "    }\n" +
                                "  </style>\n" +
                                "</head>\n" +
                                "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
//                            "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                                "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
                                "    <table role=\"presentation\" style=\"width:100%; padding-top: 10px; padding-bottom: 10px; border:none;border-spacing:0;\">\n" +
                                "      <tr>\n" +
                                "        <td align=\"center\" style=\"padding:0;\">\n" +
                                "          <!--[if mso]>\n" +
                                "          <table role=\"presentation\" align=\"center\" style=\"width:600px; margin-top: 10px; margin-bottom: 10px;\">\n" +
                                "          <tr>\n" +
                                "          <td>\n" +
                                "          <![endif]-->\n" +
                                "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                                "              <td style=\"padding:5px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:companyLogo' alt=\"Logo\" style=\"width:20%; text-align:center; margin:auto; height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                                "                <hr>\n" +
                                "              </td>\n" +
                                "            <tr>\n" +
                                "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                                "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">"+  emailconfig.get().getEmail_heading() +"</h1>\n" +
                                "                <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_salutation() +" " + firstName + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_message() +"\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_remarks() +"\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_regards_from() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_organization_name() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n"+  "<b>Tel/Phone: </b> "+ emailconfig.get().getEmail_organization_phone() +"\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Email: </b> " + emailconfig.get().getEmail_organization_mail() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Address: </b> " + emailconfig.get().getEmail_organization_address() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Location: </b> " + emailconfig.get().getEmail_organization_location() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Website: </b> " + emailconfig.get().getEmail_organization_website() + "\n" +
                                "                    </p>\n" +
                                "              </td>\n" +
                                "            </tr>\n" +
                                "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                "                       <img src='cid:rightSideImage' style='width:100%;'/>" +
                                "              </td>\n" +
                                "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                "              </td>\n" +
                                "            <tr>\n" +
                                "            </tr>\n" +
                                "           \n" +
                                "            <tr>\n" +
                                "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
                                "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
                                "              </td>\n" +
                                "            </tr>\n" +
                                "          </table>\n" +
                                "          <!--[if mso]>\n" +
                                "          </td>\n" +
                                "          </tr>\n" +
                                "          </table>\n" +
                                "          <![endif]-->\n" +
                                "        </td>\n" +
                                "      </tr>\n" +
                                "    </table>\n" +
                                "  </div>\n" +
                                "</body>\n" +
                                "</html>", true);
                helper.addInline("companyLogo",
                        new File(company_logo_path));
                helper.addInline("rightSideImage",
                        new File(banner_path));
                helper.addAttachment("Payslip.pdf", payslip);
                mailSender.send(mimeMessage);
                System.out.println("Sent successfully,sent to " + employee_email);

        }else
        {
            System.out.println("*************************Error!*****************************");
            System.out.println("Sorry! Emailing failed! No email Configuration set for Employee Salary Report!");
            System.out.println("*************************Error!*****************************");
        }
    }
}



//package co.ke.emtechhouse.hrm_payroll_system.ReportsComponent;
//
//
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.Salary;
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.SalaryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.supercsv.io.CsvBeanWriter;
//import org.supercsv.io.ICsvBeanWriter;
//import org.supercsv.prefs.CsvPreference;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/v1/reports/csv")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
//public class CSVGenerator {
//    @Autowired
//    private SalaryService salaryService;
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @GetMapping("/export/salary")
//    public void exportToCSV(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
//        response.setHeader(headerKey, headerValue);
//
//        List<Salary> salaryList = salaryService.findAllSalary();
//
//        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
//        String[] csvHeader = {"Salary ID", "Basic Salary", "Paye Deductions", "NHIF Deductions", "NSSF Deductions", "Netpay"};
//        String[] nameMapping = {"id", "basic_salary", "paye_deductions", "nhif_deductions", "nssf_deductions", "net_pay"};
//
//        csvWriter.writeHeader(csvHeader);
//
//        for (Salary salary : salaryList) {
//            csvWriter.write(salary, nameMapping);
//        }
//        csvWriter.close();
//
//    }
//
//
//
//
//}

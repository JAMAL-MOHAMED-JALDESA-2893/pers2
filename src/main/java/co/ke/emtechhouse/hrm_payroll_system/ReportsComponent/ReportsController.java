package co.ke.emtechhouse.hrm_payroll_system.ReportsComponent;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.Payroll;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class ReportsController {

    @Value("${reports_absolute_path}")
    private String files_path;
    @Value("${logolink}")
    private String logolink;
    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final JavaMailSender mailSender;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final PayrollRepo payrollRepo;

     public ReportsController(JavaMailSender mailSender, EmployeeService employeeService, EmployeeRepository employeeRepository, PayrollRepo payrollRepo) {
         this.mailSender = mailSender;
         this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
         this.payrollRepo = payrollRepo;
     }
    @GetMapping("/banktransfer")
    public void getDocument(HttpServletResponse response) throws IOException, JRException, SQLException {

//        String sourceFileName = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "SampleJasperReport.jasper")
//                .getAbsolutePath();

        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/banktransfer.jrxml"));
//        HashMap<String, Object> parameter = new HashMap<>();
        Map<String, Object> parameter = new HashMap<String, Object>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameter, connection);
        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
        reportConfigXLS.setSheetNames(new String[] { "sheet1" });
        exporter.setConfiguration(reportConfigXLS);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");
        response.setContentType("application/octet-stream");
        exporter.exportReport();
//        sent to email
    }


    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(employeeService.findAllEmployee());
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/invoice.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

//        save file
//        JasperExportManager.exportReportToPdfFile(report, "invoice.pdf");
//        download file
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=invoice.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }
//
//    @GetMapping("/department/")
//    public ResponseEntity<?> generateDepartmentReport(@RequestParam String year, @RequestParam String department_id, @RequestParam String Department_Name) throws FileNotFoundException, JRException, SQLException {
//        //         Check if the data from that year exists
//        List<Salary> results = salaryRepository.validateByYearDepartment(year, department_id);
//        if(results.size()>0) {
//            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/Annual_Department_Reports.jrxml"));
//            Map<String, Object> parameter = new HashMap<String, Object>();
//            parameter.put("year", year);
//            parameter.put("department_id", department_id);
//            parameter.put("Department_Name", Department_Name);
//            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//            byte[] data = JasperExportManager.exportReportToPdf(report);
//            HttpHeaders headers = new HttpHeaders();
//            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Annualy_Department_Report.pdf");
//            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//    }
//        return ResponseEntity
//                .badRequest()
//                .body(new MessageResponse("Error: No Data for this Year!"));
//    }
//    @GetMapping("/department/monthly")
//    public ResponseEntity<?> generateDepartmentMonthlyReport(@RequestParam String year, @RequestParam String department_id, @RequestParam String Department_Name, @RequestParam String month) throws FileNotFoundException, JRException, SQLException {
//        //         Check if the data from that year exists
//        List<Salary> results = salaryRepository.validateDepartmentReports(year,department_id,month);
//        if(results.size()>0) {
//            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/Monthly_Department_Reports.jrxml"));
//            Map<String, Object> parameter = new HashMap<String, Object>();
//            parameter.put("year", year);
//            parameter.put("department_id", department_id);
//            parameter.put("Department_Name", Department_Name);
//            parameter.put("month", month);
//            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//            byte[] data = JasperExportManager.exportReportToPdf(report);
//            HttpHeaders headers = new HttpHeaders();
//            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Annualy_Department_Report.pdf");
//            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//
//        }
//        return ResponseEntity
//                .badRequest()
//                .body(new MessageResponse("Error: No Data for this period!"));
//    }

    @GetMapping("/payslip/{id}")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/payslip.jrxml"));
//        HashMap<String, Object> parameter = new HashMap<>();
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("salaryid", id);
        parameter.put("logolink", logolink);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);

//        save file
//        JasperExportManager.exportReportToPdfFile(report, "invoice.pdf");
//        download file
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=payslip.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    @GetMapping("/p9form/{id}")
    public ResponseEntity<byte[]> generateP9form(@PathVariable Integer id) throws FileNotFoundException, JRException, SQLException {
        // Get month from date
        LocalDate currentDate= LocalDate.now();
        Month month = currentDate.getMonth();
        String getMonth = month.toString();
//        // Get year from date
        Integer year = currentDate.getYear();
        Integer prev_year = year - 1;
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/p9aform.jrxml"));
//        HashMap<String, Object> parameter = new HashMap<>();
        System.out.println(id);
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("employee_id", id);
        parameter.put("prev_year", prev_year);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        save file
//        JasperExportManager.exportReportToPdfFile(report, "invoice.pdf");
//        download file
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=P9form.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    @GetMapping("/employee/p9form/")
    public ResponseEntity<?> generateEmployeeP9form(@RequestParam String period_y, @RequestParam String id_no, @RequestParam String Employers_name, @RequestParam String Employers_pin) throws FileNotFoundException, JRException, SQLException {
//       Check if present
        List<Payroll> payroll = payrollRepo.findByEmpIdNoYear(id_no,period_y);
        if (payroll.size()>1){
            // Get month from date
            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/Employee_P9.jrxml"));
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("period_y", period_y);
            parameter.put("id_no", id_no);
            parameter.put("Employers_name", Employers_name);
            parameter.put("Employers_pin", Employers_pin);
            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        save file
//        JasperExportManager.exportReportToPdfFile(report, "invoice.pdf");
//        download file
            byte[] data = JasperExportManager.exportReportToPdf(report);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=P9form.pdf");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No Data yet for the Year: "+period_y));
        }
    }
}

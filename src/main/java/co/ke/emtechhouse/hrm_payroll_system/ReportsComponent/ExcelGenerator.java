//package co.ke.emtechhouse.hrm_payroll_system.ReportsComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.Salary;
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.SalaryService;
//import net.sf.jasperreports.engine.JasperExportManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.mail.util.ByteArrayDataSource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/v1/reports")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
//public class ExcelGenerator {
//    @Autowired
//    private SalaryService salaryService;
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @GetMapping("/users/export/excel")
//    public void exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        List<Salary> listSalaries = salaryService.findAllSalary();
//        UserExcelExporter excelExporter = new UserExcelExporter(listSalaries);
//        excelExporter.export(response);
//    }
////    get excel report by user id
//    @GetMapping("/export/{id}")
//    public void exportExcel(@PathVariable Long id,  HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        String header = "Content-Disposition";
////        get data to list
//       Salary salary = salaryService.findSalaryById(id);
//
////       set file name
//        String filename = "dataExcel";
//        String headerValue = "attachment; filename=" + filename + ".xlsx";
//        response.setHeader(header, headerValue);
//
////        insert data to file
//        ExcelExporter excelExporter = new ExcelExporter(salary);
//        excelExporter.exportData(response, salary);
//    }
//
////    sent email
//    @GetMapping("/sentmail/{id}")
//    public ResponseEntity<InputStreamResource> exportExcelSentMail(@PathVariable Long id, HttpServletResponse response) throws IOException, MessagingException {
//        response.setContentType("application/octet-stream");
//
//        String header = "Content-Disposition";
//        String filename = "CollinsExcel";
////        set filename
//        String headerValue = "attachment; filename=" + filename + ".xlsx";
//        HttpHeaders headers = new HttpHeaders();
////        get data to list
//        Salary salary = salaryService.findSalaryById(id);
//        headers.add("Content-Disposition", "attachment; filename" + filename + ".xlsx");
//        response.setHeader(header, headerValue);
//
////        insert data to file
//        ExcelExporter excelExporter = new ExcelExporter(salary);
//
//
////        get byte from data
//        ByteArrayInputStream bis = excelExporter.exportData(response, salary);
//
////        call send mail
//          sendMail(salary, bis);
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_ATOM_XML).body(new InputStreamResource(bis));
//
//    }
//    private void sendMail(Salary salary, ByteArrayInputStream bis) throws MessagingException, IOException {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setTo("receivermail@gmail.com");
//        helper.setFrom("organizationmail@gmail.com");
//        helper.setSubject("E&M Tech | Excel Report.");
//        helper.setText("Text");
//        ByteArrayDataSource attachment = new ByteArrayDataSource(bis, "application/xlsx");
//        String filename = "Excel_Report";
//        helper.addAttachment(filename +".xlsx", attachment);
//        mailSender.send(mimeMessage);
//        System.out.println("Sent successfully");
//    }
//
//}

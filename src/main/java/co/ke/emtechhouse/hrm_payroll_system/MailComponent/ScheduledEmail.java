//package co.ke.emtechhouse.hrm_payroll_system.MailComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
//import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
//import com.sun.istack.ByteArrayDataSource;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jasperreports.engine.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import javax.activation.DataSource;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component @Slf4j
//public class ScheduledEmail<SalaryRepositoy> {
//    @Value("${spring.datasource.url}")
//    private String db;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${from_mail}")
//    private String from_mail;
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private PayrollComponentService payrollComponentService;
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    LocalDate currentDate= LocalDate.now();
//
////    @Scheduled(fixedRate = 200L)
////@Scheduled(cron="0 59 2 * * *")
//public void generatePayslip() throws FileNotFoundException, JRException, SQLException, MessagingException {
//    try {
////        // Get month from date
//        Month month = currentDate.getMonth();
//        String getMonth = month.toString();
////        // Get year from date
//        Integer year = currentDate.getYear();
//        List<PayrollInterface.PayrollData> payrollReport = payrollComponentService.findAllPayroll(getMonth,year.toString() );
//        for (int i =0; i<payrollReport.size(); i++) {
//            String employeeEmail = payrollReport.get(i).getEmployeeEmail();
//            String firstName = payrollReport.get(i).getFirstName();
//            String middleName = payrollReport.get(i).getMiddleName();
//            String lastName = payrollReport.get(i).getLastName();
//            Date payrollDate = payrollReport.get(i).getPayslipDate();
//            Long id = payrollReport.get(i).getSalaryId();
//
//            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/payslip.jrxml"));
////        HashMap<String, Object> parameter = new HashMap<>();
//            Map<String, Object> parameter = new HashMap<String, Object>();
//            parameter.put("Parameter1", id);
//            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            JasperExportManager.exportReportToPdfStream(report, baos);
//            DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//            helper.setTo(employeeEmail);
//
//            helper.setFrom(from_mail);
//            helper.setSubject("!SANDBOX EMAILS!!!E&M Tech Payslip report.");
//
//            helper.setText(
//                    "<!DOCTYPE html>\n" +
//                            "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
//                            "<head>\n" +
//                            "  <meta charset=\"utf-8\">\n" +
//                            "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
//                            "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
//                            "  <title></title>\n" +
//                            "  <!--[if mso]>\n" +
//                            "  <style>\n" +
//                            "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
//                            "    div, td {padding:0;}\n" +
//                            "    div {margin:0 !important;}\n" +
//                            "  </style>\n" +
//                            "  <noscript>\n" +
//                            "    <xml>\n" +
//                            "      <o:OfficeDocumentSettings>\n" +
//                            "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
//                            "      </o:OfficeDocumentSettings>\n" +
//                            "    </xml>\n" +
//                            "  </noscript>\n" +
//                            "  <![endif]-->\n" +
//                            "  <style>\n" +
//                            "    table, td, div, h1, p {\n" +
//                            "      font-family: Arial, sans-serif;\\n\"\n" +
//                            "    }\n" +
//                            "    @media screen and (max-width: 530px) {\n" +
//                            "      .unsub {\n" +
//                            "        display: block;\n" +
//                            "        padding: 8px;\n" +
//                            "        margin-top: 14px;\n" +
//                            "        border-radius: 6px;\n" +
//                            "        background-color: #566fff;\n" +
//                            "        text-decoration: none !important;\n" +
//                            "        font-weight: bold;\n" +
//                            "      }\n" +
//                            "      .col-lge {\n" +
//                            "        max-width: 100% !important;\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "    @media screen and (min-width: 531px) {\n" +
//                            "      .col-sml {\n" +
//                            "        max-width: 27% !important;\n" +
//                            "      }\n" +
//                            "      .col-lge {\n" +
//                            "        max-width: 73% !important;\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "  </style>\n" +
//                            "</head>\n" +
//                            "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
//                            "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
//                            "    <table role=\"presentation\"  style=\"width:100%; padding:40px 30px 0px 0px; border:none;border-spacing:0;\">\n" +
//                            "      <tr>\n" +
//                            "        <td align=\"center\" style=\"padding:0;\">\n" +
//                            "          <!--[if mso]>\n" +
//                            "          <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
//                            "          <tr>\n" +
//                            "          <td>\n" +
//                            "          <![endif]-->\n" +
//                            "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
//
//                            "            <tr>\n" +
//                            "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
//
//                            "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:rightSideImage' alt=\"Logo\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
//
//
//
//                            "                <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">E&M Tech Payslip Notification!</h1>\n" +
//                            "                <p style=\"margin:0;\">\n" +
//                            "                                        Hello "  + firstName + "\n" +
//                            "                                        Please find enclosed Payslip report for last month for" + payrollDate + "\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        Kind regards\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        Human Resource Manager\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        E&M Tech technologies\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        phone: +254 722 582 328\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        P.O BOX 11001-00100, Nairobi\n" +
//                            "                    </p>\n" +
//                            "              </td>\n" +
//                            "            </tr>\n" +
//                            "           \n" +
//                            "            <tr>\n" +
//                            "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
//                            "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
//                            "              </td>\n" +
//                            "            </tr>\n" +
//                            "          </table>\n" +
//                            "          <!--[if mso]>\n" +
//                            "          </td>\n" +
//                            "          </tr>\n" +
//                            "          </table>\n" +
//                            "          <![endif]-->\n" +
//                            "        </td>\n" +
//                            "      </tr>\n" +
//                            "    </table>\n" +
//                            "  </div>\n" +
//
//                            "<img src='cid:rightSideImage' style='width:100%;'/>"  +
//                            "</body>\n" +
//                            "</html>", true);
//            helper.addInline("rightSideImage",
//                    new File("/home/devop/Documents/1-Repository/HRM_Complex/Server_side/src/main/resources/e&m_banner.jpg"));
//            helper.addAttachment("Payslip.pdf", aAttachment);
//            mailSender.send(mimeMessage);
//            System.out.println("Sent successfully,sent to" + employeeEmail);
//        }
//    } catch (Exception e) {
//        log.info("Generate Payslip Error {} "+e);
//    }
//
//    }
//
///*
//
//This schedule is set to send the P9Form to every employee dated on January Every year.To their Respective emails.
//The schedule date will be on date 10th to allow a time for salary checkup process.
// */
////        @Scheduled(fixedRate = 100L)
//
////@Scheduled(cron="0 0 3 * * *")
//public void generateP9Form() throws FileNotFoundException, JRException, SQLException, MessagingException {
//    try {
////        // Get month from date
//        Month month = currentDate.getMonth();
//        String getMonth = month.toString();
////        // Get year from date
//        Integer year = currentDate.getYear();
//        Integer prev_year = year - 1;
//        List<PayrollInterface.PayrollData> payrollReport = payrollComponentService.findAllPayrollByYear(prev_year.toString() );
//        List<Employee> employees = employeeService.findAllEmployee();
//
//        for (int i =0; i<employees.size(); i++) {
//            String employeeEmail = employees.get(i).getPersonalEmail();
//            String firstName = employees.get(i).getFirstName();
//            String middleName = employees.get(i).getMiddleName();
//            String lastName = employees.get(i).getLastName();
//            Long ID = employees.get(i).getId();
//            int id = ID.intValue();
//
//            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/p9aform.jrxml"));
//            Map<String, Object> parameter = new HashMap<String, Object>();
//            parameter.put("employee_id", id);
//            parameter.put("prev_year", prev_year);
//            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            JasperExportManager.exportReportToPdfStream(report, baos);
//            DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setTo(employeeEmail);
//            helper.setFrom(from_mail);
//            helper.setSubject("E&M Tech P9A report for end year.");
//            helper.setText(
//                    "<!DOCTYPE html>\n" +
//                            "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
//                            "<head>\n" +
//                            "  <meta charset=\"utf-8\">\n" +
//                            "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
//                            "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
//                            "  <title></title>\n" +
//                            "  <!--[if mso]>\n" +
//                            "  <style>\n" +
//                            "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
//                            "    div, td {padding:0;}\n" +
//                            "    div {margin:0 !important;}\n" +
//                            "  </style>\n" +
//                            "  <noscript>\n" +
//                            "    <xml>\n" +
//                            "      <o:OfficeDocumentSettings>\n" +
//                            "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
//                            "      </o:OfficeDocumentSettings>\n" +
//                            "    </xml>\n" +
//                            "  </noscript>\n" +
//                            "  <![endif]-->\n" +
//                            "  <style>\n" +
//                            "    table, td, div, h1, p {\n" +
//                            "      font-family: Arial, sans-serif;\\n\"\n" +
//                            "    }\n" +
//                            "    @media screen and (max-width: 530px) {\n" +
//                            "      .unsub {\n" +
//                            "        display: block;\n" +
//                            "        padding: 8px;\n" +
//                            "        margin-top: 14px;\n" +
//                            "        border-radius: 6px;\n" +
//                            "        background-color: #566fff;\n" +
//                            "        text-decoration: none !important;\n" +
//                            "        font-weight: bold;\n" +
//                            "      }\n" +
//                            "      .col-lge {\n" +
//                            "        max-width: 100% !important;\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "    @media screen and (min-width: 531px) {\n" +
//                            "      .col-sml {\n" +
//                            "        max-width: 27% !important;\n" +
//                            "      }\n" +
//                            "      .col-lge {\n" +
//                            "        max-width: 73% !important;\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "  </style>\n" +
//                            "</head>\n" +
//                            "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
//                            "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
//                            "    <table role=\"presentation\"  style=\"width:100%; padding:40px 30px 0px 0px; border:none;border-spacing:0;\">\n" +
//                            "      <tr>\n" +
//                            "        <td align=\"center\" style=\"padding:0;\">\n" +
//                            "          <!--[if mso]>\n" +
//                            "          <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
//                            "          <tr>\n" +
//                            "          <td>\n" +
//                            "          <![endif]-->\n" +
//                            "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
//
//                            "            <tr>\n" +
//                            "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
//
//                            "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:rightSideImage' alt=\"Logo\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
//
//                            "                <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">E&M Tech Payslip Notification!</h1>\n" +
//                            "                <p style=\"margin:0;\">\n" +
//                            "                                        Hello "  + firstName + "\n" +
//                            "                                        Please find enclosed Payslip report for last month for  \n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        Kind regards\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        Human Resource Manager\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        E&M Tech technologies\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        phone: +254 722 582 328\n" +
//                            "                    </p>\n" +
//                            "                   <p style=\"margin:0;\">\n" +
//                            "                                        P.O BOX 11001-00100, Nairobi\n" +
//                            "                    </p>\n" +
//                            "              </td>\n" +
//                            "            </tr>\n" +
//                            "           \n" +
//                            "            <tr>\n" +
//                            "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
//                            "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
//                            "              </td>\n" +
//                            "            </tr>\n" +
//                            "          </table>\n" +
//                            "          <!--[if mso]>\n" +
//                            "          </td>\n" +
//                            "          </tr>\n" +
//                            "          </table>\n" +
//                            "          <![endif]-->\n" +
//                            "        </td>\n" +
//                            "      </tr>\n" +
//                            "    </table>\n" +
//                            "  </div>\n" +
//                            "<img src='cid:rightSideImage' style='width:100%;'/>"  +
//                            "</body>\n" +
//                            "</html>", true);
//            helper.addInline("rightSideImage",
//                    new File("/home/devop/Documents/1-Repository/HRM_Complex/Server_side/src/main/resources/e&m_banner.jpg"));
//            helper.addAttachment("E&M_Tech_Employee_P9A_report.pdf", aAttachment);
//            mailSender.send(mimeMessage);
//            System.out.println("Sent successfully,sent to" + employeeEmail);
//        }
//    } catch (Exception e) {
//        log.info("Generate Payslip Error {} "+e);
//    }
//
//    }
//
//}
//

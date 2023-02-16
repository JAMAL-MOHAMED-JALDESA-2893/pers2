package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.EmployeeNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private  final EmployeeRepository employeeRepository;
    private final EmployeeEducationRepo employeeEducationRepo;
    private final EmployeeExperienceRipo employeeExperienceRipo;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeEducationRepo employeeEducationRepo, EmployeeExperienceRipo employeeExperienceRipo) {
        this.employeeRepository = employeeRepository;
        this.employeeEducationRepo = employeeEducationRepo;
        this.employeeExperienceRipo = employeeExperienceRipo;
    }

    public long countEmployee(){
        return employeeRepository.countEmployee();
    }

    public long countClosedEmployee(){
        return employeeRepository.countClosedEmployee();
    }


    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public List<Employee> findAllEmployee(){
        return employeeRepository.findAll();
    }
    public List<Employee> findunAprovedEmployees(){
        return employeeRepository.findunAprovedEmployees();
    }
    public List<Employee> findActiveEmployee(){
        return employeeRepository.findActiveEmployees();
    }
    public List<Employee> findActiveSalariedEmployees(){
        return employeeRepository.findActiveSalariedEmployees();
    }

    public List<Employee> findInActiveEmployee(){
        return employeeRepository.findInActiveEmployees();
    }
    public List<Employee> findTrashedEmployees(){
        return employeeRepository.findTrashedEmployees();
    }


    public Employee findEmployeeById(Long id){
        return employeeRepository.findEmployeeById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee " + id +"was not found"));
    }
    public Employee findById(Long id){
        return employeeRepository.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee " + id +"was not found"));
    }
    public EmployeeEducation findEmployeeEducationById(Long id){
        return employeeEducationRepo.findEmployeeEducationById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee " + id +"was not found"));
    }
    public EmployeeWorkExperience findEmployeeExperienceById(Long id){
        return employeeExperienceRipo.findEmployeeExperienceById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee " + id +"was not found"));
    }

    public Employee updateEmployee(Employee employeeEntity){
        return employeeRepository.save(employeeEntity);
    }
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
//    Generate a a report
//    public String generateReport() throws FileNotFoundException, JRException{
//        String path = "/home/devop/Downloads";
//        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(employeeRepository.findAll());
//        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/invoice.jrxml"));
//        HashMap<String, Object> map = new HashMap<>();
//        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//
////        save file
//        JasperExportManager.exportReportToPdfFile(report, path+ "invoice.pdf");
//        return "Downloaded successfully!";
////        download file
////        byte[] data = JasperExportManager.exportReportToPdf(report);
////        HttpHeaders headers = new HttpHeaders();
////        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=invoice.pdf");
////        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//
//    }
    public String exportReports(String reportFormat) throws FileNotFoundException, JRException {
        String path="/home/devop/Desktop/Reports";
        List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);
//        Load the fxml
        File file = ResourceUtils.getFile("classpath:invoice.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/invoice.jrxml"));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Coullence");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path+ "/employees.pdf");

//        if(reportFormat.equalsIgnoreCase("html")){
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+ "\\employees.html");
//        }
//        if(reportFormat.equalsIgnoreCase("pdf")){
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path+ "employees.pdf");
//        }
        return  "Generated successfully! in " + path;



    }
}

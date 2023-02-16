package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent.Document;
import co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent.DocumentController;
import co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent.DocumentRepo;
import co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent.DocumentService;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent.Promotion;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent.PromotionService;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.TotalInterafaces;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.Sacco;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig.Saccoconfig;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig.SaccoconfigRepo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoRepo;
import co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent.Department;
import co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent.DepartmentRepo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/employees")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class EmployeeController {
    private final  EmployeeService employeeService;
    private final  PromotionService promotionService;
    private final  EmployeeRepository employeeRepository;
    private final  EmployeeEducationRepo employeeEducationRepo;
    private final  EmployeeExperienceRipo employeeExperienceRipo;
    private final  DepartmentRepo departmentRepo;
    private final  SaccoconfigRepo saccoconfigRepo;
    private final  SaccoRepo saccoRepo;
    private final  DocumentRepo documentRepo;
    private final  DocumentService documentService;
    private final  DocumentController documentController;
    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${file.upload-dir}")
    public String DIRECTORY;
    public EmployeeController(EmployeeService employeeService, PromotionService promotionService, EmployeeRepository employeeRepository, EmployeeEducationRepo employeeEducationRepo, EmployeeExperienceRipo employeeExperienceRipo, DepartmentRepo departmentRepo, SaccoconfigRepo saccoconfigRepo, SaccoRepo saccoRepo, DocumentRepo documentRepo, DocumentService documentService, DocumentController documentController) {
        this.employeeService = employeeService;
        this.promotionService = promotionService;
        this.employeeRepository = employeeRepository;
        this.employeeEducationRepo = employeeEducationRepo;
        this.employeeExperienceRipo = employeeExperienceRipo;
        this.departmentRepo = departmentRepo;
        this.saccoconfigRepo = saccoconfigRepo;
        this.saccoRepo = saccoRepo;
        this.documentRepo = documentRepo;
        this.documentService = documentService;
        this.documentController = documentController;
    }
    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
        try {
            return employeeService.exportReports(format);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/payslip/{id}")
    public String generatePayslip(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        try {
//        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(employeeService.findEmployeeById(id));
            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/ePayslip.jrxml"));
//        HashMap<String, Object> parameter = new HashMap<>();
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("employeeId", id);
            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        save file
            JasperExportManager.exportReportToPdfFile(report, "payslip.pdf");
            return null;

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
//    @GetMapping("/count")
//    public  long countClosedEmployees(){
//        return employeeService.countEmployee();
//    }
//    @GetMapping("/count/closed/salary")
//    public  long countClosedSalary(){
//        return employeeService.countClosedEmployee();
//    }

    @PostMapping("/add")
    public  ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        try {
            employee.setIs_approved(true);
            employee.setEmployee_status("Active");
            employee.setIs_deleted(false);
            Employee newEmployee = employeeService.addEmployee(employee);
            // initialise sacco for employee
            Sacco saccoInit = new Sacco();
            saccoInit.setEmployee_id(newEmployee.getId());
            saccoInit.setDepartment_id(newEmployee.getDepartmentId());
            saccoInit.setContribution_amount(0.00);
            saccoInit.setEnrolled_on(LocalDateTime.now());
            saccoInit.setMonth(LocalDateTime.now().getMonth().toString());
            saccoInit.setYear(LocalDateTime.now().getYear());
            saccoRepo.save(saccoInit);
            System.out.println("*********************save sacco********************");
            return  new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> uploadFiles( @RequestParam("files") List<MultipartFile> multipartFiles) throws IOException {
        try {
            String group_by = "Employee Documents";
            String user_id = "121312";
                List<String> documents = new ArrayList<>();
                for (MultipartFile file : multipartFiles) {
                    String filename = StringUtils.cleanPath(file.getOriginalFilename());
                    String filenameref = StringUtils.cleanPath(file.getOriginalFilename()) + LocalDateTime.now();
//            TODO: Figure out a way of using a customised file name when adding a new file
//            store in database files name
//            check file name if exist? then reject tell user to update
                    Optional<Document> documentData = documentRepo.findByFilenameref(filenameref);
                    if (documentData.isPresent()) {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: The file with the name already exist!Kindly change the file naming or update"));
                    } else {
                        Document newDocument = new Document();
                        newDocument.setFilenameref(filenameref);
                        newDocument.setFilename(filename);
                        newDocument.setGroup_by(group_by);
                        newDocument.setUser_id(user_id);
                        Document document = documentService.addDocument(newDocument);
//                TODO: Check if the file has been saved in the databases first
                        Optional<Document> checkData = documentRepo.findByFilenameref(filenameref);
                        if (checkData.isPresent()) {
                            Path fileStorage = get(DIRECTORY, filenameref).toAbsolutePath().normalize();
                            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
                            documents.add(filenameref);
                            return new ResponseEntity<>(document, HttpStatus.CREATED);
                        } else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: File not saved"));
                        }
                    }
                }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        try {
            List<Employee> employees = employeeService.findAllEmployee();
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/filter/by/employee/status")
    public ResponseEntity<List<EmployeeRepository.EmployeeDepartmentDetailed>> filterByEmployeeStatus(@RequestParam String employee_status) {
        try {
            List<EmployeeRepository.EmployeeDepartmentDetailed> employees = employeeRepository.filterByEmployeeStatus(employee_status);
            System.out.println(employees);
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/evaluation/unenrolled/employees")
    public ResponseEntity<List<Employee>> findUnenrolledActiveEmployees() {
        try {
            List<Employee> employees =  employeeRepository.findUnenrolledActiveEmployees();
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/unApproved")
    public ResponseEntity<List<Employee>> findunAprovedEmployees() {
        try {
            List<Employee> employees = employeeService.findunAprovedEmployees();
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/active")
    public ResponseEntity<List<Employee>> getActiveEmployees () {
        try {
            List<Employee> employees = employeeService.findActiveSalariedEmployees();
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/inactive")
    public ResponseEntity<List<Employee>> getInActiveEmployees () {
        try {
            List<Employee> employees = employeeService.findInActiveEmployee();
            return  new ResponseEntity<>(employees, HttpStatus.OK);

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/trashed")
    public ResponseEntity<List<Employee>> getTrashedEmployees () {
        try {
            List<Employee> employees = employeeService.findTrashedEmployees();
            return  new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/employee/Department")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment (@RequestParam Long dp_Id){
        try {
            List<Employee> employee = employeeRepository.findByDepartment(dp_Id);
            return  new ResponseEntity<>(employee, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id){
        try {
            Employee employee = employeeService.findEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/employee/detail/{id}")
    public ResponseEntity<List<TotalInterafaces.SalaryDetail>> getEmployeeSalaryDataById (@PathVariable("id") Long id){
        try {
            List<TotalInterafaces.SalaryDetail> employee = employeeRepository.findEmployeeSalaryDetail(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
//    findEmployeeSalaryDetail
    @GetMapping("/find/education/{id}")
    public ResponseEntity<EmployeeEducation> getEmployeeEducationById (@PathVariable("id") Long id){
        try {
            EmployeeEducation education = employeeService.findEmployeeEducationById(id);
            return new ResponseEntity<>(education, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/education/{id}")
    public ResponseEntity<EmployeeEducation> updateEmployeeEducation(@PathVariable("id") long id, @RequestBody EmployeeEducation employeeEducationEntity){
        try {
            Optional<EmployeeEducation> employeeData = employeeEducationRepo.findEmployeeEducationById(id);
            if (employeeData.isPresent()) {
                EmployeeEducation _employeeEducationEntity = employeeData.get();
                _employeeEducationEntity.setInstitutionLevel(employeeEducationEntity.getInstitutionLevel());
                _employeeEducationEntity.setInstitutionName(employeeEducationEntity.getInstitutionName());
                _employeeEducationEntity.setAwardCertificate(employeeEducationEntity.getAwardCertificate());
                _employeeEducationEntity.setGpaScore(employeeEducationEntity.getGpaScore());
                _employeeEducationEntity.setEnrollOn(employeeEducationEntity.getEnrollOn());
                _employeeEducationEntity.setGraduatedOn(employeeEducationEntity.getGraduatedOn());
                return new ResponseEntity<>(employeeEducationRepo.save(_employeeEducationEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/experience/{id}")
    public ResponseEntity<EmployeeWorkExperience> getEmployeeExperienceById (@PathVariable("id") Long id){
        try {
            EmployeeWorkExperience experience = employeeService.findEmployeeExperienceById(id);
            return new ResponseEntity<>(experience, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/experience/{id}")
    public ResponseEntity<EmployeeWorkExperience> updateEmployeeExperience(@PathVariable("id") long id, @RequestBody EmployeeWorkExperience employeeWorkExperience){
        try {
            Optional<EmployeeWorkExperience> employeeData = employeeExperienceRipo.findEmployeeExperienceById(id);
            if (employeeData.isPresent()) {
                EmployeeWorkExperience _employeeExperience = employeeData.get();
                _employeeExperience.setCompanyName(employeeWorkExperience.getCompanyName());
                _employeeExperience.setWorkPosition(employeeWorkExperience.getWorkPosition());
                _employeeExperience.setTimeTaken(employeeWorkExperience.getTimeTaken());
                return new ResponseEntity<>(employeeExperienceRipo.save(_employeeExperience), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        try {
            employee.setIs_approved(true);
            employee.setEmployee_status("Active");
            employee.setIs_deleted(false);
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/enroll/to/sacco/{id}")
    public ResponseEntity<?> enrollEmployeeToSacco(@PathVariable("id") long id, @RequestBody Employee employeeEntity){
       Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
        if (employeeData.isPresent()) {
            Optional<Saccoconfig> saccoconfig = saccoconfigRepo.findSaccoConfiguration();
            Double min_sacco_deduction = saccoconfig.get().getMin_deduction_Percentage();
            Double max_sacco_deduction = saccoconfig.get().getMax_deduction_Percentage();
            if (saccoconfig.isPresent()){
                Double sacco_deduction_amount = employeeEntity.getSacco_deduction_percentage();
                if (sacco_deduction_amount<min_sacco_deduction || sacco_deduction_amount>max_sacco_deduction){
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Kindly check your percentages!, Maximum allowed percentage:"+ max_sacco_deduction + "Minimum percentage allowed:" + min_sacco_deduction+"."));
                }else {
                    Employee _employeeEntity = employeeData.get();
                    _employeeEntity.setFirstName(employeeEntity.getFirstName());
                    _employeeEntity.setIs_sacco_enrolled(true);
                    _employeeEntity.setSacco_deduction_percentage(employeeEntity.getSacco_deduction_percentage());
                    _employeeEntity.setEnrolled_on(LocalDateTime.now());
                    _employeeEntity.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/promote/employee/{id}")
    public ResponseEntity<Employee> promoteEmployee(@PathVariable("id") long id, @RequestBody Promotion promotion){
        try {
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                promotion.setPrev_basic_pay(_employeeEntity.getGross_salary());
                _employeeEntity.setGross_salary(promotion.getNew_basic_pay());
                _employeeEntity.setUpdated_at(LocalDateTime.now());
//Call to  save the update promotion update logs
                promotion.setEmployee_id(_employeeEntity.getId());
                promotion.setDepartment_id(_employeeEntity.getDepartmentId());
                promotion.setPrev_position(_employeeEntity.getPosition());
                promotion.setIs_executive(promotion.getIs_executive());
                promotion.setRegistared_on(_employeeEntity.getCreated_at());
                if (promotion.getIs_head_of_department()){
                    promotion.setIs_executive(true);
//                _employeeEntity.setHead_of_department_id(_employeeEntity.getDepartmentId());
                    promotion.setIs_head_of_department(promotion.getIs_head_of_department());
//                TODO: Update department with an head employee
//                find department by id
                    Optional<Department> department = departmentRepo.findDepartmentById(_employeeEntity.getDepartmentId());
                    if (department.isPresent()){
                        Department _department = department.get();
                        _department.setHead_of_department_eid(_employeeEntity.getId());
                        departmentRepo.save(_department);
                    }
                }
                promotionService.addPromotion(promotion);
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
//    Approve employee
    @PutMapping("/approve/employee/{id}")
    public ResponseEntity<Employee> approveEmployee(@PathVariable("id") long id){
        try {
            Boolean approved = true;
            Boolean is_approved = true;
            String Activate = "Active";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
//            _employeeEntity.setIs_employee_approved(approved);
                _employeeEntity.setIs_approved(is_approved);
                _employeeEntity.setEmployee_status(Activate);
                _employeeEntity.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    //    Approve Employee Promotion
//    THis will set the updates for the salary increment
//    THis will update the approval status
    @PutMapping("/approve/promoted/employee/{id}")
    public ResponseEntity<Employee> approvePromotedEmployee(@PathVariable("id") long id){
        try {
            Boolean delete = false;
            String employee_status = "Active";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_deleted(false);
                _employeeEntity.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/diactivate/employee/{id}")
    public ResponseEntity<Employee> diactivateEmployee(@PathVariable("id") long id){
        try {
            Boolean delete = false;
            Boolean updateClosedSalary = false;
            String employee_status = "Inactive";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_salary_closed(updateClosedSalary);
//            _employeeEntity.se;
                _employeeEntity.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/trash/delete/{id}")
    public ResponseEntity<Employee> trashDeleteEmployee(@PathVariable("id") long id){
        try {
            Boolean updateClosedSalary = false;
            String employee_status = "Inactive";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_salary_closed(updateClosedSalary);
                _employeeEntity.setIs_trashed(true);
                _employeeEntity.setDeleted_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/soft/delete/{id}")
    public ResponseEntity<Employee> softDeleteEmployee(@PathVariable("id") long id){
        try {
            Boolean updateClosedSalary = false;
            String employee_status = "Inactive";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_salary_closed(updateClosedSalary);
                _employeeEntity.setIs_deleted(true);
                _employeeEntity.setDeleted_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/restore/employee/{id}")
    public ResponseEntity<Employee> restoreEmployee(@PathVariable("id") long id){
        try {
//        Boolean delete = false;
//        Boolean updateClosedSalary = true;
            String employee_status = "Active";
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_salary_closed(true);
                _employeeEntity.setIs_deleted(false);
                _employeeEntity.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/clear/employee/{id}")
    public ResponseEntity<Employee> employeeCleared(@PathVariable("id") long id){
        try {
//        Generate a whole time report and send to employer and employee
            Boolean delete = true;
            Boolean updateClosedSalary = false;
            String employee_status = "Inactive";
            Boolean permanentlyCleared = true;
            Optional<Employee> employeeData = employeeRepository.findEmployeeById(id);
            if (employeeData.isPresent()) {
                Employee _employeeEntity = employeeData.get();
                _employeeEntity.setEmployee_status(employee_status);
                _employeeEntity.setIs_salary_closed(false);
                _employeeEntity.setIs_deleted(true);
                _employeeEntity.setPermanently_cleared(true);
                _employeeEntity.setCleared_at(LocalDateTime.now());
                return new ResponseEntity<>(employeeRepository.save(_employeeEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

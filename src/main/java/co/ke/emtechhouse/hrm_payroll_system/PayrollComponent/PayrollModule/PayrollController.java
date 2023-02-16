package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.EmployeeInfo.Employeeinfo;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.EmployeeInfo.EmployeeinfoRepo;
import com.sun.mail.iap.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v2/payroll/")
public class PayrollController {
    private final PayrollService payrollService;
    private final PayrollRepo payrollRepo;
    private final EmployeeinfoRepo employeeinfoRepo;
    @Autowired
    private PayrollGenerator payrollGenerator;
    @Autowired
    private EmployeeRepository employeeRepository;

    public PayrollController(PayrollService payrollService, PayrollRepo payrollRepo, EmployeeinfoRepo employeeinfoRepo) {
        this.payrollService = payrollService;
        this.payrollRepo = payrollRepo;
        this.employeeinfoRepo = employeeinfoRepo;
    }
    @PostMapping("/generate/Manual")
    public ResponseEntity<?> generateManualPayroll(@RequestBody Payroll payroll) {
        Long emp_no = payroll.getEmployee_id();
        String period_m = payroll.getPeriod_m();
        String period_y = payroll.getPeriod_y();
//            check if salary has been generated
        Optional<Payroll> payroll1 = payrollRepo.findByEmpYearMonth(emp_no, period_m, period_y);
        if (payroll1.isPresent()){ return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Already Generated for this month!" +period_m));
        }else{
            Payroll newPayroll = payrollService.addPayroll(payroll);
            return new ResponseEntity<>(newPayroll, HttpStatus.CREATED);
        }
    }
    //@Scheduled(cron = "0/1 * * * * ?")
    @PostMapping("/generate/Automatic")
    public ResponseEntity<?> generateAutomaticPayroll() {
//        Foreach Employee Get last entry
//        List<Payroll> activeEmployees = payrollRepo.findActiveEmployeeDetails();
        List<Employee> activeEmployees = employeeRepository.findActiveEmployees();
        for (int i = 0; i<activeEmployees.size();i++){
            Long emp_no = activeEmployees.get(i).getEmp_no();
            String first_name = activeEmployees.get(i).getFirstName();
            String other_names = activeEmployees.get(i).getMiddleName()+" "+activeEmployees.get(i).getLastName();
            String id_no = activeEmployees.get(i).getNationalId();
            String acct_no = activeEmployees.get(i).getBankAccount();
            String bank = activeEmployees.get(i).getBankName();
            String nssf_no = activeEmployees.get(i).getNssfNo();
            String nhif_no = activeEmployees.get(i).getNhifNo();
            String pin_no = activeEmployees.get(i).getKraNo();
            String period_m = LocalDateTime.now().getMonth().toString();
            String period_y = String.valueOf(LocalDateTime.now().getYear());
            Double Salary = activeEmployees.get(i).getGross_salary();
            //basic salary
            Double basic_salary= activeEmployees.get(i).getBasic_salary();
            Double helb_deductions = 0.00;
            Double Total_non_cash_benefit = activeEmployees.get(i).getTotal_non_cash_benefit();
            Double Dcrs_actual_e2 = activeEmployees.get(i).getDcrs_actual_e2();
            Double Owner_occupied_interests = activeEmployees.get(i).getOwner_occupied_interests();
            Double Value_of_quarters = activeEmployees.get(i).getValue_of_quarters();
            Double Total_relief = 0.00;
            payrollGenerator.setSalary(emp_no,first_name,other_names,id_no,acct_no, bank, nssf_no,nhif_no,pin_no,period_m,
                    period_y,helb_deductions, Total_non_cash_benefit,Dcrs_actual_e2,Owner_occupied_interests,Total_relief, basic_salary);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("upload/excel/payroll")
    public ResponseEntity<Response> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String message = "";
        if( Excellimports.hasExcelFormat(file)){
            payrollService.excellimport(file);
            message="File upload successfully";
            log.info("new stock added to system");
            return  ResponseEntity.ok().body(new Response(message));
        }
        message = "Please upload an excel file!";
        return  ResponseEntity.ok().body(new Response(message));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addPayroll(@RequestBody Payroll payroll, @RequestBody Employeeinfo employeeinfo) {
        setEmployeeInfo(employeeinfo);
        Long emp_no = payroll.getEmployee_id();
        String period_m = payroll.getPeriod_m();
        String period_y = payroll.getPeriod_y();
//            check if salary has been generated
        Optional<Payroll> payroll1 = payrollRepo.findByEmpYearMonth(emp_no, period_m, period_y);
        if (payroll1.isPresent()){
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Already Generated for this month!" +period_m));
        }else{
            Payroll newPayroll = payrollService.addPayroll(payroll);
            return new ResponseEntity<>(newPayroll, HttpStatus.CREATED);
        }
    }
    public void setEmployeeInfo(@RequestBody Employeeinfo employeeinfo){
        Optional<Employeeinfo> employeeinfo1 = employeeinfoRepo.findById_no(employeeinfo.getId_no());
        if (employeeinfo1.isPresent()){
//            Update Details
            Employeeinfo _empployeeinfo = employeeinfo1.get();
            _empployeeinfo.setFirst_name(employeeinfo.getFirst_name());
            _empployeeinfo.setOther_names(employeeinfo.getOther_names());
            _empployeeinfo.setId_no(employeeinfo.getId_no());
            _empployeeinfo.setAcct_no(employeeinfo.getAcct_no());
            _empployeeinfo.setBank(employeeinfo.getBank());
            _empployeeinfo.setNssf_no(employeeinfo.getNssf_no());
            _empployeeinfo.setNhif_no(employeeinfo.getNhif_no());
            _empployeeinfo.setPin_no(employeeinfo.getPin_no());
            _empployeeinfo.setSalary(employeeinfo.getSalary());
            _empployeeinfo.setUpdated_at(LocalDateTime.now());
            employeeinfoRepo.save(_empployeeinfo);
        }
        else{
//            Add New Details
            employeeinfoRepo.save(employeeinfo);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Payroll>> getAllPayrolls() {
        List<Payroll> payrolls = payrollService.findAllPayrolls();
        return new ResponseEntity<>(payrolls, HttpStatus.OK);
    }
    @GetMapping("/basic/details/all")
    public ResponseEntity<List<Payroll>> getBasicEMployeeDetails() {
        List<Payroll> payrolls = payrollRepo.findBasicEmployeeDetails();
        return new ResponseEntity<>(payrolls, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getPayrollById(@PathVariable("id") Long id) {
        Payroll payroll = payrollService.findById(id);
        return new ResponseEntity<>(payroll, HttpStatus.OK);
    }
    @GetMapping("/approve/by/{id}")
    public ResponseEntity<?> approveByID(@PathVariable("id") Long id) {
        Payroll payroll = payrollService.findById(id);
        payroll.setIs_salary_committed(true);
        payroll.setUpdated_at(LocalDateTime.now());
        payrollRepo.save(payroll);
        return new ResponseEntity<>(payroll, HttpStatus.OK);
    }
    @GetMapping("/all/uncommitted")
    public ResponseEntity<List<Payroll>>  findCurrentUncommittedPayroll() {
        List<Payroll> payrolls = payrollRepo. findCurrentUncommittedPayroll();
        return new ResponseEntity<>(payrolls, HttpStatus.OK);
    }
    @GetMapping("/employee/list/{employee_id}")
    public List<?> getSalaryDetailsForEmployee (@PathVariable("employee_id") Long employee_id) {
        return payrollRepo.findByEmployee_id(employee_id);
    }
    @GetMapping("/employee/list/six/months/{employee_id}")
    public List<?> getSalaryDetailsForEmployeeSixMonths (@PathVariable("employee_id") Long employee_id) {
        return payrollRepo.findByEmployee_idLastSixMonths(employee_id);
    }
    @GetMapping("committed/per/year/month")
    public List<?> getCommittedYearlyMonthlySalaryDetail (@RequestParam String month, @RequestParam  String year) {
        return payrollRepo.findCurrentCommittedMonthSalaryDetail(month, year);
    }
    @GetMapping("find/per/year/month")
    public List<?> getCurrentMonthSalaryDetail (@RequestParam String month, @RequestParam  String year, @RequestParam  Boolean committedStatus) {
        return payrollRepo.findCurrentMonthSalaryDetails(month, year,committedStatus);
    }
    @GetMapping("/summary/per/month&year")
    public ResponseEntity<?> getPAyrollSummary (@RequestParam String period_m, @RequestParam  String period_y) {
        Optional<PayrollInterfaces.PayrollSummary> payrollSummary = payrollRepo.monthlySummary(period_m, period_y);
        if (payrollSummary.isPresent()){
            return new ResponseEntity<>(payrollSummary, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/summary/per/year")
    public ResponseEntity<?> getPayrollSummaryPerYear (@RequestParam  String period_y) {
        List<PayrollInterfaces.yearlyPayrollSummary> payrollSummary = payrollRepo.yearlyGrossSummary(period_y);
        if (payrollSummary.size()>0){
            return new ResponseEntity<>(payrollSummary, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/employee/summary/per/year")
    public ResponseEntity<?> employeeSummary(@RequestParam  String period_y) {
        List<PayrollInterfaces.employeeSummary> payrollSummary = payrollRepo.getEmployeeSummary(period_y);
        if (payrollSummary.size()>0){
            return new ResponseEntity<>(payrollSummary, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/years")
    public ResponseEntity<?> getYears() {
        List<PayrollInterfaces.payrollYears> payrollSummary = payrollRepo.getYears();
        if (payrollSummary.size()>0){
            return new ResponseEntity<>(payrollSummary, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updatePayroll(@RequestBody Payroll payroll) {
        //        Get Payroll id
       Payroll _payroll = payrollService.findById(payroll.getId());
       if (_payroll.getIs_salary_committed()){
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("The salary for this employee on this month: "+ _payroll.getPeriod_m() + " Has been committed as final!"));
       }else{
           Payroll newPayroll = payrollService.updatePayroll(payroll);
           return new ResponseEntity<>(newPayroll, HttpStatus.OK);
       }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayroll(@PathVariable("id") Long id) {
        //        Get Payroll id
        Payroll _payroll = payrollService.findById(id);
        if (_payroll.getIs_salary_committed()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("The salary for this employee on this month: "+ _payroll.getPeriod_m() + " Has been committed as final!"));
        }else{
            payrollService.deletePayroll(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //TODO: COMMITTING PAYROLL
    @PutMapping("/hr/commit/{id}")
    public ResponseEntity<?> commitPayroll(@PathVariable Long id) {
        try {
            Optional<Payroll> payroll = payrollRepo.findById(id);
            if(payroll.isPresent()){
                Payroll existingPayroll=payroll.get();
                existingPayroll.setIs_salary_committed(true);
                payrollRepo.save(existingPayroll);
                return new ResponseEntity<>(existingPayroll, HttpStatus.OK);
            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    //TODO: COMMITTING PAYROLL ALL (REVIEW)

    @PutMapping("/hr/commit/all")
    public ResponseEntity<?> commitAll() {
        try {
            List<Payroll> payroll = payrollRepo.findAll();
            if(payroll.size()>0){
                log.info(String.valueOf(payroll.size()));
                for(int i=0; i<payroll.size();i++){
                    Payroll existingPayroll=payroll.get(i);
                    if(existingPayroll.getIs_salary_committed().equals(false)){
                        existingPayroll.setIs_salary_committed(true);
                        payrollRepo.save(existingPayroll);
                        return new ResponseEntity<>(existingPayroll, HttpStatus.OK);
                    }
                }

            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

}
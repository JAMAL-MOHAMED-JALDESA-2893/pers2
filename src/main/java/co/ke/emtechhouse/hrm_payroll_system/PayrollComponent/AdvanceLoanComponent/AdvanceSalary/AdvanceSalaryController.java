package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalary;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalaryParams.AdvanceSalaryParams;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalaryParams.AdvanceSalaryParamsRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits.Benefit_non_cash_custom;
import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule.PayrollRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/salary/advance/")
@Slf4j
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AdvanceSalaryController {
    private final AdvanceSalaryRepo advanceSalaryRepo;
    private final AdvanceSalaryService advanceSalaryService;
    private final EmployeeRepository employeeRepository;
    private final AdvanceSalaryParamsRepo advanceSalaryParamsRepo;
    private final PayrollRepo payrollRepo;
    public AdvanceSalaryController(AdvanceSalaryRepo advanceSalaryRepo, AdvanceSalaryService advanceSalaryService, EmployeeRepository employeeRepository, AdvanceSalaryParamsRepo advanceSalaryParamsRepo, PayrollRepo payrollRepo) {
        this.advanceSalaryRepo = advanceSalaryRepo;
        this.advanceSalaryService = advanceSalaryService;
        this.employeeRepository = employeeRepository;
        this.advanceSalaryParamsRepo = advanceSalaryParamsRepo;
        this.payrollRepo = payrollRepo;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAdvanceSalary(@RequestBody AdvanceSalary advanceSalary){
//        TODO: Check if employee has got applied for advance for that particular month.
        Long employee_id = advanceSalary.getEmployee_id();
        String month = advanceSalary.getMonth();
        Integer year = advanceSalary.getYear();
        Optional<AdvanceSalary> employeeAdvancedSalary = advanceSalaryRepo.findEmployeeAdvancedSalaryMonthly(employee_id,month,year);
        if (employeeAdvancedSalary.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: You already applied for an advance salary this month. Kindly wait for the new month or you may as well take a loan to cover your need."));
        }else {
//        TODO: Check if employee has still got unpaid advance
            Optional<AdvanceSalary> openAdvancedSalary = advanceSalaryRepo.findOpenAdvancedSalary(employee_id);
            if (openAdvancedSalary.isPresent()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: You still have pending Advance, kindly pay the advance before applying"));
            } else {
//            TODO: GET employee net salary
                Optional<Employee> employee = employeeRepository.findEmployeeById(employee_id);
                if (employee.isPresent()) {
                    //TODO: calculating employee net salary after tax
                    Double gross_salary = employee.get().getGross_salary();
                    Double salaryAfterTaxDeduction= advanceSalaryService.employeeSalaryAfterPayeDeduction(employee_id);
//                TODO: CHECK if the advance request is under the required parameters.
//                Get the parameters
                    Optional<AdvanceSalaryParams> params = Optional.ofNullable(advanceSalaryParamsRepo.findAll().get(0));
                    if (params.isPresent()) {
                        Double max_percentage = params.get().getMax_salary_percentage();
                        Double min_percentage = params.get().getMin_salary_percentage();
//                    get the computation
                        Double max_advance = max_percentage * salaryAfterTaxDeduction;
                        Double min_advance = min_percentage * salaryAfterTaxDeduction;
//                    compare if the employee is qualified for the presented amount
                        Double amount = advanceSalary.getSalary_amount();
                        if (amount < min_advance) {
                            return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Kindly we do not give an advance of lower than " + min_percentage * 100 + "%"));
                        } else if (amount > max_advance) {
                            return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Kindly we do not give an advance of higher than " + max_percentage * 100 + "%"));
                        } else {
//                            get User identity
                            String user_identity = advanceSalary.getUser_identity();
                            if (user_identity.equalsIgnoreCase("Administrator")){
//                            get user department id Administrator
                                Long department_id = employee.get().getDepartmentId();
                                advanceSalary.setDepartment_id(department_id);
                                advanceSalary.setDirector_approval("Pending");
                                AdvanceSalary newAdvanceSalary = advanceSalaryService.addAdvanceSalary(advanceSalary);
                                return new ResponseEntity<>(newAdvanceSalary, HttpStatus.CREATED);

                            }else {
//                            get user department id
                                Long department_id = employee.get().getDepartmentId();
                                advanceSalary.setDepartment_id(department_id);
                                advanceSalary.setSupervisor_approval("Pending");
                                advanceSalary.setHr_approval("Pending");
                                AdvanceSalary newAdvanceSalary = advanceSalaryService.addAdvanceSalary(advanceSalary);
                                return new ResponseEntity<>(newAdvanceSalary, HttpStatus.CREATED);
                            }


                        }
                    } else {
                        System.out.println("There is no advance parameters");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                } else {
                    System.out.println("Employee not present");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvanceSalaryRepo.AdvanceSalaryDetail>> getAllAdvanceSalarys () {
        List<AdvanceSalaryRepo.AdvanceSalaryDetail> advanceSalarys = advanceSalaryService.findAllAdvanceSalarys();
        return  new ResponseEntity<>(advanceSalarys, HttpStatus.OK);
    }
    @GetMapping("/all/per/department/{department_id}")
    public ResponseEntity<List<AdvanceSalaryRepo.AdvanceSalaryDetail>> findAllDepartmentAdvancedSalaryDetail(@PathVariable("department_id")Long department_id) {
        List<AdvanceSalaryRepo.AdvanceSalaryDetail> advanceSalarys = advanceSalaryService.findAllDepartmentAdvancedSalaryDetail(department_id);
        return  new ResponseEntity<>(advanceSalarys, HttpStatus.OK);
    }

    @GetMapping("/all/open/advance/loans")
    public ResponseEntity<List<AdvanceSalaryRepo.AdvanceSalaryDetail>> getAllOpenAdvanceSalarys () {
        List<AdvanceSalaryRepo.AdvanceSalaryDetail> advanceSalarys = advanceSalaryRepo.findAllOpenAdvancedSalary();
        return  new ResponseEntity<>(advanceSalarys, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<AdvanceSalary> getAdvanceSalaryById (@PathVariable("id") Long id){
        AdvanceSalary advanceSalary = advanceSalaryService.findAdvanceSalaryById(id);
        return new ResponseEntity<>(advanceSalary, HttpStatus.OK);
    }
//    TODO: Find by employee id
    @GetMapping("/find/by/employee/{id}")
    public ResponseEntity<List<AdvanceSalary>> getAdvanceSalaryByEmployeeId (@PathVariable("id") Long id){
        Long  employee_id = id;
        List<AdvanceSalary> advanceSalary = advanceSalaryService.findAdvanceSalaryByEmployeeId(employee_id);
        return new ResponseEntity<>(advanceSalary, HttpStatus.OK);
    }
//    TODO: Update to processing
@PutMapping("/update/to/processing/by/{id}")
public ResponseEntity<?> processingAdvanceSalaryStatus(@PathVariable("id") long id, @RequestBody AdvanceSalary advanceSalary){
    //Advance can not be processed! instantly hence the system should gave a 24hr grazing period
    Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findAdvanceSalaryById(id);
    LocalDateTime onboard_on = advanceSalaryData.get().getUpdated_at();
    LocalDateTime current_date = LocalDateTime.now();
    long hours = ChronoUnit.HOURS.between(onboard_on, current_date);
    if (hours<24){
        return ResponseEntity.badRequest().body(new MessageResponse("Sorry!, This advanced loan has not completed the grazing period of 24hrs from the last update by employee... "));
    }else {
        if (advanceSalaryData.isPresent()) {
            AdvanceSalary _advanceSalary = advanceSalaryData.get();
            _advanceSalary.setIs_processing(true);
            _advanceSalary.setStatus("Processing");
            return new ResponseEntity<>(advanceSalaryRepo.save(_advanceSalary), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
//    TODO: Update to approved
//@PutMapping("/update/to/approved/by/{id}")
//public ResponseEntity<?> approvedAdvanceSalaryStatus(@PathVariable("id") long id, @RequestBody AdvanceSalary advanceSalary) throws MessagingException {
//    Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findAdvanceSalaryById(id);
//    LocalDateTime onboard_on = advanceSalaryData.get().getUpdated_at();
//    LocalDateTime current_date = LocalDateTime.now();
//    long hours = ChronoUnit.HOURS.between(onboard_on, current_date);
//    if (hours<24){
//        return ResponseEntity.badRequest().body(new MessageResponse("Sorry!, This advanced loan has not completed the grazing period of 24hrs from the last updat by employee... "));
//    }else {
//        if (advanceSalaryData.isPresent()) {
//            AdvanceSalary _advanceSalary = advanceSalaryData.get();
//            _advanceSalary.setIs_approved(true);
//            _advanceSalary.setStatus("Approved");
////        TODO: Update the salary of that month... deduct the advance and insert the advance amount
////        find the salary by 1. employee id, 2. month, 3. year
//            Long employee_id = _advanceSalary.getEmployee_id();
//            String month = _advanceSalary.getMonth();
//            Integer year = _advanceSalary.getYear();
//            Optional<Salary> currentSalary = salaryRepository.findUncommitedSalaryByEmployeeIdMonthYear(employee_id, month, year);
//            Optional<Payroll> currentSalary = payrollRepo.
//            if (currentSalary.isPresent()) {
//                Double currentNetPay = currentSalary.get().getNet_pay();
//                Double newNetPay = currentNetPay - _advanceSalary.getSalary_amount();
////            ok
//                Salary _newSalary = currentSalary.get();
//                _newSalary.setNet_pay(newNetPay);
//                _newSalary.setAdvanced_deductions(_advanceSalary.getSalary_amount());
//                _newSalary.setUpdatedAt(LocalDateTime.now());
//                System.out.println(newNetPay);
//                salaryRepository.save(_newSalary);
////                Get employee data
//                System.out.println(employee_id);
//                Optional<Employee> employee = employeeRepository.findEmployeeById(employee_id);
////                call notification class
//                return new ResponseEntity<>(advanceSalaryRepo.save(_advanceSalary), HttpStatus.OK);
//            } else {
//                return ResponseEntity.badRequest().body(new MessageResponse("Sorry!:No Salary Generated for this employee... "));
//            }
//
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}
//    TODO: Update to disbursed
@PutMapping("/update/to/disbursement/by/{id}")
public ResponseEntity<?> disbursedAdvanceSalaryStatus(@PathVariable("id") long id){
    Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findAdvanceSalaryById(id);
    LocalDateTime onboard_on = advanceSalaryData.get().getUpdated_at();
    LocalDateTime current_date = LocalDateTime.now();
    long hours = ChronoUnit.HOURS.between(onboard_on, current_date);
    if (hours<0){
        return ResponseEntity.badRequest().body(new MessageResponse("Sorry!, This advanced loan has not completed the grazing period of 24hrs from the last updat by employee... "));
    }else {
        if (advanceSalaryData.isPresent()) {
            AdvanceSalary _advanceSalary = advanceSalaryData.get();
            if (_advanceSalary.getIs_approved()) {
                _advanceSalary.setIs_disbursed(true);
                _advanceSalary.setStatus("Disbursed");
                _advanceSalary.setIs_advance_salary_closed(true);
                advanceSalaryService.addAdvanceSalaryDeduction(_advanceSalary);
                return new ResponseEntity<>(advanceSalaryRepo.save(_advanceSalary), HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Sorry!:No It has not been approved "));
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdvanceSalary(@PathVariable("id") long id, @RequestBody AdvanceSalary advanceSalary){
        Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findAdvanceSalaryById(id);
        if (advanceSalaryData.isPresent()) {
            AdvanceSalary _advanceSalary = advanceSalaryData.get();
//        TODO: Check if the application is on process
        if (_advanceSalary.getIs_processing()){
            return ResponseEntity.badRequest().body(new MessageResponse("Sorry!:You can not update this application as it is in processing. If you may have an issue, kindly consult the director. "));
        }else{
                        //        TODO: Check if employee has got applied for advance for that particular month.
            Long employee_id = advanceSalary.getEmployee_id();
            String month = advanceSalary.getMonth();
            Integer year = advanceSalary.getYear();
            Optional<AdvanceSalary> employeeAdvancedSalary = advanceSalaryRepo.findEmployeeAdvancedSalaryMonthly(employee_id,month,year);
//            TODO: GET employee Gross salary
                    Optional<Employee> employee = employeeRepository.findEmployeeById(employee_id);
                    if (employee.isPresent()) {
                        //Double gross_salary = employee.get().getGross_salary();
                        Double salaryAfterTaxDeduction= advanceSalaryService.employeeSalaryAfterPayeDeduction(employee_id);
//                TODO: CHECK if the advance request is under the required parameters.
//                Get the parameters
                        long params_id = 1;
                        Optional<AdvanceSalaryParams> params = advanceSalaryParamsRepo.findAdvanceSalaryParamsById(params_id);
                        if (params.isPresent()) {
                            Double max_percentage = params.get().getMax_salary_percentage();
                            Double min_percentage = params.get().getMin_salary_percentage();
//                    get the computation
                            Double max_advance = max_percentage * salaryAfterTaxDeduction;
                            Double min_advance = min_percentage * salaryAfterTaxDeduction;
//                    compare if the employee is qualified for the presented amount
                            Double amount = advanceSalary.getSalary_amount();
                            if (amount < min_advance) {
                                return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Kindly we do not give an advance of lower than " + min_percentage * 100 + "%"));
                            } else if (amount > max_advance) {
                                return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Kindly we do not give an advance of higher than " + max_percentage * 100 + "%"));
                            } else {

                                _advanceSalary.setSalary_amount(advanceSalary.getSalary_amount());
                                advanceSalaryRepo.save(_advanceSalary);
                                return new ResponseEntity<>(HttpStatus.OK);
                                
                            }
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }

        }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        try {
                    Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findById(id);
                    if (advanceSalaryData.isPresent()){
                        if (advanceSalaryData.get().getStatus().matches("Generated")){
                            advanceSalaryRepo.deleteById(id);
                        }else{
                            return ResponseEntity.badRequest().body(new MessageResponse("Sorry! Your application is in progress! "));
                        }
                    }else {
                        return null;
                    }

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return null;
        }
    }
//    TODO:Supervisor approval
     @PutMapping("/approve/by/supervisor/{id}")
    public ResponseEntity<AdvanceSalary> supervisorApprove(@PathVariable("id") long id){
        try {
            Optional<AdvanceSalary> advanceSalary = advanceSalaryRepo.findById(id);
            if(advanceSalary.isPresent()){
                AdvanceSalary newAdvanceSalary=advanceSalary.get();
                newAdvanceSalary.setSupervisor_approval("approved");
                newAdvanceSalary.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(advanceSalaryRepo.save(newAdvanceSalary), HttpStatus.OK);
            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //    TODO:HR approval
    @PutMapping("/approve/by/hr/{id}")
    public ResponseEntity<?> hrApprove(@PathVariable("id") long id){
        try {
            Optional<AdvanceSalary> advanceSalary = advanceSalaryRepo.findById(id);
            if(advanceSalary.isPresent()){
                //TODO: confirm supervisor approval
                AdvanceSalary newAdvanceSalary=advanceSalary.get();
                if(newAdvanceSalary.getSupervisor_approval().equalsIgnoreCase("approved")){
                    newAdvanceSalary.setHr_approval("approved");
                    newAdvanceSalary.setUpdated_at(LocalDateTime.now());
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("Failed! Not approved by a supervisor "));
                }

                return new ResponseEntity<>(advanceSalaryRepo.save(newAdvanceSalary), HttpStatus.OK);
            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //    TODO:HR approval
    @PutMapping("/approve/by/director/{id}")
    public ResponseEntity<?> directorApprove(@PathVariable("id") long id){
        try {
            Optional<AdvanceSalary> advanceSalary = advanceSalaryRepo.findById(id);
            if(advanceSalary.isPresent()){
                //TODO: confirm hr approval
                AdvanceSalary newAdvanceSalary=advanceSalary.get();
                if(newAdvanceSalary.getHr_approval().equalsIgnoreCase("approved")){
                    newAdvanceSalary.setDirector_approval("approved");
                    newAdvanceSalary.setIs_approved(true);
                    newAdvanceSalary.setUpdated_at(LocalDateTime.now());
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("Failed! Not approved by a HR "));
                }

                return new ResponseEntity<>(advanceSalaryRepo.save(newAdvanceSalary), HttpStatus.OK);
            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteAdvanceSalary(@PathVariable("id") long id, @RequestBody AdvanceSalary advanceSalary) {
//        Optional<AdvanceSalary> advanceSalaryData = advanceSalaryRepo.findAdvanceSalaryById(id);
////        TODO: Check if has been disbursed then nothing will be deleted
//        if (advanceSalaryData.get().getIs_disbursed()) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Advanced Salary has been disbursed, kindly check you account or with the hr...maximum of 24 hrs "));
//        } else if (advanceSalaryData.get().getIs_approved()) {
////        TODO: Check if approved but not discbursed, then can cancel
//            return ResponseEntity.badRequest().body(new MessageResponse("Sorry!: Advanced Salary has been approved kindly wait for disbursement "));
//        }else if (advanceSalaryData.get().getIs_processing()){
////        TODO: Check if approved but not discbursed, then can cancel
//            AdvanceSalary _newStatus = advanceSalaryData.get();
//            _newStatus.setStatus("Canceled");
//            _newStatus.setIs_advance_salary_closed(true);
//            return new ResponseEntity<>(advanceSalaryRepo.save(_newStatus), HttpStatus.OK);
//        }
//        else {
////        TODO: CHECK if not approved then delete permanently
//            advanceSalaryService.deleteAdvanceSalary(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//    }




}
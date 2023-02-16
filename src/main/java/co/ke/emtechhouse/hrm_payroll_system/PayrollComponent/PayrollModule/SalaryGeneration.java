package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.Activity;
import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.ActivityRepo;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.MailComponent.AutoEmails;
import co.ke.emtechhouse.hrm_payroll_system._Batch_Processes.ScheduledSalaryEvent;
import co.ke.emtechhouse.hrm_payroll_system._Batch_Processes.Batch_processes;
import co.ke.emtechhouse.hrm_payroll_system._Batch_Processes.Batch_processesRepo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/payroll/salary/generate")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class SalaryGeneration {
    private final Batch_processesRepo batch_processesRepo;
    private final ScheduledSalaryEvent scheduledSalaryEvent;
    private final AutoEmails autoEmails;
    private final ActivityRepo activityRepo;
    private final PayrollGenerator payrollGenerator;
    private final PayrollRepo payrollRepo;

    public SalaryGeneration(Batch_processesRepo batch_processesRepo, ScheduledSalaryEvent scheduledSalaryEvent, AutoEmails autoEmails, ActivityRepo activityRepo, PayrollGenerator payrollGenerator, PayrollRepo payrollRepo) {
        this.batch_processesRepo = batch_processesRepo;
        this.scheduledSalaryEvent = scheduledSalaryEvent;
        this.autoEmails = autoEmails;
        this.activityRepo = activityRepo;
        this.payrollGenerator = payrollGenerator;
        this.payrollRepo = payrollRepo;
    }

    /*
    *
    * Each event will check if the parent Event has been executed, otherwise reject
    * check if the event has been executed or enabled by batch process.
    * if not, the system will execute the system
    *
    */
    @PostMapping("/Generate_Payroll")
    public ResponseEntity<?> Open_Salary(){
        LocalDateTime currentData = LocalDateTime.now();
        String period_m = currentData.getMonth().toString();
        String period_y = String.valueOf(currentData.getYear());

        List<Payroll> payroll1 = payrollRepo.findByYearMonth(period_m,period_y);
        if (payroll1.size()>0){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Already Generated for this month!" + period_m));
        }else{
            Activity newActivity = new Activity();
            String activity_name = "Generate Payroll Executed";
            String activity_category = "Batch Process";
            newActivity.setActivity_name(activity_name);
            newActivity.setActivity_category(activity_category);
            activityRepo.save(newActivity);

            String event_type = "Generate Payroll";
            Optional<Batch_processes> _batch_process = batch_processesRepo.findByEventType(event_type);
            if (_batch_process.isPresent()) {
                System.out.println("Found Batch");

                Boolean is_executed = _batch_process.get().getIs_executed();
                if (is_executed) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("This Event has been executed! kindly review on your salary Generation history"));
                } else {
//                TODO: Execute the event
                    _batch_process.get().setIs_executed(true);
                    batch_processesRepo.save(_batch_process.get());
//                TODO: Open the next Process
                    String next_process = "Sent Payroll Report to Director";
                    Optional<Batch_processes> _next_process = batch_processesRepo.findByEventType(next_process);
                    if (_next_process.isPresent()) {
                        System.out.println(_next_process);

                        _next_process.get().setIs_executed(false);
                        batch_processesRepo.save(_next_process.get());
                    }
                    payrollGenerator.genPayroll();
                }
            } else {
                System.out.println("Not Found Batch");
//
////            TODO: No batch Process Set else execute
//                payrollGenerator.genPayroll();
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @PostMapping("/Sent_Payroll_Report_to_Director")
    public ResponseEntity<?> Sent_Modifiable_Payroll_Report_to_Director() throws JRException, SQLException, MessagingException, FileNotFoundException {
        String event_type = "Sent Payroll Report to Director";
        Optional<Batch_processes> _batch_process = batch_processesRepo.findByEventType(event_type);
        if (_batch_process.isPresent()){
            Boolean is_executed = _batch_process.get().getIs_executed();
            if (is_executed){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("This Event has been executed! kindly review on your salary Generation history"));
            }else {
//                TODO: Execute the event
                _batch_process.get().setIs_executed(true);
                batch_processesRepo.save(_batch_process.get());
//                TODO: Open the next Process
                String next_process = "Approve & Commit Payroll";
                Optional<Batch_processes> _next_process = batch_processesRepo.findByEventType(next_process);
                if (_next_process.isPresent()){
                    _next_process.get().setIs_executed(false);
                    batch_processesRepo.save(_next_process.get());
                }
                autoEmails.sendDummyMonthlySalaryReportToExecutives();
            }
        }else {
//            TODO: No batch Process Set else execute - pending
            autoEmails.sendDummyMonthlySalaryReportToExecutives();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/Approve_and_Commit Payroll")
    public ResponseEntity<?> Close_Payroll_and_Commit_Salary(){
        String event_type = "Approve & Commit Payroll";
        Optional<Batch_processes> _batch_process = batch_processesRepo.findByEventType(event_type);
        if (_batch_process.isPresent()){
            Boolean is_enabled = _batch_process.get().getIs_enabled();
            Boolean is_executed = _batch_process.get().getIs_executed();
            if (is_executed){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("This Event has been executed! kindly review on your salary Generation history"));
            }else {
//                TODO: Execute the event
                _batch_process.get().setIs_executed(true);
                batch_processesRepo.save(_batch_process.get());
//                TODO: Open the next Process
                String next_process = "Generate Payroll Report & Payslips";

                Optional<Batch_processes> _next_process = batch_processesRepo.findByEventType(next_process);
                if (_next_process.isPresent()){
                    _next_process.get().setIs_executed(false);
                    batch_processesRepo.save(_next_process.get());
                }
//                Get all this month payroll
                LocalDateTime currentData = LocalDateTime.now();
                String month = currentData.getMonth().toString();
                String year = String.valueOf(currentData.getYear());

                List<Payroll> uncommited = payrollRepo.findCurrentUncommittedMonthSalaryDetail(month,year);
                if (uncommited.size()>0){
                    for (int i =0; i<uncommited.size(); i++){
                        Payroll _payroll = uncommited.get(i);
                        _payroll.setIs_salary_committed(true);
                        _payroll.setUpdated_at(LocalDateTime.now());
                        payrollRepo.save(_payroll);
                    }
                }
            }
        }else {
//            TODO: No batch Process Set else execute
            scheduledSalaryEvent.closedSalary();
            scheduledSalaryEvent.commitSalary();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/Generate_Payroll_Report_and_Payslips")
    public ResponseEntity<?> Generate_Payslips_and_P9forms_to_employees_Emails() throws JRException, SQLException, MessagingException, FileNotFoundException {
        String event_type = "Generate Payroll Report & Payslips";
        Optional<Batch_processes> _batch_process = batch_processesRepo.findByEventType(event_type);
        if (_batch_process.isPresent()){
            Boolean is_executed = _batch_process.get().getIs_executed();
            if (is_executed){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("This Event has been executed! kindly review on your salary Generation history"));
            }else {
//                TODO: Execute the event
                _batch_process.get().setIs_executed(true);
                batch_processesRepo.save(_batch_process.get());
//                TODO: Open the next Process
                String next_process = "Generate Payroll";
                Optional<Batch_processes> _next_process = batch_processesRepo.findByEventType(next_process);
                if (_next_process.isPresent()){
                    _next_process.get().setIs_executed(false);
                    batch_processesRepo.save(_next_process.get());
                }
//                autoEmails.generateEmployeeSalaryReport();
            }
        }else {
//            TODO: No batch Process Set else execute
//            autoEmails.generateEmployeeSalaryReport();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

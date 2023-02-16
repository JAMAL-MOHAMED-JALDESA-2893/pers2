package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

import co.ke.emtechhouse.hrm_payroll_system.MailComponent.AutoEmails;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class Batch_Processes_Scheduler {

    @Autowired
    private Batch_processesRepo batch_processesRepo;
    @Autowired
    private Batch_processesService batch_processesService;
    @Autowired
    private AutoEmails autoEmails;
    @Autowired
    private ScheduledSalaryEvent scheduledSalaryEvent;
//
////
//        @Scheduled(fixedRate = 500L)
//    public void checking(){
//            log.info("Executed now");
//
//// Create a List using the set of zones and sort it.
//
//            LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Europe/Madrid").toZoneId());
//            System.out.println(localNow);
//
//
//
//            LocalDateTime  current_date = LocalDateTime.now();
//            System.out.println(current_date.atZone("Nairobi").toZ);
//            System.out.println(current_date);
//            System.out.println(current_date.toLocalDate());
//            LocalTime nowt = LocalTime.now();
//            System.out.println(nowt);
//
//        }

//    @Scheduled(fixedRate = 21000L)
@Scheduled(cron="${cron.exp_batch_process}")
public void Scheduler() throws JRException, SQLException, MessagingException, FileNotFoundException {
    try {//        call the batch databases
        log.info("**************************************** EXECUTED!: Daily Process Executed for today!************************************************");
        List<Batch_processes> getEvent = batch_processesRepo.findAll();
        if (getEvent.size()>0){
            for (int i =0; i<getEvent.size(); i++){
                Batch_processes currentEvent = getEvent.get(i);
//                Generate Dummy Salary
                if (currentEvent.getEvent_type().equalsIgnoreCase("Open_Salary") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("********************** Opening Salary**********************");
                    scheduledSalaryEvent.openSalary();
                }else if (currentEvent.getEvent_type().matches("Generate_Dummy_Payroll") && currentEvent.getExecution_date() == LocalDateTime.now().getDayOfMonth()){
                    log.info("********************** Generating Dummy Payroll **********************");
                    scheduledSalaryEvent.addNewSalary();
                }else if (currentEvent.getEvent_type().equalsIgnoreCase("Sent_Dummy_Payroll_Notification_Mail_Report") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("********************** Sent_Dummy_Payroll_Notification_Mail_Report**********************");
                    autoEmails.sendDummyMonthlySalaryReportToExecutives();
                }else if (currentEvent.getEvent_type().equalsIgnoreCase("Close_Payroll_Generation") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("**********************Close_Payroll_Generation**********************");
                    scheduledSalaryEvent.closedSalary();
                }else if (currentEvent.getEvent_type().equalsIgnoreCase("Close_and_commit_Salary") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("**********************Commit_Salary**********************");
                    scheduledSalaryEvent.commitSalary();
                }else if (currentEvent.getEvent_type().equalsIgnoreCase("Sent_Actual_Payroll_Notification_Mail_Report") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("**********************Sent_Actual_Payroll_Notification_Mail_Report**********************");
                    autoEmails.sendCommitedMonthlySalaryReportToExecutives();
                }else if (currentEvent.getEvent_type().equalsIgnoreCase("Sent_P9form_and_Payslip_Reports_Mail") && currentEvent.getExecution_date().equals(LocalDateTime.now().getDayOfMonth())){
                    log.info("**********************Sent_P9form_and_Payslip_Reports_Mail**********************");
//                    autoEmails.generateEmployeeSalaryReport();
                    log.info("**********************************CLOSED!  Salary Generation Process closed thank you ******************************************************");
                }else {
                    log.info("********************************* Process not found ********************************* ");
                }
            }
        }
    } catch (Exception e) {
        log.info("Catched Error {} "+e);
    }
    }
}

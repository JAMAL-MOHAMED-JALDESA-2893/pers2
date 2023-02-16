package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/batch/process/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Batch_processController {
    @Autowired
    private Batch_processesRepo batch_processesRepo;
    @Autowired
    private Batch_processesService batch_processesService;

    @PostMapping("/add")
    public ResponseEntity<?> addBatch_processes(@RequestBody Batch_processes batch_processes){
        try {
            LocalDateTime initial = LocalDateTime.now();
            LocalDateTime currentMonth = initial.withDayOfMonth(1);
            LocalDateTime nextExecutionDate = currentMonth.plusDays(batch_processes.getExecution_date()).minusDays(1);
            LocalDateTime lastExecutionDate = nextExecutionDate.minusMonths(1);
            batch_processes.setExecuted_on(lastExecutionDate);
            batch_processes.setNext_execution(nextExecutionDate);
//        check if the batch process already set
            Optional<Batch_processes> batchProcess = batch_processesRepo.findByEventType(batch_processes.getEvent_type());
            if (batchProcess.isPresent()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("You already have configuration for this!"));
            }else {
//            check date delimiters
                Integer getDate = batch_processes.getExecution_date();
                if (getDate<1 || getDate>31){
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Invalid date! check your calender date limits"));
                }else {
                    if (batch_processes.getEvent_type().matches("Open_Salary")){
                        Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                        return  new ResponseEntity<>(newBatch_processes, HttpStatus.CREATED);
                    }
//                Generate_Modifiable_Payroll
                    else if (batch_processes.getEvent_type().matches("Generate_Modifiable_Payroll")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Open_Salary");
                        if (parentProcessType.isPresent()){
//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){
                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {
//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Open Salary date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Open salary process which has not been set, kindly set first! "));
                        }
                    }
                    //                Sent_Modifiable_Payroll_Report_to_Director
                    else if (batch_processes.getEvent_type().matches("Sent_Modifiable_Payroll_Report_to_Director ")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Generate_Modifiable_Payroll");
                        if (parentProcessType.isPresent()){

//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            System.out.println(parentProcessType);
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){

                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {

//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Generate_Modifiable_Payroll date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Generate_Modifiable_Payroll process which has not been set, kindly set first! "));
                        }
                    }
                    //                Close_Payroll_and_Commit_Salary
                    else if (batch_processes.getEvent_type().matches("Close_Payroll_and_Commit_Salary")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Sent_Modifiable_Payroll_Report_to_Director ");
                        if (parentProcessType.isPresent()){
//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){
                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {
//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Sent_Modifiable_Payroll_Report_to_Director  date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Sent_Modifiable_Payroll_Report_to_Director  which has not been set, kindly set first! "));
                        }
                    }
                    //                Close_and_commit_Salary
                    else if (batch_processes.getEvent_type().matches("Close_and_commit_Salary")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Close_Payroll_and_Commit_Salary");
                        if (parentProcessType.isPresent()){
//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){
                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {
//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Close_Payroll_and_Commit_Salary date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Close_Payroll_and_Commit_Salary which has not been set, kindly set first! "));
                        }
                    }
                    //                Generate_Modifiable_Payroll
                    else if (batch_processes.getEvent_type().matches("Generate_Committed_Payroll_Report_to_Director")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Close_and_commit_Salary");
                        if (parentProcessType.isPresent()){
//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){
                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {
//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Open Salary date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Close_and_commit_Salary which has not been set, kindly set first! "));
                        }
                    }

                    //                Generate_Modifiable_Payroll
                    else if (batch_processes.getEvent_type().matches("Generate_Payslips_and_P9forms_to_employees_Emails")){
//                    check prev support
                        Optional<Batch_processes> parentProcessType = batch_processesRepo.findByEventType("Generate_Committed_Payroll_Report_to_Director");
                        if (parentProcessType.isPresent()){
//                        check month
//                        if month is less than
                            int parentMonth = parentProcessType.get().getNext_execution().getMonth().getValue();
                            if (parentMonth<batch_processes.getNext_execution().getMonth().getValue()){
                                Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                            }else {
//                            The parent Process is the same month with the new process....we check dates
                                if (parentProcessType.get().getNext_execution().getDayOfMonth() >  nextExecutionDate.getDayOfMonth()){
                                    return ResponseEntity
                                            .badRequest()
                                            .body(new MessageResponse("This Process Can not be set to before Generate_Committed_Payroll_Report_to_Director date, kindly amend"));
                                }else {
                                    Batch_processes newBatch_processes = batch_processesService.addBatch_processes(batch_processes);
                                }
                            }
//                        end of present
                        }else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Kindly this process depends on Generate_Committed_Payroll_Report_to_Director which has not been set, kindly set first! "));
                        }
                    }
                    else {
                        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }
            }
            return  new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e) {
            log.info("Error {} "+e);
        }
        return null;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Batch_processes>> getAllBatch_processess () {
        try {
            List<Batch_processes> batch_processess = batch_processesService.findAllBatch_processess();
            return  new ResponseEntity<>(batch_processess, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }

    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Batch_processes> getBatch_processesById (@PathVariable("id") Long id){
        try {
            Batch_processes batch_processes = batch_processesService.findBatch_processesById(id);
            return new ResponseEntity<>(batch_processes, HttpStatus.OK);

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Batch_processes> updateBatch_processes(@PathVariable("id") long id, @RequestBody Batch_processes batch_processes){
        try {
            Optional<Batch_processes> batch_processesData = batch_processesRepo.findBatch_processesById(id);
            if (batch_processesData.isPresent()) {
/*
     @Considerations
     The system should check if the parent process  date should not be greather than the current process new date.
     For instance
                    Parent Process 1 will have a process on date 27th.
                    Child process 2 will or rather must have a date of execution on date later than 27th.
                    @Setbacks
                    On date 31st, their might be an issue on running the dates schedules...
                    to solve that, i will be using month and date as a comparison toolkit
*/

                LocalDateTime initial = LocalDateTime.now();
                LocalDateTime currentMonth = initial.withDayOfMonth(1);
                LocalDateTime nextExecutionDate = currentMonth.plusDays(batch_processes.getExecution_date()).minusDays(1);
                LocalDateTime lastExecutionDate = nextExecutionDate.minusMonths(1);
                batch_processes.setExecuted_on(lastExecutionDate);
                batch_processes.setNext_execution(nextExecutionDate);

                Batch_processes _batch_processes = batch_processesData.get();
                _batch_processes.setExecution_date(batch_processes.getExecution_date());
                _batch_processes.setExecuted_on(batch_processes.getExecuted_on());
                _batch_processes.setNext_execution(batch_processes.getNext_execution());
                _batch_processes.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(batch_processesRepo.save(_batch_processes), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/on/enabled/{id}")
    public ResponseEntity<Batch_processes> updateOnEnabledBatchProcess(@PathVariable("id") long id){
        try {
            Optional<Batch_processes> batchProcessData = batch_processesRepo.findById(id);
            if (batchProcessData.isPresent()) {
                Batch_processes _batch = batchProcessData.get();
                _batch.setIs_enabled(true);
                _batch.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(batch_processesRepo.save(_batch), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/on/disabled/{id}")
    public ResponseEntity<Batch_processes> updateOnDisabledBatchProcess(@PathVariable("id") long id){
        try {
            Optional<Batch_processes> batchProcessData = batch_processesRepo.findById(id);
            if (batchProcessData.isPresent()) {
                Batch_processes _batch = batchProcessData.get();
                _batch.setIs_enabled(false);
                _batch.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(batch_processesRepo.save(_batch), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/permanent/delete/{id}")
    public ResponseEntity<Batch_processes> deleteBatch_process(@PathVariable("id") Long id){
        try {
            batch_processesRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

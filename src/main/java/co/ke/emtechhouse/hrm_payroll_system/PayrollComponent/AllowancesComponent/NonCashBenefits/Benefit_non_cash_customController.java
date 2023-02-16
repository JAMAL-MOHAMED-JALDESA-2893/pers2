package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.NonCashBenefits;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/benefit/custom")
@Slf4j
public class Benefit_non_cash_customController {

    @Autowired
    private Benefit_non_cash_customService benefit_noncash_customService;
    @Autowired
    private Benefit_non_cash_customRepo benefit_noncash_customRepo;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> addBenefitCustom(@RequestBody Benefit_non_cash_custom benefit_noncash_custom) {
        try {
//            Long empId=benefit_noncash_custom.getEmployee_id();
//            LocalDate start_date=benefit_noncash_custom.getStart_date();
//            LocalDate end_date =benefit_noncash_custom.getEnd_date();
//            LocalDate current_date=LocalDate.now();
//            Employee employee=employeeService.findEmployeeById(empId);
//            //confirm if employee is salary is closed
//            Boolean is_not_payable= employee.getIs_salary_closed();
//            if(is_not_payable){
//                return ResponseEntity.badRequest().body(new MessageResponse("Employee salary is closed"));
//            }else {
//                //confirm whether start date is greater than current date
//                if(start_date.isBefore(current_date)){
//                    return ResponseEntity.badRequest().body(new MessageResponse("The start date cannot be date earlier than today"));
//                }else {
//                    //confirm whether end date does not occur before start date
//                    if(end_date.isBefore(current_date)){
//                        return ResponseEntity.badRequest().body(new MessageResponse("The end date cannot be date before the start date"));
//                    }else {
//                        Benefit_non_cash_custom newBenefit_noncash_custom = benefit_noncash_customService.addBenefit_custom(benefit_noncash_custom);
//                        return new ResponseEntity<>(newBenefit_noncash_custom, HttpStatus.CREATED);
//                    }
//                }
//
//            }
            Benefit_non_cash_custom newBenefit_noncash_custom = benefit_noncash_customService.addBenefit_custom(benefit_noncash_custom);
            return new ResponseEntity<>(newBenefit_noncash_custom, HttpStatus.CREATED);

        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Benefit_non_cash_custom>> getBenefit_custom() {
        try {
            List<Benefit_non_cash_custom> benefit_noncash_customs = benefit_noncash_customService.getBenefit_custom();
            return new ResponseEntity<>(benefit_noncash_customs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Benefit_non_cash_custom> getComplainById(@PathVariable("id") Long id) {
        try {
            Benefit_non_cash_custom benefit_noncash_custom = benefit_noncash_customService.getBenefit_customById(id);
            return new ResponseEntity<>(benefit_noncash_custom, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Benefit_non_cash_custom> updateBenefit_custom(@PathVariable("id") long id,@RequestBody Benefit_non_cash_custom benefit_noncash_custom) {
        try {
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom= benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                Benefit_non_cash_custom newBenefit_noncash_custom = benefit_non_cash_custom.get();
                newBenefit_noncash_custom.setEmployee_id(benefit_noncash_custom.getEmployee_id());
                newBenefit_noncash_custom.setMonthly_valuation(benefit_noncash_custom.getMonthly_valuation());
                newBenefit_noncash_custom.setBenefit_for(benefit_noncash_custom.getBenefit_for());
                newBenefit_noncash_custom.setIs_taxable(benefit_noncash_custom.getIs_taxable());
//                newBenefit_noncash_custom.setStart_date(benefit_noncash_custom.getStart_date());
//                newBenefit_noncash_custom.setEnd_date(benefit_noncash_custom.getEnd_date());
                benefit_noncash_customRepo.save(newBenefit_noncash_custom);
                return new ResponseEntity<>(newBenefit_noncash_custom, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //TODO: Supervisor approval

    @PutMapping("/approve/by/supervisor/{id}")
    public ResponseEntity<Benefit_non_cash_custom> supervisorApprove(@PathVariable("id") long id){
        try {
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom = benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                Benefit_non_cash_custom newBenefit_non_cash_custom=benefit_non_cash_custom.get();
                newBenefit_non_cash_custom.setIs_supervisor_approved("approved");
                newBenefit_non_cash_custom.setIs_payable(true);
                newBenefit_non_cash_custom.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(benefit_noncash_customRepo.save(newBenefit_non_cash_custom), HttpStatus.OK);
            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    //TODO: HR approval

    @PutMapping("/approve/by/hr/{id}")
    public ResponseEntity<?> hrApprove(@PathVariable("id") long id){
        try {
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom = benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                //TODO: confirm supervisor approval
                Benefit_non_cash_custom newBenefit_non_cash_custom=benefit_non_cash_custom.get();
                if(newBenefit_non_cash_custom.getIs_supervisor_approved().equalsIgnoreCase("approved")){
                    newBenefit_non_cash_custom.setIs_hrm_approved("approved");
                    newBenefit_non_cash_custom.setIs_payable(true);
                    newBenefit_non_cash_custom.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(benefit_noncash_customRepo.save(newBenefit_non_cash_custom), HttpStatus.OK);
                }else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Failed! Not approved by a supervisor "));
                }

            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //TODO: Director approval

    @PutMapping("/approve/by/director/{id}")
    public ResponseEntity<?> directorApprove(@PathVariable("id") long id){
        try {
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom = benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                Benefit_non_cash_custom newBenefit_non_cash_custom=benefit_non_cash_custom.get();
                //TODO: confirm hr approval
                if(newBenefit_non_cash_custom.getIs_hrm_approved().equalsIgnoreCase("approved")){
                    newBenefit_non_cash_custom.setIs_director_approved("Approved");
                    newBenefit_non_cash_custom.setIs_payable(true);
                    newBenefit_non_cash_custom.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(benefit_noncash_customRepo.save(newBenefit_non_cash_custom), HttpStatus.OK);
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("Failed! Not approved by a supervisor "));
                }

            }{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @PutMapping("/reject/by/director")
    public ResponseEntity<Benefit_non_cash_custom> directorReject(@RequestParam long id, @RequestParam String rejection_reason_director){
        try{
            Optional<Benefit_non_cash_custom> benefit_non_cash_custom = benefit_noncash_customRepo.findById(id);
            if(benefit_non_cash_custom.isPresent()){
                Benefit_non_cash_custom newBenefit_non_cash_custom=benefit_non_cash_custom.get();
                newBenefit_non_cash_custom.setIs_director_approved("Rejected");
                newBenefit_non_cash_custom.setIs_payable(true);
                newBenefit_non_cash_custom.setRejection_reason_director(rejection_reason_director);
                return new ResponseEntity<>(benefit_noncash_customRepo.save(newBenefit_non_cash_custom), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //TODO: get all benefits by different approval status from director, hr ,supervisor
    @GetMapping("/all/by/all/approval/status")
    public ResponseEntity<List<Benefit_non_cash_custom>> findNonCashBenefitsByApprovalStatus(@RequestParam String supervisorApproval, @RequestParam String hrApproval, @RequestParam String directorApproval) {
        try {
            List<Benefit_non_cash_custom> benefit_noncash_customs = benefit_noncash_customService.findNonCashBenefitsByApprovalStatus(supervisorApproval,hrApproval,directorApproval);
            return new ResponseEntity<>(benefit_noncash_customs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //TODO: get all benefits by supervisor approval status
    @GetMapping("/all/by/supervisor/approval/status")
    public ResponseEntity<List<Benefit_non_cash_custom>> findNonCashBenefitsBySupervisorApprovalStatus(@RequestParam String supervisorApproval) {
        try {
            List<Benefit_non_cash_custom> benefit_noncash_customs = benefit_noncash_customService.findNonCashBenefitsBySupervisorApprovalStatus(supervisorApproval);
            return new ResponseEntity<>(benefit_noncash_customs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    //TODO: get all benefits by hr approval status
    @GetMapping("/all/by/hr/approval/status")
    public ResponseEntity<List<Benefit_non_cash_custom>> findNonCashBenefitsByHRApprovalStatus(@RequestParam String hrApproval) {
        try {
            List<Benefit_non_cash_custom> benefit_noncash_customs = benefit_noncash_customService.findNonCashBenefitsByHRApprovalStatus(hrApproval);
            return new ResponseEntity<>(benefit_noncash_customs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    //TODO: get all benefits by director approval status
    @GetMapping("/all/by/director/approval/status")
    public ResponseEntity<List<Benefit_non_cash_custom>> findNonCashBenefitsByDirectorApprovalStatus(@RequestParam String directorApproval) {
        try {
            List<Benefit_non_cash_custom> benefit_noncash_customs = benefit_noncash_customService.findNonCashBenefitsByDirectorApprovalStatus(directorApproval);
            return new ResponseEntity<>(benefit_noncash_customs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }


}

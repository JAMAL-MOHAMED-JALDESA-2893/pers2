package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/allowance/custom")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Allowance_customController {
    @Autowired
    private Allowance_customRepo allowance_customRepo;
    @Autowired
    private Allowance_customService allowance_customService;
    @PostMapping("/add")
    public ResponseEntity<Allowance_custom> addAllowance_custom(@RequestBody Allowance_custom allowance_custom){
        LocalDate currentDate= LocalDate.now();
        Month monthDate = currentDate.getMonth();
//        String month = monthDate.toString();
//        int yearDate = currentDate.getYear();
//        String year = String.valueOf(yearDate);
//        allowance_custom.setMonth(month);
//        allowance_custom.setYear(year);
        Allowance_custom newAllowance_custom = allowance_customService.addAllowance_custom(allowance_custom);
        return  new ResponseEntity<>(newAllowance_custom, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Allowance_custom_detail>> getAllAllowance_customs () {
        List<Allowance_custom_detail> allowance_customs = allowance_customService.findAllAllowance_customs();
        return  new ResponseEntity<>(allowance_customs, HttpStatus.OK);
    }
    @GetMapping("/all/detailed/employee/allowances")
    public ResponseEntity<?> getDetailedAllowance () {
        List<Allowance_customRepo.Allowance_detail> allowance_customs = allowance_customRepo.findAllowanceDetailed();
        return  new ResponseEntity<>(allowance_customs, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Allowance_custom> getAllowance_customById (@PathVariable("id") Long id){
        Allowance_custom allowance_custom = allowance_customService.findAllowance_customById(id);
        return new ResponseEntity<>(allowance_custom, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Allowance_custom> updateAllowance_custom(@PathVariable("id") long id, @RequestBody Allowance_custom allowance_custom){
        Optional<Allowance_custom> allowance_customData = allowance_customRepo.findAllowance_customById(id);

        if (allowance_customData.isPresent()) {
            Allowance_custom _allowance_custom = allowance_customData.get();
            _allowance_custom.setAmount(allowance_custom.getAmount());
            _allowance_custom.setAllowance_for(allowance_custom.getAllowance_for());
            _allowance_custom.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(allowance_customRepo.save(_allowance_custom), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/approve/by/director/{id}")
    public ResponseEntity<Allowance_custom> directorApprove(@PathVariable("id") long id, @RequestBody Allowance_custom allowance_custom){
        System.out.println(id);
        Optional<Allowance_custom> allowance_customData = allowance_customRepo.findAllowance_customById(id);
        if (allowance_customData.isPresent()) {
            Allowance_custom _allowance_custom = allowance_customData.get();
            _allowance_custom.setIs_director_approved("Approved");
            _allowance_custom.setIs_payable(true);
            _allowance_custom.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(allowance_customRepo.save(_allowance_custom), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/reject/by/director/{id}")
    public ResponseEntity<Allowance_custom> directorReject(@PathVariable("id") long id, @RequestBody Allowance_custom allowance_custom){
        System.out.println(id);
        Optional<Allowance_custom> allowance_customData = allowance_customRepo.findAllowance_customById(id);
        if (allowance_customData.isPresent()) {
            Allowance_custom _allowance_custom = allowance_customData.get();
            _allowance_custom.setIs_director_approved("Reject");
            _allowance_custom.setIs_payable(false);
            _allowance_custom.setRejection_reason_director(allowance_custom.getRejection_reason_director());
            _allowance_custom.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(allowance_customRepo.save(_allowance_custom), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Allowance_custom> deleteAllowance_custom(@PathVariable("id") Long id){
//        Check if allowance_custom has got employees
        Optional<Allowance_custom> allowance_customData = allowance_customRepo.findAllowance_customById(id);
        if (allowance_customData.isPresent()){
            Allowance_custom _allowance_custom = allowance_customData.get();
            _allowance_custom.setIs_deleted(true);
            _allowance_custom.setDeleted_at(LocalDateTime.now());
            return new ResponseEntity<>(allowance_customRepo.save(_allowance_custom), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

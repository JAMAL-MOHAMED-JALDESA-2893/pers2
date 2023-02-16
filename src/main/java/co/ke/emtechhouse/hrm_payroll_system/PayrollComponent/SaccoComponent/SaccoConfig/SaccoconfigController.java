package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
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
@RequestMapping("/api/v1/sacco/config/")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class SaccoconfigController {
    @Autowired
    private SaccoconfigRepo saccoconfigRepo;
    @Autowired
    private SaccoconfigService saccoconfigService;

    @PostMapping("/add")
    public ResponseEntity<?> addSaccoconfig(@RequestBody Saccoconfig saccoconfig){
        Double min_deduction_percentage = saccoconfig.getMin_deduction_Percentage();
        Double max_deduction_percentage = saccoconfig.getMax_deduction_Percentage();
        if (min_deduction_percentage<1 || min_deduction_percentage >=100 || min_deduction_percentage>max_deduction_percentage || max_deduction_percentage <1 || max_deduction_percentage >= 100){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Kindly check your percentages! "));
        }else{
            Saccoconfig newSaccoconfig = saccoconfigService.addSaccoconfig(saccoconfig);
            return  new ResponseEntity<>(newSaccoconfig, HttpStatus.CREATED);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Saccoconfig>> getAllSaccoconfigs () {
        List<Saccoconfig> saccoconfigs = saccoconfigService.findAllSaccoconfigs();
        return  new ResponseEntity<>(saccoconfigs, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Saccoconfig> getSaccoconfigById (@PathVariable("id") Long id){
        Saccoconfig saccoconfig = saccoconfigService.findSaccoconfigById(id);
        return new ResponseEntity<>(saccoconfig, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Saccoconfig> updateSaccoconfig(@PathVariable("id") long id, @RequestBody Saccoconfig saccoconfig){
        Optional<Saccoconfig> saccoconfigData = saccoconfigRepo.findSaccoconfigById(id);
        if (saccoconfigData.isPresent()) {
            Saccoconfig _saccoconfig = saccoconfigData.get();
            _saccoconfig.setMin_deduction_Percentage(saccoconfig.getMin_deduction_Percentage());
            _saccoconfig.setMax_deduction_Percentage(saccoconfig.getMax_deduction_Percentage());
            _saccoconfig.setYear(saccoconfig.getYear());
            _saccoconfig.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(saccoconfigRepo.save(_saccoconfig), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Saccoconfig> deleteSaccoconfig(@PathVariable("id") Long id){
        saccoconfigRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent.AbsentismConfigurations;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
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
@RequestMapping("/api/v1/absentism/configuration")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AbsentismconfigController {
    @Autowired
    private AbsentismconfigRepo absentismconfigRepo;
    @Autowired
    private AbsentismconfigService absentismconfigService;

    @PostMapping("/add")
    public ResponseEntity<?> addAbsentismconfig(@RequestBody Absentismconfig absentismconfig){
       Double deduction_rate = absentismconfig.getDeduction_rate();
        if (deduction_rate > 100 | deduction_rate < 0 ){
            return ResponseEntity.badRequest().body(new MessageResponse("Check Percentage range can not be greater than 100% or less than 0!"));
        }
        else{
//            check if exist
            List<Absentismconfig> allAbsentismconfig = absentismconfigService.findAllAbsentismconfigs();
            if (allAbsentismconfig.size()>0){
                return ResponseEntity.badRequest().body(new MessageResponse("You have an existing configurations! kindly update or delete it to set new one!"));
            }else {
                Absentismconfig newAbsentismconfig = absentismconfigService.addAbsentismconfig(absentismconfig);
                return  new ResponseEntity<>(newAbsentismconfig, HttpStatus.CREATED);
            }
//
        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<Absentismconfig>> getAllAbsentismconfigs () {
        List<Absentismconfig> absentismconfigs = absentismconfigService.findAllAbsentismconfigs();
        return  new ResponseEntity<>(absentismconfigs, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Absentismconfig> getAbsentismconfigById (@PathVariable("id") Long id){
        Absentismconfig absentismconfig = absentismconfigService.findAbsentismconfigById(id);
        return new ResponseEntity<>(absentismconfig, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAbsentismconfig(@PathVariable("id") long id, @RequestBody Absentismconfig absentismconfig){
        Double deduction_rate = absentismconfig.getDeduction_rate();
        if (deduction_rate > 100 | deduction_rate < 0 ){
            return ResponseEntity.badRequest().body(new MessageResponse("Check Percentage range can not be greater than 100% or less than 0!"));
        }else {
            Optional<Absentismconfig> absentismconfigData = absentismconfigRepo.findAbsentismconfigById(id);
            if (absentismconfigData.isPresent()) {
                Absentismconfig _absentismconfig = absentismconfigData.get();
                _absentismconfig.setMode_of_deduction(absentismconfig.getMode_of_deduction());
                _absentismconfig.setPeriod(absentismconfig.getPeriod());
                _absentismconfig.setDeduction_rate(absentismconfig.getDeduction_rate());
                _absentismconfig.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(absentismconfigRepo.save(_absentismconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
    @PutMapping("/disabled/{id}")
    public ResponseEntity<Absentismconfig> deleteAbsentismconfig(@PathVariable("id") Long id, @RequestBody Absentismconfig absentismconfig){
        Optional<Absentismconfig> absentismconfigData = absentismconfigRepo.findAbsentismconfigById(id);
        if (absentismconfigData.isPresent()) {
            Absentismconfig _absentismconfig = absentismconfigData.get();
            _absentismconfig.setIs_disabled(absentismconfig.getIs_disabled());
            _absentismconfig.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(absentismconfigRepo.save(_absentismconfig), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        absentismconfigRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

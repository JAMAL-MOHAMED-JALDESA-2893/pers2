package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations;

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
@RequestMapping("/api/v1/configurations/nssf/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class NssfController {

    private final NssfService nssfService;
    private final NssfRepo nssfRepo;

    @Autowired
    public NssfController(NssfService nssfService, NssfRepo nssfRepo) {
        this.nssfService = nssfService;
        this.nssfRepo = nssfRepo;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addNssf(@RequestBody Nssf nssf){
        Double employee_payment_rate = nssf.getEmployee_payment_rate();
        Double company_payment_rate = nssf.getCompany_payment_rate();
//        check for valid employee pay rate
        if(employee_payment_rate < 0 | employee_payment_rate > 100){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Employee pay rate Invalid Enter in scale of (0-100)!"));
        }else{
//            check for valide company pay rate
            if(company_payment_rate < 0 | company_payment_rate > 100){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Company pay rate Invalid Enter in scale of (0-100)!"));
            }else{
//                All condition met
                Double total_rate = employee_payment_rate + company_payment_rate;
                Double total_nssf_rate = total_rate/100;
                nssf.setTotal_nssf_rate(total_nssf_rate);
                Nssf newNssf = nssfService.addNssf(nssf);
                return  new ResponseEntity<>(newNssf, HttpStatus.CREATED);
            }
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Nssf>> getAllNssf () {
        List<Nssf> nssfs = nssfService.findAllNssf();
        return  new ResponseEntity<>(nssfs, HttpStatus.OK);
    }
    @GetMapping("/find/limited")
    public ResponseEntity<?> getLimited (){
        Optional<Nssf> nssf = nssfRepo.findNssf();
        return new ResponseEntity<>(nssf, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Nssf> getNssfById (@PathVariable("id") Long id){
        Nssf nssf = nssfService.findNssfById(id);
        return new ResponseEntity<>(nssf, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNssf(@PathVariable("id") long id, @RequestBody Nssf nssf){
        Double employee_payment_rate = nssf.getEmployee_payment_rate();
        Double company_payment_rate = nssf.getCompany_payment_rate();
//        check for valid employee pay rate
        if(employee_payment_rate < 0 | employee_payment_rate > 100){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Employee pay rate Invalid Enter in scale of (0-100)!"));
        }else{
//            check for valide company pay rate
            if(company_payment_rate < 0 | company_payment_rate > 100){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Company pay rate Invalid Enter in scale of (0-100)!"));
            }else{
//                All condition met
                Double total_rate = employee_payment_rate + company_payment_rate;
                Double total_nssf_rate = total_rate/100;

                Optional<Nssf> nssfData = nssfRepo.findNssfById(id);
                if (nssfData.isPresent()) {
                    Nssf _nssf = nssfData.get();
                    _nssf.setTax_band(nssf.getTax_band());
                    _nssf.setMin_nssf(nssf.getMin_nssf());
                    _nssf.setMax_nssf(nssf.getMax_nssf());
                    _nssf.setEmployee_payment_rate(nssf.getEmployee_payment_rate());
                    _nssf.setCompany_payment_rate(nssf.getCompany_payment_rate());
                    _nssf.setTotal_nssf_rate(total_nssf_rate);
                    _nssf.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(nssfRepo.save(_nssf), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }


    }
    @DeleteMapping("/permanent/delete/{id}")
    public ResponseEntity<Nssf> deleteNssf(@PathVariable("id") Long id){
        nssfService.deleteNssf(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

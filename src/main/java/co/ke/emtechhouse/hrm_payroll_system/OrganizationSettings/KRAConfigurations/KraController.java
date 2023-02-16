package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/configurations/kra/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class KraController {
    private final KraRepo kraRepo;
    private final KraService kraService;

    public KraController(KraRepo kraRepo, KraService kraService) {
        this.kraRepo = kraRepo;
        this.kraService = kraService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addKra(@RequestBody Kra kra){
//        check if taxband already exist
        Optional<Kra> existingConfig = kraRepo.checkIFTaxBandexist(kra.getTax_band());
        if (existingConfig.isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Tax band already existed!"));
        }else{
            Double rate = kra.getRate();
//        check for valid employee pay rate
            if(rate < 0 | rate > 100){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Employee pay rate Invalid Enter in scale of (0-100)!"));
            }else{
//           valid pay rate
                Kra newKra = kraService.addKra(kra);
                return  new ResponseEntity<>(newKra, HttpStatus.CREATED);
            }
        }


    }
    @GetMapping("/all")
    public ResponseEntity<List<Kra>> getAllKra () {
        List<Kra> kras = kraService.findAllKra();
        return  new ResponseEntity<>(kras, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Kra> getKraById (@PathVariable("id") Long id){
        Kra kra = kraService.findKraById(id);
        return new ResponseEntity<>(kra, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKra(@PathVariable("id") long id, @RequestBody Kra kra){
        Double rate = kra.getRate();
//        check for valid employee pay rate
        if(rate < 0 | rate > 100){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Employee pay rate Invalid Enter in scale of (0-100)!"));
        }else{
//           valid pay rate
            Optional<Kra> kraData =  kraRepo.findKraById(id);
            if (kraData.isPresent()) {
                Kra _kra = kraData.get();
                _kra.setTax_band(kra.getTax_band());
                _kra.setAmount_annual(kra.getAmount_annual());
                _kra.setAmount_monthly(kra.getAmount_monthly());
                _kra.setRate(kra.getRate());
                _kra.setPersonal_relief_annualy(kra.getPersonal_relief_annualy());
                _kra.setPersonal_relief_monthly(kra.getPersonal_relief_monthly());
                _kra.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(kraRepo.save(_kra), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }


    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Kra> deleteKra(@PathVariable("id") Long id){
        kraRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
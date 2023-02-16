package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations;

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
@RequestMapping("/api/v1/configurations/nhif/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class NhifController {
    private final NhifService nhifService;
    @Autowired
    private NhifRepo nhifRepo;
    public NhifController(NhifService nhifService) {
        this.nhifService = nhifService;
    }
    @PostMapping("/add")
    public ResponseEntity<Nhif> addNhif(@RequestBody Nhif nhif){
        Optional<Nhif> Tax_band = nhifRepo.findLastTaxBandOptional();
        if(Tax_band.isPresent()){
            Nhif _nhif = Tax_band.get();
            Double lastMax = _nhif.getMax_threshold();
            Double newMinThreshold = lastMax + 1;
            nhif.setMin_threshold(newMinThreshold);
            Nhif newNhif = nhifService.addNhif(nhif);
            return  new ResponseEntity<>(newNhif, HttpStatus.CREATED);
        }
        Nhif newNhif = nhifService.addNhif(nhif);
        return  new ResponseEntity<>(newNhif, HttpStatus.CREATED);

    }
    @GetMapping("/all")
    public ResponseEntity<List<Nhif>> getAllNhif () {
        List<Nhif> nhifs = nhifService.findAllNhif();
        return  new ResponseEntity<>(nhifs, HttpStatus.OK);
    }
    @GetMapping("/fetch/last/max")
    public ResponseEntity<Nhif> getLastMax () {
        Nhif nhifs = nhifRepo.findLastTaxBand();
        return  new ResponseEntity<>(nhifs, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Nhif> getNhifById (@PathVariable("id") Long id){
        Nhif nhif = nhifService.findNhifById(id);
        return new ResponseEntity<>(nhif, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNhif(@PathVariable("id") long id, @RequestBody Nhif nhif){
//        check if is present
        Optional<Nhif> nhifObjectData = nhifRepo.findNhifById(id);
        if (nhifObjectData.isPresent()) {
//            check if new max < min threshold
            Double currentMinThreshold = nhifObjectData.get().getMin_threshold();
            if(nhif.getMax_threshold() <= currentMinThreshold   ){
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                return ResponseEntity.badRequest().body(new MessageResponse("Max Value Cannot be less than Min Value!"));
            }else {
                //        get next id
                Long newBandId = id+1;
                Optional<Nhif> newBand = nhifRepo.findNhifById(newBandId);
                if(newBand.isPresent()){
                    Nhif _nhif = newBand.get();
//            Get prev max threshold
                    Double Newmaxthreshold = nhif.getMax_threshold();
                    Double newNextMin = Newmaxthreshold + 1;
                    _nhif.setMin_threshold(newNextMin);
                    _nhif.setTax_band(nhif.getTax_band());
                    _nhif.setUpdated_at(LocalDateTime.now());
                    nhifRepo.save(_nhif);
                }
                Optional<Nhif> nhifData = nhifRepo.findNhifById(id);
                if (nhifData.isPresent()) {
                    Nhif _nhif = nhifData.get();
                    _nhif.setMax_threshold(nhif.getMax_threshold());
                    _nhif.setNhif_deduction(nhif.getNhif_deduction());
                    _nhif.setTax_band(nhif.getTax_band());
                    _nhif.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(nhifRepo.save(_nhif), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

        }
        return null;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNhif(@PathVariable("id") Long id){
        System.out.println(id);
//        get band by id
        Optional<Nhif> cBand = nhifRepo.findById(id);
        if (cBand.isPresent()){
            //        get min threshold
            Double cMinimumThreshold = cBand.get().getMin_threshold();
            //        get max threshold
            Double cMAximumThreshold = cBand.get().getMax_threshold();
            Double prevMaxThreshold = cMinimumThreshold - 1;
            //        get prev max == select from db where max threshold == c-minthreshold-1
            Optional<Nhif> prevBand = nhifRepo.findByMaxThreshold(prevMaxThreshold);
            if (prevBand.isPresent()){
                //        update prev max band = max threshold
                Nhif _updated =  prevBand.get();
                _updated.setMax_threshold(cMAximumThreshold);
                _updated.setUpdated_at(LocalDateTime.now());
                nhifRepo.save(_updated);
                //        delete the band
                nhifRepo.deleteById(id);
            }else {
//                No Prev Band set Next start to 0
//                get current cmaxhreshold
//                get nextMinthreshold = cMaxTHreshold + 1;
                  Double nextMinThreshold = cMAximumThreshold + 1;
//                find by minimum threshold and update to 0
                Optional<Nhif> _existing = nhifRepo.findByMinThreshold(nextMinThreshold);
                if (_existing.isPresent()){
//                    update and delete
                    Nhif _new = _existing.get();
                    _new.setMin_threshold(cMinimumThreshold);
                    _new.setUpdated_at(LocalDateTime.now());
                    nhifRepo.save(_new);
                    nhifRepo.deleteById(id);
                }else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Contact Developer!"));
                }
            }
        }else {
            return ResponseEntity.badRequest().body(new MessageResponse("Not Found!"));
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
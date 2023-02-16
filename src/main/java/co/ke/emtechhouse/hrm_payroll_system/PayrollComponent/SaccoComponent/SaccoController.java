package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/sacco/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class SaccoController {
    @Autowired
    private SaccoRepo saccoRepo;
    @Autowired
    private SaccoService saccoService;

    @PostMapping("/add")
    public ResponseEntity<Sacco> addSacco(@RequestBody Sacco sacco){

        Sacco newSacco = saccoService.addSacco(sacco);
        return  new ResponseEntity<>(newSacco, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Sacco>> getAllSaccos () {
        List<Sacco> saccos = saccoService.findAllSaccos();
        return  new ResponseEntity<>(saccos, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Sacco> getSaccoById (@PathVariable("id") Long id){
        Sacco sacco = saccoService.findSaccoById(id);
        return new ResponseEntity<>(sacco, HttpStatus.OK);
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Sacco> updateSacco(@PathVariable("id") long id, @RequestBody Sacco sacco){
//        Optional<Sacco> saccoData = saccoRepo.findSaccoById(id);
//        if (saccoData.isPresent()) {
//            Sacco _sacco = saccoData.get();
//            _sacco.setSaccoName(sacco.getSaccoName());
//            _sacco.setDirectorOfSacco(sacco.getDirectorOfSacco());
//            _sacco.setSaccoMail(sacco.getSaccoMail());
//            _sacco.setStatus(sacco.getStatus());
//            _sacco.setDeleted(sacco.getDeleted());
//            return new ResponseEntity<>(saccoRepo.save(_sacco), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent/delete/{id}")
    public ResponseEntity<Sacco> deleteSacco(@PathVariable("id") Long id){
        saccoRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

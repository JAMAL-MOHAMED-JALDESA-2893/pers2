//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.PerdeamComponent;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/v1/allowance/perdeam/")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//public class PerdeamController {
//    private final PerdeamRipo  perdeamRipo;
//    private final PerdeamService perdeamService;
//
//    public PerdeamController(PerdeamRipo perdeamRipo, PerdeamService perdeamService) {
//        this.perdeamRipo = perdeamRipo;
//        this.perdeamService = perdeamService;
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<Allowance_Perdeam> addPerdeam(@RequestBody Allowance_Perdeam allowance_perdeam){
//
//        Allowance_Perdeam newAllowance_Perdeam = perdeamService.addPerdeam(allowance_perdeam);
//        return  new ResponseEntity<>(newAllowance_Perdeam, HttpStatus.CREATED);
//    }
//    @GetMapping("/all")
//    public ResponseEntity<List<Allowance_Perdeam>> getAllAllowance_Perdeams () {
//        List<Allowance_Perdeam> allowance_perdeams = perdeamService.findAllPerdeam();
//        return  new ResponseEntity<>(allowance_perdeams, HttpStatus.OK);
//    }
//    @GetMapping("/find/{id}")
//    public ResponseEntity<Allowance_Perdeam> getAllowance_PerdeamById (@PathVariable("id") Long id){
//        Allowance_Perdeam allowance_perdeam = perdeamService.findPerdeamById(id);
//        return new ResponseEntity<>(allowance_perdeam, HttpStatus.OK);
//    }
////    @PutMapping("/update/{id}")
////    public ResponseEntity<Allowance_Perdeam> updateAllowance_Perdeam(@PathVariable("id") long id, @RequestBody Allowance_Perdeam allowance_perdeam){
////        Optional<Allowance_Perdeam> allowance_perdeamData = perdeamRepo.findAllowance_PerdeamById(id);
////        if (allowance_perdeamData.isPresent()) {
////            Allowance_Perdeam _allowance_perdeam = allowance_perdeamData.get();
////            _allowance_perdeam.setDeleted(allowance_perdeam.getDeleted());
////            return new ResponseEntity<>(allowance_perdeamRepo.save(_allowance_perdeam), HttpStatus.OK);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
////    Warning! : THE SYSTEM IS REQUIRED TO KEEP THE HISTORY OF PROMOTION FOREVER.
////    @DeleteMapping("/delete/{id}")
////    public ResponseEntity<Allowance_Perdeam> deleteAllowance_Perdeam(@PathVariable("id") Long id){
////        perdea.deleteAllowance_Perdeams(id);
////        return new ResponseEntity<>(HttpStatus.OK);
////    }
//}
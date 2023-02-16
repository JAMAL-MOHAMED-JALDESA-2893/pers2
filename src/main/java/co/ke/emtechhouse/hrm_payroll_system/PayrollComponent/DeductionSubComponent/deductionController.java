//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionSubComponent;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/deductions")
////@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
//public class deductionController {
//    private final DeductionService deductionService;
//
//    public deductionController(DeductionService deductionService) {
//        this.deductionService = deductionService;
//    }
//
//    @PostMapping("/add")
//    public  ResponseEntity<Deductions> addDEduction(@RequestBody Deductions deductions){
//        Deductions newDeduction = deductionService.addDeduction(deductions);
//        return  new ResponseEntity<>(newDeduction, HttpStatus.CREATED);
//    }
//    @GetMapping("/all")
//    public ResponseEntity<List<Deductions>> getAllDeductions () {
//        List<Deductions> deduction = deductionService.findAllDeductions();
//        return  new ResponseEntity<>(deduction, HttpStatus.OK);
//    }
//    @GetMapping("/find/{id}")
//    public ResponseEntity<Deductions> getDeductionById (@PathVariable("id") Long id){
//        Deductions deduction = deductionService.findDeductionById(id);
//        return new ResponseEntity<>(deduction, HttpStatus.OK);
//    }
//    @PutMapping("/update")
//    public ResponseEntity<Deductions> updateDeduction(@RequestBody Deductions deductions){
//        Deductions updateDeduction = deductionService.updateDeduction(deductions);
//        return new ResponseEntity<>(updateDeduction, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Deductions> deleteDeduction(@PathVariable("id") Long id){
//        deductionService.deleteDeductions(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//}

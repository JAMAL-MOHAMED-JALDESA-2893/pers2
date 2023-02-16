package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionsComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/deduction")
@Slf4j
public class DeductionController {
    @Autowired
    private  DeductionService deductionService;

    @PostMapping("/add")
    public ResponseEntity<?> addDeduction(@RequestBody Deduction deduction) {
        try {
            Deduction newDeduction = deductionService.addDeduction(deduction);
            return new ResponseEntity<>(newDeduction, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
}

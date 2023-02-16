package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/promotions/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class PromotionController {
    private final PromotionRepo promotionRepo;
    private final PromotionService promotionService;

    public PromotionController(PromotionRepo promotionRepo, PromotionService promotionService) {
        this.promotionRepo = promotionRepo;
        this.promotionService = promotionService;
    }
    @PostMapping("/add")
    public ResponseEntity<Promotion> addPromotion(@RequestBody Promotion promotion){

        Promotion newPromotion = promotionService.addPromotion(promotion);
        return  new ResponseEntity<>(newPromotion, HttpStatus.CREATED);
    }
//    @GetMapping("/all")
//    public ResponseEntity<List<Promotion>> getAllPromotions () {
//        List<Promotion> promotions = promotionService.findAllPromotionData();
//        return  new ResponseEntity<>(promotions, HttpStatus.OK);
//    }
    @GetMapping("/all/data")
    public ResponseEntity<List<PromotionRepo.TotalDeductions>> getAllPromotionsData () {
        List<PromotionRepo.TotalDeductions> promotions = promotionService.findAllPromotionsData();
        return  new ResponseEntity<>(promotions, HttpStatus.OK);
    }
    @GetMapping("/all/data/per/status")
    public ResponseEntity<List<PromotionRepo.TotalDeductions>> getAllPromotionsDataPerStatus (@RequestParam String status) {
        List<PromotionRepo.TotalDeductions> promotions = promotionRepo.findAllPromotionsDataPerStatus(status);
        return  new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Promotion> getPromotionById (@PathVariable("id") Long id){
        Promotion promotion = promotionService.findPromotionById(id);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable("id") long id, @RequestBody Promotion promotion){
        Optional<Promotion> promotionData = promotionRepo.findPromotionById(id);
        if (promotionData.isPresent()) {
            Promotion _promotion = promotionData.get();
            _promotion.setIs_deleted(promotion.getIs_deleted());
            return new ResponseEntity<>(promotionRepo.save(_promotion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    Director Approval
    @PutMapping("/director/approve/{id}")
    public ResponseEntity<Promotion> directorAprrovePromotion(@PathVariable("id") long id, @RequestBody Promotion promotion){
        Optional<Promotion> promotionData = promotionRepo.findPromotionById(id);
        Boolean director_approved = true;
        if (promotionData.isPresent()) {
            Promotion _promotion = promotionData.get();
            _promotion.setDirector_approved(director_approved);
            _promotion.setPromoted_at(LocalDateTime.now());
            return new ResponseEntity<>(promotionRepo.save(_promotion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    Supervisor Approval
@PutMapping("/supervisor/approve/{id}")
public ResponseEntity<Promotion> supervisorAprrovePromotion(@PathVariable("id") long id, @RequestBody Promotion promotion){
    Optional<Promotion> promotionData = promotionRepo.findPromotionById(id);
    Boolean supervisor_approved = true;
    if (promotionData.isPresent()) {
        Promotion _promotion = promotionData.get();
        _promotion.setDirector_approved(supervisor_approved);
        _promotion.setPromoted_at(LocalDateTime.now());
        return new ResponseEntity<>(promotionRepo.save(_promotion), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
//    HRM Approval
@PutMapping("/hrm/approve/{id}")
public ResponseEntity<Promotion> hrmAprrovePromotion(@PathVariable("id") long id, @RequestBody Promotion promotion){
    Optional<Promotion> promotionData = promotionRepo.findPromotionById(id);
    Boolean hrm_approved = true;
    if (promotionData.isPresent()) {
        Promotion _promotion = promotionData.get();
        _promotion.setDirector_approved(hrm_approved);
        _promotion.setPromoted_at(LocalDateTime.now());
        return new ResponseEntity<>(promotionRepo.save(_promotion), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
//    Warning! : THE SYSTEM IS REQUIRED TO KEEP THE HISTORY OF PROMOTION FOREVER.
    @PutMapping("/trash/{id}")
    public ResponseEntity<Promotion> TrashPromotion(@PathVariable("id") Long id){
        System.out.println("Got Called");
        Optional<Promotion> employee = promotionRepo.findById(id);
        if (employee.isPresent()){
            Promotion _cureentEmployee = employee.get();
            _cureentEmployee.setIs_trashed(true);
            _cureentEmployee.setStatus("Trashed");
            _cureentEmployee.setDeleted_at(LocalDateTime.now());
            return new ResponseEntity<>(promotionRepo.save(_cureentEmployee), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        @PutMapping("/delete/{id}")
        public ResponseEntity<Promotion> deletePromotion(@PathVariable("id") Long id){
            System.out.println("Got Called");
            Optional<Promotion> employee = promotionRepo.findById(id);
            if (employee.isPresent()){
                Promotion _cureentEmployee = employee.get();
                _cureentEmployee.setIs_deleted(true);
                _cureentEmployee.setStatus("Trashed");
                _cureentEmployee.setDeleted_at(LocalDateTime.now());
                return new ResponseEntity<>(promotionRepo.save(_cureentEmployee), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}

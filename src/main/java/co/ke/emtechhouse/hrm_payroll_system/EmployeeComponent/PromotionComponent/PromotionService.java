package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.PromotionComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PromotionService {
    private final PromotionRepo promotionRepo;

    public PromotionService(PromotionRepo promotionRepo) {
        this.promotionRepo = promotionRepo;
    }
    public Promotion addPromotion(Promotion promotion){
        return promotionRepo.save(promotion);
    }
    public List<PromotionRepo.TotalDeductions> findAllPromotionsData(){
        return promotionRepo.findAllPromotionsData();
    }
//    public List<Promotion> findAllPromotionData(){
//        return promotionRepo.findAllPromotionData();
//    }
    public Promotion findPromotionById(Long id){
        return promotionRepo.findPromotionById(id).orElseThrow(()-> new DataNotFoundException("Promotion by " + id +"was not found"));
    }
    public Promotion updatePromotion(Promotion promotion){
        return promotionRepo.save(promotion);
    }
    public void deletePromotionType(Long id){
        promotionRepo.deletePromotionById(id);
    }
//}
}

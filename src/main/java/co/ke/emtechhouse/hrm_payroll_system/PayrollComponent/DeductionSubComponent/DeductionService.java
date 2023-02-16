//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.DeductionSubComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system._exception.DeductionNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DeductionService {
//    private final DeductionsRepo deductionsRepo;
//
//    @Autowired
//    public DeductionService(DeductionsRepo deductionsRepo){
//        this.deductionsRepo = deductionsRepo;
//    }
//    public Deductions addDeduction(Deductions deductions){
//        return deductionsRepo.save(deductions);
//    }
//    public List<Deductions> findAllDeductions(){
//        return  deductionsRepo.findAll();
//    }
//    public  Deductions updateDeduction(Deductions deductions){
//        return  deductionsRepo.save(deductions);
//    }
//    public Deductions findDeductionById(Long id){
//        return deductionsRepo.findDeductionById(id).orElseThrow(()->new DeductionNotFoundException("DEduction by id"+ id + "was not found"));
//    }
//    public void deleteDeductions(Long id){
//        deductionsRepo.deleteDeductionById(id);
//    }
//}

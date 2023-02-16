package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalaryParams;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvanceSalaryParamsService {
    @Autowired
    AdvanceSalaryParamsRepo advanceSalaryParamsRepo;

    public AdvanceSalaryParams addAdvanceSalaryParams(AdvanceSalaryParams advanceSalaryParams){
        return advanceSalaryParamsRepo.save(advanceSalaryParams);
    }
    public List<AdvanceSalaryParams> findAllAdvanceSalaryParams(){
        return advanceSalaryParamsRepo.findAll();
    }
    public AdvanceSalaryParams findAdvanceSalaryParamsById(Long id){
        return advanceSalaryParamsRepo.findAdvanceSalaryParamsById(id).orElseThrow(()-> new DataNotFoundException("AdvanceSalaryParams " + id +"was not found"));
    }
    //    public List<AdvanceSalaryParams> findAdvanceSalaryParamsByUserId(String user_id){
//
//        return advanceSalaryParamsRepo.findByUserId(user_id);
//    }
    public AdvanceSalaryParams updateAdvanceSalaryParams(AdvanceSalaryParams advanceSalaryParams){

        return advanceSalaryParamsRepo.save(advanceSalaryParams);
    }
    public void deleteAdvanceSalaryParams(Long id){
        advanceSalaryParamsRepo.deleteById(id);
    }

}
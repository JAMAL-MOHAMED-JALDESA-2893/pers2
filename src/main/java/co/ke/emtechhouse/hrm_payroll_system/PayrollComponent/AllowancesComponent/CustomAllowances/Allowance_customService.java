package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.CustomAllowances;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Allowance_customService {
    @Autowired
    private Allowance_customRepo allowance_customRepo;

    public Allowance_custom addAllowance_custom(Allowance_custom allowance_custom){

        return allowance_customRepo.save(allowance_custom);
    }
    public List<Allowance_custom_detail> findAllAllowance_customs(){

        return allowance_customRepo.findAllowanceCustomDetail();
    }
    public Allowance_custom findAllowance_customById(Long id){
        return allowance_customRepo.findAllowance_customById(id).orElseThrow(()-> new DataNotFoundException("Allowance_custom " + id +"was not found"));
    }

    public Allowance_custom updateAllowance_custom(Allowance_custom allowance_custom){

        return allowance_customRepo.save(allowance_custom);
    }
    public void deleteAllowance_custom(Long id){
        allowance_customRepo.deleteById(id);
    }
}


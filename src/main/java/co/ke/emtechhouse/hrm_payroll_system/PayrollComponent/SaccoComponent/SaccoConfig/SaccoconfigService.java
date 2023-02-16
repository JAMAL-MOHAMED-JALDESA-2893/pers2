package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent.SaccoConfig;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaccoconfigService {
    @Autowired
    private SaccoconfigRepo saccoconfigRepo;

    public Saccoconfig addSaccoconfig(Saccoconfig saccoconfig){
        return saccoconfigRepo.save(saccoconfig);
    }
    public List<Saccoconfig> findAllSaccoconfigs(){
        return saccoconfigRepo.findAll();
    }
    public Saccoconfig findSaccoconfigById(Long id){
        return saccoconfigRepo.findSaccoconfigById(id).orElseThrow(()-> new DataNotFoundException("Saccoconfig " + id +"was not found"));
    }

    public Saccoconfig updateSaccoconfig(Saccoconfig saccoconfig){

        return saccoconfigRepo.save(saccoconfig);
    }
    public void deleteSaccoconfig(Long id){
        saccoconfigRepo.deleteById(id);
    }
}




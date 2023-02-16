package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.SaccoComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaccoService {
    @Autowired
    private SaccoRepo saccoRepo;

    public Sacco addSacco(Sacco sacco){
        return saccoRepo.save(sacco);
    }
    public List<Sacco> findAllSaccos(){
        return saccoRepo.findAll();
    }
    public Sacco findSaccoById(Long id){
        return saccoRepo.findSaccoById(id).orElseThrow(()-> new DataNotFoundException("Sacco " + id +"was not found"));
    }

    public Sacco updateSacco(Sacco sacco){

        return saccoRepo.save(sacco);
    }
    public void deleteSacco(Long id){
        saccoRepo.deleteById(id);
    }
}

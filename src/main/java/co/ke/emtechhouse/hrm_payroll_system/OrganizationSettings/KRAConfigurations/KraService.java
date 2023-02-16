package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.KRAConfigurations;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KraService {
    private final KraRepo kraRepo;

    public KraService(KraRepo kraRepo) {
        this.kraRepo = kraRepo;
    }

    public Kra addKra(Kra kra){
        return kraRepo.save(kra);
    }
    public List<Kra> findAllKra(){

        return kraRepo.findAll();
    }
    public Kra findKraById(Long id){
        return kraRepo.findKraById(id).orElseThrow(()-> new DataNotFoundException("Kra " + id +"was not found"));
    }
    //
    public Kra updateKra(Kra kra){
        return kraRepo.save(kra);
    }
    public void deleteKra(Long id){
        kraRepo.deleteKraById(id);
    }

}
package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NHIFConfigurations;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NhifService {

    private final NhifRepo nhifRepo;

    public NhifService(NhifRepo nhifRepo) {
        this.nhifRepo = nhifRepo;
    }

    public Nhif addNhif(Nhif nhif){
    return nhifRepo.save(nhif);
}
    public List<Nhif> findAllNhif(){
        return nhifRepo.findAll();
    }
    public Nhif findNhifById(Long id){
        return nhifRepo.findNhifById(id).orElseThrow(()-> new DataNotFoundException("Nhif " + id +"was not found"));
    }
    public Nhif updateNhif(Nhif nhif){
        return nhifRepo.save(nhif);
    }
    public void deleteNhif(Long id){
        nhifRepo.deleteNhifById(id);
    }

}
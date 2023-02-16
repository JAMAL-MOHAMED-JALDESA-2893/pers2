package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClearenceService {

    @Autowired
    private ClearenceRepo clearenceRepo;

    public Clearence addClearence(Clearence clearence){
        return clearenceRepo.save(clearence);
    }
    public List<Clearence> findAllClearence(){
        return clearenceRepo.findAll();
    }
    public Clearence findClearenceById(Long id){
        return clearenceRepo.findClearenceById(id).orElseThrow(()-> new DataNotFoundException("Clearence " + id +"was not found"));
    }

    public Clearence findClearenceByEmployeeId(Long id){
        return clearenceRepo.findClearenceByEmployeeId(id).orElseThrow(()-> new DataNotFoundException("Clearence " + id +"was not found"));
    }

    //
    public Clearence updateClearence(Clearence clearence){

        return clearenceRepo.save(clearence);
    }
    public void deleteClearence(Long id){
        clearenceRepo.deleteClearenceById(id);
    }
}

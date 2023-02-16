package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent.AbsentismConfigurations;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsentismconfigService {
    @Autowired
    private AbsentismconfigRepo absentismconfigRepo;

    public Absentismconfig addAbsentismconfig(Absentismconfig absentismconfig){

        return absentismconfigRepo.save(absentismconfig);
    }
    public List<Absentismconfig> findAllAbsentismconfigs(){

        return absentismconfigRepo.findAll();
    }
    public Absentismconfig findAbsentismconfigById(Long id){
        return absentismconfigRepo.findAbsentismconfigById(id).orElseThrow(()-> new DataNotFoundException("Absentismconfig " + id +"was not found"));
    }

    public Absentismconfig updateAbsentismconfig(Absentismconfig absentismconfig){

        return absentismconfigRepo.save(absentismconfig);
    }
    public void deleteAbsentismconfig(Long id){
        absentismconfigRepo.deleteById(id);
    }
}


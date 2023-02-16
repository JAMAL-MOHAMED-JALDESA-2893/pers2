package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AbsentismService {
    @Autowired
    private AbsentismRepo absentismRepo;

    public Absentism addAbsentism(Absentism absentism){

        return absentismRepo.save(absentism);
    }
    public List<Absentism> findAllAbsentisms(){

        return absentismRepo.findAll();
    }
    public Absentism findAbsentismById(Long id){
        return absentismRepo.findAbsentismById(id).orElseThrow(()-> new DataNotFoundException("Absentism " + id +"was not found"));
    }

    public Absentism updateAbsentism(Absentism absentism){

        return absentismRepo.save(absentism);
    }
    public void deleteAbsentism(Long id){
        absentismRepo.deleteById(id);
    }
}
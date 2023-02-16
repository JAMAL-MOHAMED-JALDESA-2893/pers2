package co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeClearence.ResignationBalance;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResignationamtService {
    @Autowired
    private ResignationamtRepo resignationamtRepo;

    public Resignationamt addResignationamt(Resignationamt resignationamt){
        return resignationamtRepo.save(resignationamt);
    }
    public List<Resignationamt> findAllResignationamt(){
        return resignationamtRepo.findAll();
    }
    public Resignationamt findResignationamtById(Long id){
        return resignationamtRepo.findResignationById(id).orElseThrow(()-> new DataNotFoundException("Resignationamt " + id +"was not found"));
    }

    public Resignationamt findResignationamtByEmployeeId(Long id){
        return resignationamtRepo.findResignationById(id).orElseThrow(()-> new DataNotFoundException("Resignationamt " + id +"was not found"));
    }
    public Resignationamt updateResignationamt(Resignationamt Resignationamt){
        return resignationamtRepo.save(Resignationamt);
    }
//    public void deleteResignationamt(Long id){
//        ResignationamtRepo.deleteResignationamtById(id);
//    }
}


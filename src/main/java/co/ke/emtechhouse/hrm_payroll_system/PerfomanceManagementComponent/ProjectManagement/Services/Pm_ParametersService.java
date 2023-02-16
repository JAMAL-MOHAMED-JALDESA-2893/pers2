package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Parameters;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_ParametersRepo;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_ParametersService {
    @Autowired
    private Pm_ParametersRepo pm_parametersRepo;

    public Pm_Parameters addPm_Parameters(Pm_Parameters pm_parameters){
        return pm_parametersRepo.save(pm_parameters);
    }
    public List<Pm_Parameters> findAllPm_Parameterss(){
        return pm_parametersRepo.findAll();
    }
    public Pm_Parameters findPm_ParametersById(Long id){
        return pm_parametersRepo.findPm_ParametersById(id).orElseThrow(()-> new DataNotFoundException("Pm_Parameters " + id +"was not found"));
    }
    //    public List<Pm_Parameters> findPm_ParametersByUserId(String user_id){
//
//        return pm_parametersRepo.findByUserId(user_id);
//    }
    public Pm_Parameters updatePm_Parameters(Pm_Parameters pm_parameters){

        return pm_parametersRepo.save(pm_parameters);
    }
    public void deletePm_Parameters(Long id){
        pm_parametersRepo.deleteById(id);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_DeliverablesRepo;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Deliverables;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_DeliverablesService {
    @Autowired
    private Pm_DeliverablesRepo pm_deliverablesRepo;

    public Pm_Deliverables addPm_Deliverables(Pm_Deliverables pm_deliverables){
        return pm_deliverablesRepo.save(pm_deliverables);
    }
    public List<Pm_Deliverables> findAllPm_Deliverabless(){
        return pm_deliverablesRepo.findAll();
    }
    public Pm_Deliverables findPm_DeliverablesById(Long id){
        return pm_deliverablesRepo.findPm_DeliverablesById(id).orElseThrow(()-> new DataNotFoundException("Pm_Deliverables " + id +"was not found"));
    }
//    public List<Pm_Deliverables> findPm_DeliverablesByUserId(String user_id){
//
//        return pm_deliverablesRepo.findByUserId(user_id);
//    }
    public Pm_Deliverables updatePm_Deliverables(Pm_Deliverables pm_deliverables){

        return pm_deliverablesRepo.save(pm_deliverables);
    }
    public void deletePm_Deliverables(Long id){
        pm_deliverablesRepo.deleteById(id);
    }
}

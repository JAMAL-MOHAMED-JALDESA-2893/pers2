package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_SummaryRepo;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Summary;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_SummaryService {
    @Autowired
    private Pm_SummaryRepo pm_summaryRepo;

    public Pm_Summary addPm_Summary(Pm_Summary pm_summary){
        return pm_summaryRepo.save(pm_summary);
    }
    public List<Pm_Summary> findAllPm_Summarys(){
        return pm_summaryRepo.findAll();
    }
    public Pm_Summary findPm_SummaryById(Long id){
        return pm_summaryRepo.findPm_SummaryById(id).orElseThrow(()-> new DataNotFoundException("Pm_Summary " + id +"was not found"));
    }
    //    public List<Pm_Summary> findPm_SummaryByUserId(String user_id){
//
//        return pm_summaryRepo.findByUserId(user_id);
//    }
    public Pm_Summary updatePm_Summary(Pm_Summary pm_summary){

        return pm_summaryRepo.save(pm_summary);
    }
    public void deletePm_Summary(Long id){
        pm_summaryRepo.deleteById(id);
    }
}

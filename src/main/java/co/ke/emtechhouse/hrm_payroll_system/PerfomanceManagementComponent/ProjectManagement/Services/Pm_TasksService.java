package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Services;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_TasksRepo;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Tasks;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_TasksService {
    @Autowired
    private Pm_TasksRepo tasksRepo;

    public Pm_Tasks addPm_Tasks(Pm_Tasks pm_tasks){
        return tasksRepo.save(pm_tasks);
    }
    public List<Pm_Tasks> findAllPm_Taskss(){
        return tasksRepo.findAll();
    }
    public List<Pm_Tasks> findPm_TasksByEmployeeId(Long id){
        return tasksRepo.findPm_TasksByEmployeeId(id);
    }


    public Pm_Tasks findPm_TasksById(Long id){
        return tasksRepo.findPm_TasksById(id).orElseThrow(()-> new DataNotFoundException("Pm_Tasks " + id +"was not found"));
    }
    //    public List<Pm_Tasks> findPm_TasksByUserId(String user_id){
//
//        return tasksRepo.findByUserId(user_id);
//    }
    public Pm_Tasks updatePm_Tasks(Pm_Tasks pm_tasks){

        return tasksRepo.save(pm_tasks);
    }
    public void deletePm_Tasks(Long id){
        tasksRepo.deleteById(id);
    }
}

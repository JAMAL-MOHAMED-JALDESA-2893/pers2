package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_goalService {
    @Autowired
    private Pm_goalRepo pm_goalRepo;

    public Pm_goal addPm_goal(Pm_goal pm_goal){
        return pm_goalRepo.save(pm_goal);
    }
    public List<Pm_goal> findAllPm_goals(){
        return pm_goalRepo.findAll();
    }
    public List<Pm_goalRepo.GoalParameter> findAllByEmployeeId(Long employee_id){
        return pm_goalRepo.findByEmployeeId(employee_id);
    }
    public List<Pm_goalRepo.GoalParameter> findGoalScoreByEmployeeId(Long employee_id){
        return pm_goalRepo.findByEmployeeId(employee_id);
    }

    public Pm_goal findPm_goalById(Long id){
        return pm_goalRepo.findPm_goalById(id).orElseThrow(()-> new DataNotFoundException("Pm_goal " + id +"was not found"));
    }
    public Pm_goalRepo.GoalParameter findByDetailsByGoalId(Long goal_id){
        return pm_goalRepo.findByDetailsByGoalId(goal_id).orElseThrow(()-> new DataNotFoundException("Pm_goal " + goal_id +"was not found"));
    }

    //    public List<Pm_goal> findPm_goalByUserId(String user_id){
//
//        return pm_goalRepo.findByUserId(user_id);
//    }
    public Pm_goal updatePm_goal(Pm_goal pm_goal){

        return pm_goalRepo.save(pm_goal);
    }
    public void deletePm_goal(Long id){
        pm_goalRepo.deleteById(id);
    }
}
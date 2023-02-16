package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_reviewService {
    @Autowired
    Pm_reviewRepo pm_reviewRepo;

    public Pm_review addPm_review(Pm_review pm_review){
        return pm_reviewRepo.save(pm_review);
    }
    public List<Pm_review> findAllPm_reviews(){
        return pm_reviewRepo.findAll();
    }
    public Pm_review findPm_reviewById(Long id){
        return pm_reviewRepo.findPm_reviewById(id).orElseThrow(()-> new DataNotFoundException("Pm_review " + id +"was not found"));
    }
    public Pm_review findLastReviewByEmployeeGoalId(Long employee_id, Long goal_id){
        return pm_reviewRepo.findLastReviewByEmployeeGoalId(employee_id,goal_id).orElseThrow(()-> new DataNotFoundException("Pm_review was not found"));
    }
    public List<Pm_review> findReviewByEmployeeId(Long employee_id){
        return pm_reviewRepo.findReviewByEmployeeId(employee_id);
    }

    ;
    //    public List<Pm_review> findPm_reviewByUserId(String user_id){
//
//        return pm_reviewRepo.findByUserId(user_id);
//    }
    public Pm_review updatePm_review(Pm_review pm_review){

        return pm_reviewRepo.save(pm_review);
    }
    public void deletePm_review(Long id){
        pm_reviewRepo.deleteById(id);
    }
}

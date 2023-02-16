package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments.Replies;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Pm_replyService {
    @Autowired
    private Pm_replyRepo replyRepo;

    public Pm_reply addReply(Pm_reply reply){
        return replyRepo.save(reply);
    }
    public List<Pm_reply> findAllReplys(){
        return replyRepo.findAll();
    }
    public Pm_reply findReplyById(Long id){
        return replyRepo.findReplyById(id).orElseThrow(()-> new DataNotFoundException("Reply " + id +"was not found"));
    }
    //    public List<Reply> findReplyByUserId(String user_id){
//
//        return replyRepo.findByUserId(user_id);
//    }
    public Pm_reply updateReply(Pm_reply reply){

        return replyRepo.save(reply);
    }
    public void deleteReply(Long id){
        replyRepo.deleteById(id);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Pm_commentService {
    @Autowired
    private Pm_commentRepo commentRepo;

    public Pm_comment addComment(Pm_comment comment){
        return commentRepo.save(comment);
    }
    public List<Pm_comment> findAllComments(){
        return commentRepo.findAll();
    }
    public Pm_comment findCommentById(Long id){
        return commentRepo.findCommentById(id).orElseThrow(()-> new DataNotFoundException("Comment " + id +"was not found"));
    }
    //    public List<Comment> findCommentByUserId(String user_id){
//
//        return commentRepo.findByUserId(user_id);
//    }
    public Pm_comment updateComment(Pm_comment pmcomment){

        return commentRepo.save(pmcomment);
    }
    public void deleteComment(Long id){
        commentRepo.deleteById(id);
    }
}

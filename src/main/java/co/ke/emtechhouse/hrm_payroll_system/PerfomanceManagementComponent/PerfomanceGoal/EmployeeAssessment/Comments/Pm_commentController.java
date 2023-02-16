package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/goal/review/comments")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_commentController {
    @Autowired
    private Pm_commentRepo pmcommentRepo;
    @Autowired
    private Pm_commentService pmcommentService;

    @PostMapping("/add")
    public ResponseEntity<Pm_comment> addComment(@RequestBody Pm_comment comment){
        Pm_comment newComment = pmcommentService.addComment(comment);
        return  new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_comment>> getAllComments () {
        List<Pm_comment> comments = pmcommentService.findAllComments();
        return  new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_comment> getCommentById (@PathVariable("id") Long id){
        Pm_comment comment = pmcommentService.findCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    //    @PutMapping("/update/{id}")
//    public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment comment){
//        Optional<Comment> commentData = commentRepo.findCommentById(id);
//
//        if (commentData.isPresent()) {
//            Comment _comment = commentData.get();
//            _comment.setCommentName(comment.getCommentName());
//            _comment.setHeadOfComment(comment.getHeadOfComment());
//            _comment.setDirectorOfComment(comment.getDirectorOfComment());
//            _comment.setCommentMail(comment.getCommentMail());
//            _comment.setStatus(comment.getStatus());
//            _comment.setDeleted(comment.getDeleted());
//            return new ResponseEntity<>(commentRepo.save(_comment), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pmcommentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
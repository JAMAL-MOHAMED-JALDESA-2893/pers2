package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Comments.Replies;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/goal/review/comment/replies")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_replyController {
    @Autowired
    private Pm_replyRepo pmreplyRepo;
    @Autowired
    private Pm_replyService pmreplyService;

    @PostMapping("/add")
    public ResponseEntity<Pm_reply> addReply(@RequestBody Pm_reply reply){
        Pm_reply newPmreply = pmreplyService.addReply(reply);
        return  new ResponseEntity<>(newPmreply, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_reply>> getAllReplys () {
        List<Pm_reply> replys = pmreplyService.findAllReplys();
        return  new ResponseEntity<>(replys, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_reply> getReplyById (@PathVariable("id") Long id){
        Pm_reply reply = pmreplyService.findReplyById(id);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }
    //    @PutMapping("/update/{id}")
//    public ResponseEntity<Reply> updateReply(@PathVariable("id") long id, @RequestBody Reply reply){
//        Optional<Reply> replyData = replyRepo.findReplyById(id);
//
//        if (replyData.isPresent()) {
//            Reply _reply = replyData.get();
//            _reply.setReplyName(reply.getReplyName());
//            _reply.setHeadOfReply(reply.getHeadOfReply());
//            _reply.setDirectorOfReply(reply.getDirectorOfReply());
//            _reply.setReplyMail(reply.getReplyMail());
//            _reply.setStatus(reply.getStatus());
//            _reply.setDeleted(reply.getDeleted());
//            return new ResponseEntity<>(replyRepo.save(_reply), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pmreplyService.deleteReply(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
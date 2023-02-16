package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/leave/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;
    @Autowired
    private  LeaveTypeRepo leaveTypeRepo;


    @PostMapping("/add")
    public ResponseEntity<?> addLeaveType(@RequestBody LeaveType leaveType) {
        LeaveType newLeaveType = leaveTypeService.addTypeLeave(leaveType);
       Double deductionPercentage = leaveType.getDeductionPercentage();
       if (deductionPercentage > 100 ){
           return ResponseEntity.badRequest().body(new MessageResponse("Percentage can not be greater than 100%!"));
       }else if (deductionPercentage < 0){
           return ResponseEntity.badRequest().body(new MessageResponse("Percentage can not less than 0!"));
       }
       else{
            return new ResponseEntity<>(newLeaveType, HttpStatus.CREATED);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<LeaveType>> getAllLeavesType () {
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveType();
        return  new ResponseEntity<>(leaveTypes, HttpStatus.OK);
    }
    @GetMapping("/all/enabled/leave/types")
    public ResponseEntity<List<LeaveType>> findAllActiveLeaveTypes() {
        List<LeaveType> leaveTypes = leaveTypeRepo.findAllActiveLeaveTypes();
        return  new ResponseEntity<>(leaveTypes, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<LeaveType> getLeaveTypeById (@PathVariable("id") Long id){
        LeaveType leaveType = leaveTypeService.findLeaveTypeById(id);
        return new ResponseEntity<>(leaveType, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveType> updateLeaveType(@PathVariable("id") long id, @RequestBody LeaveType leaveType){
        Optional<LeaveType> leaveTypeData = leaveTypeRepo.findLeaveTypeById(id);
        if (leaveTypeData.isPresent()) {
            LeaveType _leave = leaveTypeData.get();
            _leave.setLeaveType(leaveType.getLeaveType());
            _leave.setDuration(leaveType.getDuration());
            _leave.setDeductionPercentage(leaveType.getDeductionPercentage());
            _leave.setStatus(leaveType.getStatus());
            _leave.setUpdated_at(LocalDateTime.now());
            _leave.setDeleted(leaveType.getDeleted());
            return new ResponseEntity<>(leaveTypeRepo.save(_leave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/on/enabled/{id}")
    public ResponseEntity<LeaveType> updateOnEnabledLeaveType(@PathVariable("id") long id){
        Optional<LeaveType> leaveTypeData = leaveTypeRepo.findLeaveTypeById(id);
        if (leaveTypeData.isPresent()) {
            LeaveType _leave = leaveTypeData.get();
            _leave.setIs_enabled(true);
            _leave.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(leaveTypeRepo.save(_leave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/on/disabled/{id}")
    public ResponseEntity<LeaveType> updateOnDisabledLeaveType(@PathVariable("id") long id){
        Optional<LeaveType> leaveTypeData = leaveTypeRepo.findById(id);
        if (leaveTypeData.isPresent()) {
            LeaveType _leave = leaveTypeData.get();
            _leave.setIs_enabled(false);
            _leave.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(leaveTypeRepo.save(_leave), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<LeaveType> deleteLeaveType(@PathVariable("id") Long id){
        leaveTypeRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}



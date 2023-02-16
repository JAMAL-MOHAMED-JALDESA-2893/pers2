package co.ke.emtechhouse.hrm_payroll_system.LeaveComponent.LeaveType;

import co.ke.emtechhouse.hrm_payroll_system._exception.LeaveTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveTypeService {
    @Autowired
    private final LeaveTypeRepo leaveTypeRepo;

    public LeaveTypeService(LeaveTypeRepo leaveTypeRepo) {
        this.leaveTypeRepo = leaveTypeRepo;
    }
    public LeaveType addTypeLeave(LeaveType leaveType){
        leaveType.setCreated_at(LocalDateTime.now());
        return leaveTypeRepo.save(leaveType);
    }
    public List<LeaveType> findAllLeaveType(){
        return leaveTypeRepo.findAll();
    }
    public LeaveType findLeaveTypeById(Long id){
        return leaveTypeRepo.findLeaveTypeById(id).orElseThrow(()-> new LeaveTypeNotFoundException("Leave " + id +"was not found"));
    }
    public LeaveType updateLeaveType(LeaveType leaveType){
        return leaveTypeRepo.save(leaveType);
    }
    public void deleteLeaveType(Long id){
        leaveTypeRepo.deleteLeaveTypeById(id);
    }
//}
}
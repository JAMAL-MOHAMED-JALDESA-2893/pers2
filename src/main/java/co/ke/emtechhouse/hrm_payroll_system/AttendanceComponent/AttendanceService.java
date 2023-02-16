package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class AttendanceService {
    @Autowired
    private AttendanceRepo attendanceRepo;

    public Attendance addAttendance(Attendance attendance){
        try {
            return attendanceRepo.save(attendance);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Attendance> findAllAttendances(){
        try {
            return attendanceRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Attendance findAttendanceById(Long id){
        try {
            return attendanceRepo.findAttendanceById(id).orElseThrow(()-> new DataNotFoundException("Attendance " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public Attendance updateAttendance(Attendance attendance){
        try {
            return attendanceRepo.save(attendance);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteAttendance(Long id){
        try {
            attendanceRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }
}

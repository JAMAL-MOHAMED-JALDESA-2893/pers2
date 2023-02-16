package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class AttendanceconfigService {
    @Autowired
    private AttendanceconfigRepo attendanceconfigRepo;
    public Attendanceconfig addAttendanceconfig(Attendanceconfig attendanceconfig){
        try {
            return attendanceconfigRepo.save(attendanceconfig);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Attendanceconfig> findAllAttendanceconfigs(){
        try {
            return attendanceconfigRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Attendanceconfig findAttendanceconfigById(Long id){
        try {
            return attendanceconfigRepo.findAttendanceconfigById(id).orElseThrow(()-> new DataNotFoundException("Attendanceconfig " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Attendanceconfig updateAttendanceconfig(Attendanceconfig attendanceconfig){
        try {
            return attendanceconfigRepo.save(attendanceconfig);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteAttendanceconfig(Long id){
        try {
            attendanceconfigRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }
}


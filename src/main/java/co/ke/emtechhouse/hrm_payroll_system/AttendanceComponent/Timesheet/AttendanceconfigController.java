package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/v1/attendance/config")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AttendanceconfigController {
    @Autowired
    private AttendanceconfigService attendanceconfigService;
    @Autowired
    private AttendanceconfigRepo attendanceconfigRepo;
    @PostMapping("/add")
    public ResponseEntity<?> addAttendanceconfig(@RequestBody Attendanceconfig attendanceconfig){
        try {
            List<Attendanceconfig> allConfig = attendanceconfigRepo.findAll();
            System.out.println(allConfig.size());
            if (allConfig.size() ==7  ){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: You already have Max Week days, you may need to update"));
            }else {
                Attendanceconfig newAttendanceconfig = attendanceconfigService.addAttendanceconfig(attendanceconfig);
                return  new ResponseEntity<>(newAttendanceconfig, HttpStatus.CREATED);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Attendanceconfig>> getAllAttendanceconfigs () {
        try {
            List<Attendanceconfig> attendanceconfigs = attendanceconfigService.findAllAttendanceconfigs();
            return  new ResponseEntity<>(attendanceconfigs, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Attendanceconfig> getAttendanceconfigById (@PathVariable("id") Long id){
        try {
            Attendanceconfig attendanceconfig = attendanceconfigService.findAttendanceconfigById(id);
            return new ResponseEntity<>(attendanceconfig, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Attendanceconfig> updateAttendanceconfig(@PathVariable("id") long id, @RequestBody Attendanceconfig attendanceconfig){
        try {
            Optional<Attendanceconfig> attendanceconfigData = attendanceconfigRepo.findAttendanceconfigById(id);
            if (attendanceconfigData.isPresent()) {
                Attendanceconfig _attendanceconfig = attendanceconfigData.get();
                _attendanceconfig.setDay(attendanceconfig.getDay());
                _attendanceconfig.setOpen_hours(attendanceconfig.getOpen_hours());
                _attendanceconfig.setClosing_hours(attendanceconfig.getClosing_hours());
                _attendanceconfig.setIs_open(attendanceconfig.getIs_open());
                _attendanceconfig.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(attendanceconfigRepo.save(_attendanceconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    //    sign in by attendanceconfig id
    @PutMapping("/sign/in/by/attendanceconfig/{id}")
    public ResponseEntity<Attendanceconfig> signIn(@PathVariable("id") long id, @RequestBody Attendanceconfig attendanceconfig){
        try {
            Optional<Attendanceconfig> attendanceconfigData = attendanceconfigRepo.findAttendanceconfigById(id);
            if (attendanceconfigData.isPresent()) {
                Attendanceconfig _attendanceconfig = attendanceconfigData.get();
//        _attendanceconfig.setTime_in(LocalDateTime.now());
                return new ResponseEntity<>(attendanceconfigRepo.save(_attendanceconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    //    sign out by attendanceconfig id
    @PutMapping("/sign/out/by/attendanceconfig/{id}")
    public ResponseEntity<Attendanceconfig> signOut(@PathVariable("id") long id, @RequestBody Attendanceconfig attendanceconfig){
        try {
            Optional<Attendanceconfig> attendanceconfigData = attendanceconfigRepo.findAttendanceconfigById(id);
            if (attendanceconfigData.isPresent()) {
                Attendanceconfig _attendanceconfig = attendanceconfigData.get();
//        _attendanceconfig.setTime_out(LocalDateTime.now());
                return new ResponseEntity<>(attendanceconfigRepo.save(_attendanceconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    //    Approved by hr
    @PutMapping("/approve/by/hr/attendanceconfig/{id}")
    public ResponseEntity<Attendanceconfig> hrApprove(@PathVariable("id") long id, @RequestBody Attendanceconfig attendanceconfig){
        try {
            Optional<Attendanceconfig> attendanceconfigData = attendanceconfigRepo.findAttendanceconfigById(id);
            if (attendanceconfigData.isPresent()) {
                Attendanceconfig _attendanceconfig = attendanceconfigData.get();
                _attendanceconfig.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(attendanceconfigRepo.save(_attendanceconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    //    Approved by supervisor
    @PutMapping("/approve/by/supervisor/attendanceconfig/{id}")
    public ResponseEntity<Attendanceconfig> supervisorAttendanceconfig(@PathVariable("id") long id, @RequestBody Attendanceconfig attendanceconfig){
        try {
            Optional<Attendanceconfig> attendanceconfigData = attendanceconfigRepo.findAttendanceconfigById(id);
            if (attendanceconfigData.isPresent()) {
                Attendanceconfig _attendanceconfig = attendanceconfigData.get();
                _attendanceconfig.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(attendanceconfigRepo.save(_attendanceconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/permanent/delete/{id}")
    public ResponseEntity<Attendanceconfig> deleteEmployee(@PathVariable("id") Long id){
        try {
            attendanceconfigRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}


package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent;

import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.Attendanceconfig;
import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.AttendanceconfigRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/attendance")
@Slf4j
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AttendanceController {
    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceconfigRepo attendanceconfigRepo;
    @Autowired
    Autoattendanceregister autoattendanceregister;

    @PostMapping("/add")
    public ResponseEntity<Attendance> addAttendance(@RequestBody Attendance attendance){
        try {
            LocalDateTime currentDate= LocalDateTime.now();
            Month monthDate = currentDate.getMonth();
            String month = monthDate.toString();
            int year = currentDate.getYear();
            String day = currentDate.getDayOfWeek().toString();
            LocalTime time_in = OffsetDateTime.parse(currentDate.toString()).toLocalTime();
            attendance.setRegister_on(currentDate);
            attendance.setDay(day);
            attendance.setTime_in(time_in);

            System.out.println("current date" +currentDate);
            System.out.println("day" +day);
            System.out.println("time in" +time_in);

            Attendance newAttendance = attendanceService.addAttendance(attendance);
            return  new ResponseEntity<>(newAttendance, HttpStatus.CREATED);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendances () {
        try {
            List<Attendance> attendances = attendanceService.findAllAttendances();
            return  new ResponseEntity<>(attendances, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/detailed")
    public ResponseEntity<List<AttendanceRepo.AttendanceDetail>> findAttendanceDetail() {
        try {
            List<AttendanceRepo.AttendanceDetail> attendances = attendanceRepo.findAttendanceDetail();
            return  new ResponseEntity<>(attendances, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all/detailed/per/department")
    public ResponseEntity<List<AttendanceRepo.AttendanceDetail>> findAttendanceDetailPerDepartment(@RequestParam  Long department_id) {
        try {
            System.out.println(department_id);
            List<AttendanceRepo.AttendanceDetail> attendances = attendanceRepo.findAttendanceDetailPerDepartment(department_id);
            return  new ResponseEntity<>(attendances, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }



    @GetMapping("/find/{id}")
    public ResponseEntity<Attendance> getAttendanceById (@PathVariable("id") Long id){
        try {
            Attendance attendance = attendanceService.findAttendanceById(id);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/register")
    public  ResponseEntity<?> createRegister(){
        try {
            System.out.println("got called!");
            autoattendanceregister.openRegister();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/employee/attendance")
    public ResponseEntity<?> findEmployeeAttendance(@RequestParam Long employee_id){
        try {
            LocalDate time_in = LocalDate.now();
            AttendanceRepo.AttendanceDetail attendance = attendanceRepo.findEmployeeAttendance(employee_id, time_in );
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/todays/attendance")
    public ResponseEntity<?> findAttendance(){
        try {
            LocalDate registar_date = LocalDate.now();
            List<AttendanceRepo.AttendanceDetail> attendance = attendanceRepo.findTodaysAttendance(registar_date);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/all/attendance/filtered/by/")
    public ResponseEntity<?> findAttendance(@RequestParam String attendance_status){
        try {
            LocalDate registar_date =  LocalDate.now();
            List<AttendanceRepo.AttendanceDetail> attendance = attendanceRepo.filterByAttendanceStatus(attendance_status, registar_date);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") long id, @RequestBody Attendance attendance){
        try {
            Optional<Attendance> attendanceData = attendanceRepo.findAttendanceById(id);
            if (attendanceData.isPresent()) {
                Attendance _attendance = attendanceData.get();
                _attendance.setTime_in(attendance.getTime_in());
                _attendance.setTime_out(attendance.getTime_out());
                return new ResponseEntity<>(attendanceRepo.save(_attendance), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
//    sign in by attendance id
@PutMapping("/sign/in/by/attendance/{id}")
public ResponseEntity<Attendance> signIn(@PathVariable("id") long id){
    try {
        Optional<Attendance> attendanceData = attendanceRepo.findAttendanceById(id);
        if (attendanceData.isPresent()) {
            Attendance _attendance = attendanceData.get();
            LocalDateTime currentDate = LocalDateTime.now();
            String day = currentDate.getDayOfWeek().toString();
            LocalTime time_in = currentDate.toLocalTime();
//        LocalTime time_in = OffsetDateTime.parse(currentDate.toString()).toLocalTime();
            String str_time_in = time_in.toString();
            Optional<Attendanceconfig> _dayconfig = attendanceconfigRepo.findByDay(day);
            if (_dayconfig.isPresent()){
                if (time_in.isAfter(_dayconfig.get().getOpen_hours())){
                    long timeDifference = ChronoUnit.MINUTES.between(_dayconfig.get().getOpen_hours(),time_in);
                    if (timeDifference>60){
                        long hrDifference = ChronoUnit.HOURS.between(_dayconfig.get().getOpen_hours(),time_in);
                        _attendance.setTime_in(time_in);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_in.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_in_status("Too Late by " + hrDifference + " Hrs" );
                    }else{
                        _attendance.setTime_in(time_in);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_in.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_in_status("Late by " + timeDifference + " Min" );
                    }
                }else if (time_in.isBefore(_dayconfig.get().getOpen_hours())){
                    long timeDifference = ChronoUnit.MINUTES.between(time_in,_dayconfig.get().getOpen_hours());
                    if (timeDifference>60){
                        long hrDifference = ChronoUnit.HOURS.between(time_in,_dayconfig.get().getOpen_hours());
                        _attendance.setTime_in(time_in);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_in.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_in_status("Too Early by " + hrDifference + " Hrs" );
                    }else{
                        _attendance.setTime_in(time_in);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_in.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_in_status("Early by " + timeDifference + " Min" );
                    }
                }else {
                    _attendance.setTime_in(time_in);
                    _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_in.getMinute()));
                    _attendance.setAttendance_status("Present");
                    _attendance.setTime_in_status("Actual Time");
                }
            }
            return new ResponseEntity<>(attendanceRepo.save(_attendance), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }catch (Exception e) {
        log.info("Error {} "+e);
        return null;
    }
}
//    sign out by attendance id
@PutMapping("/sign/out/by/attendance/{id}")
public ResponseEntity<Attendance> signOut(@PathVariable("id") long id){
    try {
        Optional<Attendance> attendanceData = attendanceRepo.findAttendanceById(id);
        if (attendanceData.isPresent()) {
            Attendance _attendance = attendanceData.get();
            LocalDateTime currentDate = LocalDateTime.now();
            String day = currentDate.getDayOfWeek().toString();
            LocalTime time_out = currentDate.toLocalTime();
//        LocalTime time_in = OffsetDateTime.parse(currentDate.toString()).toLocalTime();
            String str_time_in = time_out.toString();
            Optional<Attendanceconfig> _dayconfig = attendanceconfigRepo.findByDay(day);
            if (_dayconfig.isPresent()){
                if (time_out.isAfter(_dayconfig.get().getClosing_hours())){
                    long timeDifference = ChronoUnit.MINUTES.between(_dayconfig.get().getClosing_hours(),time_out);
                    if (timeDifference>60){
                        long hrDifference = ChronoUnit.HOURS.between(_dayconfig.get().getClosing_hours(),time_out);
                        _attendance.setTime_out(time_out);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_out.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_out_status("Long Extended by " + hrDifference + " Hrs" );
                    }else{
                        _attendance.setTime_out(time_out);
                        _attendance.setIn_late_by(_dayconfig.get().getClosing_hours().minusMinutes(time_out.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_out_status("Extended by " + timeDifference + " Min" );
                    }
                }else if (time_out.isBefore(_dayconfig.get().getClosing_hours())){
                    long timeDifference = ChronoUnit.MINUTES.between(time_out,_dayconfig.get().getClosing_hours());
                    if (timeDifference>60){
                        long hrDifference = ChronoUnit.HOURS.between(time_out,_dayconfig.get().getClosing_hours());
                        _attendance.setTime_out(time_out);
                        _attendance.setIn_late_by(_dayconfig.get().getClosing_hours().minusMinutes(time_out.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_out_status("Too Early by " + hrDifference + " Hrs" );
                    }else{
                        _attendance.setTime_out(time_out);
                        _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_out.getMinute()));
                        _attendance.setAttendance_status("Present");
                        _attendance.setTime_out_status("Early by " + timeDifference + " Min" );
                    }
                }else {
                    _attendance.setTime_out(time_out);
                    _attendance.setIn_late_by(_dayconfig.get().getOpen_hours().minusMinutes(time_out.getMinute()));
                    _attendance.setAttendance_status("Present");
                    _attendance.setTime_out_status("Actual Time");
                }
            }
            return new ResponseEntity<>(attendanceRepo.save(_attendance), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }catch (Exception e) {
        log.info("Error {} "+e);
        return null;
    }
}

//    Approved by hr
@PutMapping("/approve/by/hr/attendance/{id}")
public ResponseEntity<Attendance> hrApprove(@PathVariable("id") long id, @RequestBody Attendance attendance){
    try {
        Optional<Attendance> attendanceData = attendanceRepo.findAttendanceById(id);
        if (attendanceData.isPresent()) {
            Attendance _attendance = attendanceData.get();
            _attendance.setIs_hr_approved(true);
            _attendance.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(attendanceRepo.save(_attendance), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }catch (Exception e) {
        log.info("Error {} "+e);
        return null;
    }
}
//    Approved by supervisor
@PutMapping("/approve/by/supervisor/attendance/{id}")
public ResponseEntity<Attendance> supervisorAttendance(@PathVariable("id") long id, @RequestBody Attendance attendance){
    try {
        Optional<Attendance> attendanceData = attendanceRepo.findAttendanceById(id);
        if (attendanceData.isPresent()) {
            Attendance _attendance = attendanceData.get();
            _attendance.setIs_supervisor_approved(true);
            _attendance.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(attendanceRepo.save(_attendance), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }catch (Exception e) {
        log.info("Error {} "+e);
        return null;
    }
}
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Attendance> deleteEmployee(@PathVariable("id") Long id){
        try {
            attendanceRepo.deleteAttendanceById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

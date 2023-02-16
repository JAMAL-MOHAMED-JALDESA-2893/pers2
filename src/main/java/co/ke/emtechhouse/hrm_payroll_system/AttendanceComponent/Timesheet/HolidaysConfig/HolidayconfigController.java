package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig;

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
@RequestMapping("/api/v1/attendance/config/holiday")
@Slf4j
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class HolidayconfigController {
    @Autowired
    private HolidayconfigRepo holidayconfigRepo;
    @Autowired
    private HolidayconfigService holidayconfigService;

    @PostMapping("/add")
    public ResponseEntity<Holidayconfig> addHolidayconfig(@RequestBody Holidayconfig holidayconfig){
        try {
            LocalDateTime long_date = holidayconfig.getDetailed_long_date();
            holidayconfig.setDay_of_month(long_date.getDayOfMonth());
            holidayconfig.setDay_of_week(long_date.getDayOfWeek().toString());
            holidayconfig.setYear(long_date.getYear());
            holidayconfig.setMonth(long_date.getMonth().toString());
            holidayconfig.setHoliday_date(long_date.toLocalDate());
            Holidayconfig newHolidayconfig = holidayconfigService.addHolidayconfig(holidayconfig);
            return  new ResponseEntity<>(newHolidayconfig, HttpStatus.CREATED);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Holidayconfig>> getAllHolidayconfigs () {
        try {
            List<Holidayconfig> holidayconfigs = holidayconfigService.findAllHolidayconfigs();
            return  new ResponseEntity<>(holidayconfigs, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Holidayconfig> getHolidayconfigById (@PathVariable("id") Long id){
        try {
            Holidayconfig holidayconfig = holidayconfigService.findHolidayconfigById(id);
            return new ResponseEntity<>(holidayconfig, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Holidayconfig> updateHolidayconfig(@PathVariable("id") long id, @RequestBody Holidayconfig holidayconfig){
        try {
            Optional<Holidayconfig> holidayconfigData = holidayconfigRepo.findHolidayconfigById(id);
            if (holidayconfigData.isPresent()) {
                Holidayconfig _holidayconfig = holidayconfigData.get();
                LocalDateTime long_date = holidayconfig.getDetailed_long_date();
                _holidayconfig.setDay_of_month(long_date.getDayOfMonth());
                _holidayconfig.setDay_of_week(long_date.getDayOfWeek().toString());
                _holidayconfig.setYear(long_date.getYear());
                _holidayconfig.setMonth(long_date.getMonth().toString());
                _holidayconfig.setHoliday_date(long_date.toLocalDate());
                _holidayconfig.setUpdated_at(LocalDateTime.now());
                _holidayconfig.setIs_active(holidayconfig.getIs_active());
                _holidayconfig.setUpdated_at(LocalDateTime.now());
                return new ResponseEntity<>(holidayconfigRepo.save(_holidayconfig), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Holidayconfig> deleteHolidayconfig(@PathVariable("id") Long id){
        try {
            holidayconfigRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}
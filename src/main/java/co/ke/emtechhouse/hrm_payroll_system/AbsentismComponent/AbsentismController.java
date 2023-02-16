package co.ke.emtechhouse.hrm_payroll_system.AbsentismComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/absentism/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AbsentismController {
    @Autowired
    private AbsentismRepo absentismRepo;
    @Autowired
    private AbsentismService absentismService;

    @PostMapping("/add")
    public ResponseEntity<Absentism> addAbsentism(@RequestBody Absentism absentism){
        LocalDate currentDate= LocalDate.now();
        // Get day from date
        int day = currentDate.getDayOfMonth();
        DayOfWeek dayweek = currentDate.getDayOfWeek();
        String dayofweekstr = dayweek.toString();
        // Get month from date
        Month month = currentDate.getMonth();
        String getMonth = month.toString();
        int year = currentDate.getYear();

        absentism.setDay(dayofweekstr);
//        absentism.setWeek();
        absentism.setMonth(getMonth);
        absentism.setYear(year);

        Absentism newAbsentism = absentismService.addAbsentism(absentism);
        return  new ResponseEntity<>(newAbsentism, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Absentism>> getAllAbsentisms () {
        List<Absentism> absentisms = absentismService.findAllAbsentisms();
        return  new ResponseEntity<>(absentisms, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Absentism> getAbsentismById (@PathVariable("id") Long id){
        Absentism absentism = absentismService.findAbsentismById(id);
        return new ResponseEntity<>(absentism, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Absentism> updateAbsentism(@PathVariable("id") long id, @RequestBody Absentism absentism){
        Optional<Absentism> absentismData = absentismRepo.findAbsentismById(id);
        if (absentismData.isPresent()) {
            Absentism _absentism = absentismData.get();
            _absentism.setOccupation(absentism.getOccupation());
            _absentism.setDay(absentism.getDay());
            _absentism.setMonth(absentism.getMonth());
            _absentism.setYear(absentism.getYear());
            _absentism.setAbsent_from(absentism.getAbsent_from());
            _absentism.setAbsent_to(absentism.getAbsent_to());
            _absentism.setIs_still_absent(absentism.getIs_still_absent());
            _absentism.setTotal_absent_accrued_hours(absentism.getTotal_absent_accrued_hours());
            _absentism.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(absentismRepo.save(_absentism), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/close/{id}")
    public ResponseEntity<Absentism> deleteAbsentism(@PathVariable("id") Long id, @RequestBody Absentism absentism){
        Optional<Absentism> absentismData = absentismRepo.findAbsentismById(id);
        LocalDateTime absentFrom = absentismData.get().getAbsent_from();
        LocalDateTime current_date = LocalDateTime.now();
        double hours_difference = ChronoUnit.HOURS.between(absentFrom, current_date);
        if (absentismData.isPresent()) {
            Absentism _absentism = absentismData.get();
            _absentism.setTotal_absent_accrued_hours(hours_difference);
            _absentism.setIs_still_absent(false);
            _absentism.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(absentismRepo.save(_absentism), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        absentismRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class HolidayconfigService {
    @Autowired
    private HolidayconfigRepo holidayconfigRepo;

    public Holidayconfig addHolidayconfig(Holidayconfig holidayconfig){
        try {
            return HolidayconfigService.this.holidayconfigRepo.save(holidayconfig);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Holidayconfig> findAllHolidayconfigs(){
        try {
            return holidayconfigRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }

    }
    public Holidayconfig findHolidayconfigById(Long id){
        try {
            return holidayconfigRepo.findHolidayconfigById(id).orElseThrow(()-> new DataNotFoundException("Holidayconfig " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Holidayconfig updateHolidayconfig(Holidayconfig holidayconfig){
        try {
            return HolidayconfigService.this.holidayconfigRepo.save(holidayconfig);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteHolidayconfig(Long id){
        try {
            holidayconfigRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }
}
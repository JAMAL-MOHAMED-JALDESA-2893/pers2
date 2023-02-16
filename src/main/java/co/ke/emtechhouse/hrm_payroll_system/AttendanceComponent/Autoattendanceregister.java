package co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent;

import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.Attendanceconfig;
import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.AttendanceconfigRepo;
import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig.Holidayconfig;
import co.ke.emtechhouse.hrm_payroll_system.AttendanceComponent.Timesheet.HolidaysConfig.HolidayconfigRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Autoattendanceregister {
    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HolidayconfigRepo holidayconfigRepo;
    @Autowired
    private AttendanceconfigRepo attendanceconfigRepo;

    @Scheduled(cron="${cron.exp_open_work}")
    public void openRegister(){
        log.info("***************Day Configuration Found *****************");
        try {
            //        check if register has been generated for that date, it should skip
            ZoneId tokyoZoneId = ZoneId.of("Africa/Nairobi");
            LocalDate registar_date = LocalDate.now(tokyoZoneId);
            List<Attendance> _attendance = attendanceRepo.findEmployeeAttendance(registar_date);
            if (_attendance.size()>0){
                log.info("Already Generated!");
            }else {
                List<Employee> employees = employeeRepository.findActiveEmployees();
                for (int e = 0; e < employees.size(); e++) {
                    LocalDateTime currentDate = LocalDateTime.now();
                    String month = currentDate.getMonth().toString();
                    int year = currentDate.getYear();
                    String day = currentDate.getDayOfWeek().toString();

//        check if is holiday
//        Check day and get opening status
                    Optional<Attendanceconfig> currentDay = attendanceconfigRepo.findByDay(day);
                    if (currentDay.isPresent()) {
                        log.info("***************Day Configuration Found *****************");
//                check if is open
                        if (currentDay.get().getIs_open()) {
//                    check if it is holiday
                            Optional<Holidayconfig> _holiday = holidayconfigRepo.findByDate(currentDate.toLocalDate());
                            if (_holiday.isPresent()) {
                                log.info("************Is Holiday ************");
                                Attendance newAttendance = new Attendance();
                                newAttendance.setRegister_on(currentDate);
                                newAttendance.setRegistar_date(currentDate.toLocalDate());
                                newAttendance.setDay(day);
                                newAttendance.setEmployee_id(employees.get(e).getId());
                                newAttendance.setDepartment_id(employees.get(e).getDepartmentId());
                                newAttendance.setIs_holiday(true);
                                newAttendance.setAttendance_status("Holiday");
                                newAttendance.setDay_identity(_holiday.get().getHoliday_name());
                                attendanceRepo.save(newAttendance);
                                log.info("*****************Register Updated for today!*****************");
                            } else {
                                log.info("***************Not Holiday ***********************");
                                Attendance newAttendance = new Attendance();
                                newAttendance.setRegister_on(currentDate);
                                newAttendance.setRegistar_date(currentDate.toLocalDate());
                                newAttendance.setDay(day);
                                newAttendance.setEmployee_id(employees.get(e).getId());
                                newAttendance.setDepartment_id(employees.get(e).getDepartmentId());
                                attendanceRepo.save(newAttendance);
                                log.info("*****************Register Updated for today!*****************");
                            }
                        } else {
                            Attendance newAttendance = new Attendance();
                            newAttendance.setRegister_on(currentDate);
                            newAttendance.setRegistar_date(currentDate.toLocalDate());
                            newAttendance.setDay(day);
                            newAttendance.setEmployee_id(employees.get(e).getId());
                            newAttendance.setDepartment_id(employees.get(e).getDepartmentId());
                            newAttendance.setIs_weekend(true);
                            newAttendance.setAttendance_status("Weekend");
                            attendanceRepo.save(newAttendance);
                            log.info("Workday closed | Weekend | {}",day);
                            log.info("*****************Register Updated for today!*****************");
                        }

                    } else {
                        log.info("**********No Day Configurations******");
                        Attendance newAttendance = new Attendance();
                        newAttendance.setRegister_on(currentDate);
                        newAttendance.setRegistar_date(currentDate.toLocalDate());
                        newAttendance.setDay(day);
                        newAttendance.setEmployee_id(employees.get(e).getId());
                        newAttendance.setDepartment_id(employees.get(e).getDepartmentId());
                        attendanceRepo.save(newAttendance);
                        log.info("*****************Register Updated for today!*****************");
                    }
                }
            }
        } catch (Exception e) {
            log.info("Attendance Error {} "+e);
        }

    }
//    public void closeRegister(){
//        List<EmployeeEntity> employees = employeeRepository.findActiveEmployees();
//        for (int i =0; i<employees.size(); i++){
//            Boolean is_salary_closed = false;
//            EmployeeEntity _employeeEntity = employees.get(i);
//            _employeeEntity.setIs_salary_closed(is_salary_closed);
//            _employeeEntity.setHas_dummy_salary(false);
//            employeeRepository.save(_employeeEntity);
//
//            LocalDate currentDate= LocalDate.now();
//            String month = currentDate.getMonth().toString();
//            int year = currentDate.getYear();
//            String day = currentDate.getDayOfWeek().toString();
//            String time_in = OffsetDateTime.parse(LocalDate.now().toString()).toLocalTime().toString();
//
//            attendance.setYear(year);
//            attendance.setMonth(month);
//            attendance.setDay(day);
//            attendance.setTime_in(time_in);
//            Attendance newAttendance = attendanceService.addAttendance(attendance);
//
//                log.info("*****************All active Employee Salary Opened!*****************");
//        }
//    }
}

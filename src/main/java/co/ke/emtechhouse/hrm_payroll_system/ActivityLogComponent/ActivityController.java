package co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/activity/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class ActivityController {
    @Autowired
    private ActivityRepo activityRepo;
    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity){
        Activity newActivity = activityService.addActivity(activity);
        return  new ResponseEntity<>(newActivity, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivitys () {
        List<Activity> activitys = activityService.findAllActivitys();
        return  new ResponseEntity<>(activitys, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Activity> getActivityById (@PathVariable("id") Long id){
        Activity activity = activityService.findActivityById(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }
    @GetMapping("/find/activities")
    public ResponseEntity<List<Activity>> getallactivities (@RequestParam String activity_category, @RequestParam String month, @RequestParam String year){
        System.out.println("Got Called here");
        List<Activity> activity = activityRepo.findActivity(activity_category,month,year);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }




//    @PutMapping("/update/{id}")
//    public ResponseEntity<Activity> updateActivity(@PathVariable("id") long id, @RequestBody Activity activity){
//        Optional<Activity> activityData = activityRepo.findById(id);
//        if (activityData.isPresent()) {
//            Activity _activity = activityData.get();
//            _activity.setActivityName(activity.getActivityName());
//            _activity.setDirectorOfActivity(activity.getDirectorOfActivity());
//            _activity.setActivityMail(activity.getActivityMail());
//            _activity.setStatus(activity.getStatus());
//            _activity.setDeleted(activity.getDeleted());
//            return new ResponseEntity<>(activityRepo.save(_activity), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
////    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Activity> deleteActivity(@PathVariable("id") Long id){
////        Check if activity has got employees
//        List<EmployeeEntity> employee = employeeRepository.findByActivity(id);
//        if(employee.isEmpty() ){
////            has no data, remove
//            activityService.deleteActivity(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }else{
////        has data dont remove
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//    @DeleteMapping("/permanent></delete/{id}")
//    public ResponseEntity<EmployeeEntity> deleteEmployee(@PathVariable("id") Long id){
//        employeeService.deleteEmployee(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
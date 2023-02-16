package co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityService {
    @Autowired
    private ActivityRepo activityRepo;

    public Activity addActivity(Activity activity){
        return activityRepo.save(activity);
    }
    public List<Activity> findAllActivitys(){
        return activityRepo.findAll();
    }
    public Activity findActivityById(Long id){
        return activityRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Activity " + id +"was not found"));
    }
    public Activity updateActivity(Activity activity){
        return activityRepo.save(activity);
    }
    public void deleteActivity(Long id){
        activityRepo.deleteById(id);
    }
}

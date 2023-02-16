package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal;

import co.ke.emtechhouse.hrm_payroll_system.MailComponent.NotificationsEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class GoalScheduleEvent {
    @Autowired
    private Pm_goalService pm_goalService;
    @Autowired
    private Pm_goalRepo pm_goalRepo;
    @Autowired
    private NotificationsEmail notificationsEmail;
    //    TODO:Schedule to check if any review is available. If it is available, it will update the button and notify the admin and employee that the review is schedule to happen in delayed days.

//    @Scheduled(cron="${cron.expression}", zone="Europe/Paris")
    public void checkNextSchedule() throws MessagingException {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Pm_goal> goals = pm_goalService.findAllPm_goals();
        for (int i =0; i<goals.size(); i++){
            Pm_goal pm_goal = goals.get(i);
            LocalDateTime nextReviewDate = pm_goal.getNext_review_date();
            long days_difference = ChronoUnit.DAYS.between(currentDate, nextReviewDate);
            System.out.println(days_difference);
//                if difference = 5 then notify for 5days to.
            if (days_difference<6 && days_difference>0){
                System.out.println("*****************Email has been sent to notify*****************");
            }else if (days_difference==0){
//                    Sent email
                pm_goal.setReview_enabled(true);
                pm_goalRepo.save(pm_goal);
                System.out.println("*****************Button Enabled & Email has been sent*****************");
            }
        }
//        String emailTo = "coullence@gmail.com";
//        String emailSubject = "Notification Mail";
//        String emailDesignationName = "Collins";
//        String emailMessage = "Find enclosed email and process the documents for the company";
//        notificationsEmail.sendNotificationMail(
//                emailTo,
//                emailSubject,
//                emailDesignationName,
//                emailMessage);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment;

import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.Pm_goalRepo;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.Pm_goal;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.Pm_goalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/perfomance/management/goal/reviews")
@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_reviewController {
    @Autowired
    Pm_reviewRepo pm_reviewRepo;
    @Autowired
    Pm_reviewService pm_review_service;
    @Autowired
    private Pm_goalService pm_goalService;
    @Autowired
    private Pm_goalRepo pm_goalRepo;

    @PostMapping("/add")
    public ResponseEntity<Pm_review> addPm_review(@RequestBody Pm_review pm_review){
//        get goal
        Long goal_id = pm_review.getPm_goal_id();
        Optional<Pm_goal> goal = pm_goalRepo.findById(goal_id);

        if (goal.isPresent()){
            Long parameter_id = goal.get().getParameter_id();
            String parameter_name = goal.get().getParameter_name();
            LocalDate currentDate= LocalDate.now();
            int day = currentDate.getDayOfMonth();
            Month month = currentDate.getMonth();
            String getMonth = month.toString();
            int year = currentDate.getYear();
            pm_review.setParameter_id(parameter_id);
            pm_review.setParameter_name(parameter_name);
            pm_review.setGoal(goal.get().getGoal());
            pm_review.setYear(year);
            pm_review.setMonth(getMonth);
            pm_review.setIs_reviewed(true);
            Pm_review newPm_review = pm_review_service.addPm_review(pm_review);
//        TODO:RESET THE REVIEW DATE ACCORDING TO REVIEW CYCLE
//        Find the goal by id
            Optional<Pm_goal> pm_goalData = pm_goalRepo.findPm_goalById(pm_review.getPm_goal_id());
            if (pm_goalData.isPresent()) {
//        update the 1. review date and disable the button
                Pm_goal _pm_goal = pm_goalData.get();
                LocalDateTime nowDate = LocalDateTime.now();
                LocalDateTime newDate = nowDate.plusMonths(_pm_goal.getNext_month_review_delay());
                _pm_goal.setNext_review_date(newDate);
                _pm_goal.setReview_enabled(false);
                pm_goalRepo.save(_pm_goal);
            }
            return  new ResponseEntity<>(newPm_review, HttpStatus.CREATED);

        }else {
            return new ResponseEntity<>(pm_review, HttpStatus.NOT_FOUND);
        }

    }
//    TODO: ON Enroll
    @PostMapping("/enroll/employee")
    public ResponseEntity<Pm_review> enrollEmployee(@RequestBody Pm_review pm_review){
        Pm_review newPm_review = pm_review_service.addPm_review(pm_review);
        return  new ResponseEntity<>(newPm_review, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Pm_review>> getAllPm_reviews () {
        List<Pm_review> pm_reviews = pm_review_service.findAllPm_reviews();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/rank/all/employees")
    public ResponseEntity<List<Pm_reviewRepo.Employeereview>> rankAllEmployees() {
        List<Pm_reviewRepo.Employeereview> pm_reviews = pm_reviewRepo.rankAllEmployees();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/best/employee")
    public ResponseEntity<Pm_reviewRepo.Employeereview> bestEmployees() {
        Pm_reviewRepo.Employeereview pm_reviews = pm_reviewRepo.bestEmployee();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/top/3/employees")
    public ResponseEntity<List<Pm_reviewRepo.Employeereview>> top3Employees() {
        List<Pm_reviewRepo.Employeereview> pm_reviews = pm_reviewRepo.top3Employees();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/last/3/employees")
    public ResponseEntity<List<Pm_reviewRepo.Employeereview>> last3Employees() {
        List<Pm_reviewRepo.Employeereview> pm_reviews = pm_reviewRepo.last3Employees();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/find/review/by/employee/{employee_id}")
    public ResponseEntity<List<Pm_reviewRepo.Employeereview>> findByEmployeeId(@PathVariable("employee_id") Long employee_id) {
        List<Pm_reviewRepo.Employeereview> pm_reviews = pm_reviewRepo.findByEmployeeId(employee_id);
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/find/review/yearly/by/employee/")
    public ResponseEntity<List<Pm_reviewRepo.employeeAverageReview>> findAvgPmReviewByEmployeeIdAndYear(@RequestParam  Long employee_id, @RequestParam  Integer year ) {
        List<Pm_reviewRepo.employeeAverageReview> pm_reviews = pm_reviewRepo.findAvgPmReviewByEmployeeIdAndYear(employee_id,year);
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }

    @GetMapping("/find/avg/review/by/employee/{employee_id}")
    public ResponseEntity<List<Pm_reviewRepo.employeeAverageReview>> findAvgPmReviewByEmployeeId(@PathVariable("employee_id") Long employee_id) {
        List<Pm_reviewRepo.employeeAverageReview> pm_reviews = pm_reviewRepo.findAvgPmReviewByEmployeeId(employee_id);
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/find/organization/avg/review/perfomance/per/year")
    public ResponseEntity<List<Pm_reviewRepo.employeeAverageReview>> findAvgMonthlyPerfomancePerYear(@RequestParam  Integer year) {
        List<Pm_reviewRepo.employeeAverageReview> pm_reviews = pm_reviewRepo.findAvgMonthlyPerfomancePerYear(year);
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/group/organization/review/per/year")
    public ResponseEntity<List<Pm_reviewRepo.employeeAverageReview>> groupPerfomanceByYear(){
        List<Pm_reviewRepo.employeeAverageReview> pm_reviews = pm_reviewRepo.groupPerfomanceByYear();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }





    @GetMapping("/count/all/goals")
    public ResponseEntity<List<Pm_reviewRepo.Employeereview>> countAllEmployees() {
        List<Pm_reviewRepo.Employeereview> pm_reviews = pm_reviewRepo.countAllGoals();
        return  new ResponseEntity<>(pm_reviews, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Pm_review> getPm_reviewById (@PathVariable("id") Long id){
        Pm_review pm_review = pm_review_service.findPm_reviewById(id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }

    @GetMapping("/find/average/by/employee/{employee_id}")
    public ResponseEntity<List<Pm_review>> findAverageReviewByEmployeeId(@PathVariable("employee_id") Long employee_id){
        List<Pm_review> pm_review = pm_reviewRepo.findAverageReviewByEmployeeId(employee_id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }
    @GetMapping("/find/sum/by/employee/{employee_id}")
    public ResponseEntity<Pm_reviewRepo.Employeereview> sumByEmployeeId(@PathVariable("employee_id") Long employee_id){
        Pm_reviewRepo.Employeereview pm_review = pm_reviewRepo.sumByEmployeeId(employee_id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }


    @GetMapping("/find/pm_review/by/parameter/and/employee/")
    public ResponseEntity<List<Pm_review>> findReviewByParameterandEmployeeId(@RequestParam Long parameter_id, @RequestParam Long employee_id){
        List<Pm_review> pm_review = pm_reviewRepo.findReviewByParameterandEmployeeId(parameter_id, employee_id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }



    @GetMapping("/find/by/employee/{employee_id}")
    public ResponseEntity<?> findReviewByEmployeeId(@PathVariable("employee_id") Long employee_id){
        List<Pm_review> pm_review = pm_review_service.findReviewByEmployeeId(employee_id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }

//    TODO: CHECK IF THE NEXT Review cycle has been reached.

    @GetMapping("/find/last/review/by/employeeid/and/goalid/")
    public ResponseEntity<Pm_review> findLastReviewByEmployeeGoalId( @RequestParam  Long employee_id, @RequestParam Long goal_id){
        Pm_review pm_review = pm_review_service.findLastReviewByEmployeeGoalId(employee_id,goal_id);
        return new ResponseEntity<>(pm_review, HttpStatus.OK);
    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<Pm_review> updatePm_review(@PathVariable("id") long id, @RequestBody Pm_review pm_review){
//        Optional<Pm_review> pm_reviewData = pm_reviewRepo.findPm_reviewById(id);
//
//        if (pm_reviewData.isPresent()) {
//            Pm_review _pm_review = pm_reviewData.get();
//            _pm_review.setPm_reviewName(pm_review.getPm_reviewName());
//            _pm_review.setHeadOfPm_review(pm_review.getHeadOfPm_review());
//            _pm_review.setDirectorOfPm_review(pm_review.getDirectorOfPm_review());
//            _pm_review.setPm_reviewMail(pm_review.getPm_reviewMail());
//            _pm_review.setStatus(pm_review.getStatus());
//            _pm_review.setDeleted(pm_review.getDeleted());
//            return new ResponseEntity<>(pm_reviewRepo.save(_pm_review), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @DeleteMapping("/permanent></delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
        pm_review_service.deletePm_review(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
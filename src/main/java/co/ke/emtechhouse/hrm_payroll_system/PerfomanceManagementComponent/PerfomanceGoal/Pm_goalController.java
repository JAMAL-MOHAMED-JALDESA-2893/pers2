package co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Models.Pm_Parameters;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.Employee;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Pm_review;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.PerfomanceGoal.EmployeeAssessment.Pm_reviewRepo;
import co.ke.emtechhouse.hrm_payroll_system.PerfomanceManagementComponent.ProjectManagement.Repositories.Pm_ParametersRepo;
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
@RequestMapping("/api/v1/perfomance/management/goals")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class Pm_goalController {
    @Autowired
    private Pm_goalRepo pm_goalRepo;
    @Autowired
    private Pm_goalService pm_goalService;
    @Autowired
    private Pm_reviewRepo pm_reviewRepo;
    @Autowired
    Pm_ParametersRepo pm_parametersRepo;

    @PostMapping("/add")
    public ResponseEntity<Pm_goal> addPm_goal(@RequestBody Pm_goal pm_goal) {
//            by using parameter id i can get the parameter name
        Long parameter_id = pm_goal.getParameter_id();
        Optional<Pm_Parameters> parameter = pm_parametersRepo.findById(parameter_id);
        if (parameter.isPresent()){
            String parameter_name = parameter.get().getParameter_name();
            LocalDate currentDate= LocalDate.now();
            int day = currentDate.getDayOfMonth();
            Month month = currentDate.getMonth();
            String getMonth = month.toString();
            int year = currentDate.getYear();
            // Take a date
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime newDate = nowDate.plusMonths(pm_goal.getNext_month_review_delay());

            pm_goal.setNext_review_date(newDate);
            pm_goal.setMonth(getMonth);
            pm_goal.setYear(year);
            pm_goal.setParameter_name(parameter_name);
            Pm_goal newPm_goal = pm_goalService.addPm_goal(pm_goal);

//            Initial Review
            Pm_review initialReview = new Pm_review();
            initialReview.setParameter_id(pm_goal.getParameter_id());
            initialReview.setParameter_name(pm_goal.getParameter_name());
            initialReview.setEmployee_id(pm_goal.getEmployee_id());
            initialReview.setPm_goal_id(pm_goal.getId());
            initialReview.setRemarks("Initial Review");
            initialReview.setRecommendations("Initial Review");
            initialReview.setPercentage_score(0.0);
            initialReview.setMonth(getMonth);
            pm_reviewRepo.save(initialReview);

            return  new ResponseEntity<>(newPm_goal, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pm_goal>> getAllPm_goals () {
        List<Pm_goal> pm_goals = pm_goalService.findAllPm_goals();
        return  new ResponseEntity<>(pm_goals, HttpStatus.OK);
    }

    @GetMapping("/all/by/employee/{employee_id}")
    public ResponseEntity<List<Pm_goalRepo.GoalParameter>> getAllByEmployeeId(@PathVariable("employee_id") Long employee_id) {
        List<Pm_goalRepo.GoalParameter> pm_goals = pm_goalService.findAllByEmployeeId(employee_id);
        return  new ResponseEntity<>(pm_goals, HttpStatus.OK);
    }

    @GetMapping("/detail/all/by/employee/{employee_id}")
    public ResponseEntity<List<Pm_goalRepo.GoalParameter>> findGoalScoreByEmployeeId(@PathVariable("employee_id") Long employee_id) {
        List<Pm_goalRepo.GoalParameter> pm_goals = pm_goalService.findGoalScoreByEmployeeId(employee_id);
        return  new ResponseEntity<>(pm_goals, HttpStatus.OK);
    }

        @GetMapping("/find/{id}")
        public ResponseEntity<Pm_goal> getPm_goalById (@PathVariable("id") Long id){
            Pm_goal pm_goal = pm_goalService.findPm_goalById(id);
            return new ResponseEntity<>(pm_goal, HttpStatus.OK);
        }
    @GetMapping("/find/detail/by/goal/{goal_id}")
    public ResponseEntity<?> findByDetailsByGoalId (@PathVariable("goal_id") Long goal_id){
        Optional<Pm_goalRepo.GoalParameter> pm_goal = pm_goalRepo.findByDetailsByGoalId(goal_id);
        if (pm_goal.isPresent()){
            return new ResponseEntity<>(pm_goal, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        use optional
    }

        @PutMapping("/update/{id}")
        public ResponseEntity<Pm_goal> updatePm_goal(@PathVariable("id") long id, @RequestBody Pm_goal pm_goal){
            Optional<Pm_goal> pm_goalData = pm_goalRepo.findPm_goalById(id);
            if (pm_goalData.isPresent()) {
                Pm_goal _pm_goal = pm_goalData.get();
                LocalDate currentDate= LocalDate.now();
                int day = currentDate.getDayOfMonth();
                Month month = currentDate.getMonth();
                String getMonth = month.toString();
                int year = currentDate.getYear();
                // Take a date
                LocalDateTime updatedDate = _pm_goal.getUpdated_at();
                LocalDateTime newDate = updatedDate .plusMonths(pm_goal.getNext_month_review_delay());

                _pm_goal.setNext_review_date(newDate);
                _pm_goal.setMonth(getMonth);
                _pm_goal.setYear(year);
                _pm_goal.setParameter_name(pm_goal.getParameter_name());
                _pm_goal.setGoal(pm_goal.getGoal());
                _pm_goal.setParameter_id(pm_goal.getParameter_id());
                _pm_goal.setNext_month_review_delay(pm_goal.getNext_month_review_delay());
                _pm_goal.setUpdated_at(LocalDateTime.now());
                if(newDate.toLocalDate() == LocalDateTime.now().toLocalDate()){
                    _pm_goal.setReview_enabled(true);
                }else if(newDate.toLocalDate() != LocalDateTime.now().toLocalDate()) {
                    _pm_goal.setReview_enabled(false);
                }
                return new ResponseEntity<>(pm_goalRepo.save(_pm_goal), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
//        @DeleteMapping("/delete/{id}")
//        public ResponseEntity<Pm_goal> deletePm_goal(@PathVariable("id") Long id){
////        Check if pm_goal has got employees
//            List<EmployeeEntity> employee = employeeRepository.findByPm_goal(id);
//            if(employee.isEmpty() ){
////            has no data, remove
//                pm_goalService.deletePm_goal(id);
//                return new ResponseEntity<>(HttpStatus.OK);
//            }else{
////        has data dont remove
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//        }
        @DeleteMapping("/permanent></delete/{id}")
        public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
            pm_goalService.deletePm_goal(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
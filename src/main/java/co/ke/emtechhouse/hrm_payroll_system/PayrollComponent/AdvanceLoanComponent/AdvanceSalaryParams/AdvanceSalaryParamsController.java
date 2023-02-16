package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AdvanceLoanComponent.AdvanceSalaryParams;

import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
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
@RequestMapping("/api/v1/salary/advance/params")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class AdvanceSalaryParamsController {
    @Autowired
    AdvanceSalaryParamsRepo advanceSalaryParamsRepo;
    @Autowired
    AdvanceSalaryParamsService advanceSalaryParamsService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdvanceSalaryParams(@RequestBody AdvanceSalaryParams advanceSalaryParams){
//        TODO: Restrict params to only one entry
        List<AdvanceSalaryParams> paramsData = advanceSalaryParamsService.findAllAdvanceSalaryParams();
        if (paramsData.size()>0){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: You already have predefined parameter kindly update if you may need to"));
        }else{
            Double max_percentage = advanceSalaryParams.getMax_salary_percentage();
            Double min_percentage = advanceSalaryParams.getMin_salary_percentage();
            advanceSalaryParams.setMax_salary_percentage(max_percentage/100);
            advanceSalaryParams.setMin_salary_percentage(min_percentage/100);
            AdvanceSalaryParams newAdvanceSalaryParams = advanceSalaryParamsService.addAdvanceSalaryParams(advanceSalaryParams);
            return  new ResponseEntity<>(newAdvanceSalaryParams, HttpStatus.CREATED);
        }


    }
    @GetMapping("/all")
    public ResponseEntity<List<AdvanceSalaryParams>> getAllAdvanceSalaryParamss () {
        List<AdvanceSalaryParams> advanceSalaryParamss = advanceSalaryParamsService.findAllAdvanceSalaryParams();
        return  new ResponseEntity<>(advanceSalaryParamss, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<AdvanceSalaryParams> getAdvanceSalaryParamsById (@PathVariable("id") Long id){
        AdvanceSalaryParams advanceSalaryParams = advanceSalaryParamsService.findAdvanceSalaryParamsById(id);
        return new ResponseEntity<>(advanceSalaryParams, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<AdvanceSalaryParams> updateAdvanceSalaryParams(@PathVariable("id") long id, @RequestBody AdvanceSalaryParams advanceSalaryParams){
        Double max_percentage = advanceSalaryParams.getMax_salary_percentage();
        Double min_percentage = advanceSalaryParams.getMin_salary_percentage();
        Optional<AdvanceSalaryParams> advanceSalaryParamsData = advanceSalaryParamsRepo.findAdvanceSalaryParamsById(id);
        if (advanceSalaryParamsData.isPresent()) {
            AdvanceSalaryParams _advanceSalaryParams = advanceSalaryParamsData.get();
            _advanceSalaryParams.setMax_salary_percentage(max_percentage/100);
            _advanceSalaryParams.setMin_salary_percentage(min_percentage/100);
            _advanceSalaryParams.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(advanceSalaryParamsRepo.save(_advanceSalaryParams), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<AdvanceSalaryParams> deleteAdvanceSalaryParams(@PathVariable("id") long id, @RequestBody AdvanceSalaryParams advanceSalaryParams){
        Optional<AdvanceSalaryParams> advanceSalaryParamsData = advanceSalaryParamsRepo.findAdvanceSalaryParamsById(id);
        Boolean is_deleted = true;
        if (advanceSalaryParamsData.isPresent()) {
            AdvanceSalaryParams _advanceSalaryParams = advanceSalaryParamsData.get();
            _advanceSalaryParams.setIs_deleted(is_deleted);
            _advanceSalaryParams.setDeleted_at(LocalDateTime.now());
            return new ResponseEntity<>(advanceSalaryParamsRepo.save(_advanceSalaryParams), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/permanent/delete/{id}")
    public ResponseEntity<AdvanceSalaryParams> deleteAdvanceSalaryParam(@PathVariable("id") Long id){
         advanceSalaryParamsService.deleteAdvanceSalaryParams(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
package co.ke.emtechhouse.hrm_payroll_system.BranchMaintainanceComponenet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/branch")
public class BranchController {
    @Autowired
    BranchService branchService;
    @Autowired
    BranchRepo branchRepo;

    @PostMapping("/add")
    public ResponseEntity<Branch> addBranch(@RequestBody Branch branch){
        try {
            Branch newBranch = branchService.addBranch(branch);
            return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
        }catch (Exception e){
            log.info("Error {} " + e);
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Branch>> getBranches() {
        try {
            List<Branch> branch = branchService.getBranches();
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable("id") Long id) {
        try {
            Branch branch = branchService.getBranchById(id);
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable("id") long id,@RequestBody Branch branch) {
        try {
            Optional<Branch> existingBranch= branchRepo.findById(id);
            if(existingBranch.isPresent()){
                Branch newBranch= existingBranch.get();

                newBranch.setBranch_type(branch.getBranch_type());
                newBranch.setBranch_manager(branch.getBranch_manager());
                newBranch.setCity(branch.getCity());
                newBranch.setEmail(branch.getEmail());
                newBranch.setCalculation_days(branch.getCalculation_days());
                newBranch.setCalculation_hours(branch.getCalculation_hours());
                newBranch.setPhone_number(branch.getPhone_number());
                newBranch.setName(branch.getName());
                branchRepo.save(newBranch);

                return new ResponseEntity<>(newBranch, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
}

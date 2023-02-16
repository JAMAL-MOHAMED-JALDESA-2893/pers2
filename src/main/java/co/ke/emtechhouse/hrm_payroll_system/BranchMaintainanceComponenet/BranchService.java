package co.ke.emtechhouse.hrm_payroll_system.BranchMaintainanceComponenet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class BranchService {
    @Autowired
    private BranchRepo branchRepo;

    public Branch addBranch(Branch branch) {
        try {
            return branchRepo.save(branch);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public List<Branch> getBranches() {
        try {
            return branchRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public Branch getBranchById(Long id) {
        try {
            return branchRepo.findById(id).orElse(null);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

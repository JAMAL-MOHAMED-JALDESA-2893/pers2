package co.ke.emtechhouse.hrm_payroll_system._Batch_Processes;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Batch_processesService {
    @Autowired
    private Batch_processesRepo batch_processesRepo;

    public Batch_processes addBatch_processes(Batch_processes batch_processes){
        try {
            return batch_processesRepo.save(batch_processes);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Batch_processes> findAllBatch_processess(){
        try {
            return batch_processesRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Batch_processes findBatch_processesById(Long id){
        try {
            return batch_processesRepo.findBatch_processesById(id).orElseThrow(()-> new DataNotFoundException("Batch_processes " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }

    public Batch_processes updateBatch_processes(Batch_processes batch_processes){
        try {
            return batch_processesRepo.save(batch_processes);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteBatch_processes(Long id){
        try {
            batch_processesRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }
}


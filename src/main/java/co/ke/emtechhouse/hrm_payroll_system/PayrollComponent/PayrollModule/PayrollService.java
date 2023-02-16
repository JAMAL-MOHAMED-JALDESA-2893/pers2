package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;
import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class PayrollService {
    @Autowired
    private PayrollRepo payrollRepo;
    private String message;
    @Value("${reports_absolute_path}")
    private String files_path;
    Gson gson = new Gson();

    public Payroll addPayroll(Payroll payroll) {
        try {
            payrollRepo.save(payroll);
            gson.toJson(payroll, new FileWriter(files_path+"/addPayroll.json"));
            return payroll;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public List<Payroll> findAllPayrolls() {
        try {
            return payrollRepo.findAll();
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public Payroll findById(Long id){
        try{
            return payrollRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Data " + id +"was not found"));
        } catch (Exception e) {
            log.info("Catched Error {} "+e);
            return null;
        }
    }
    public List<?> findCurrentMonthSalaryDetails(String month, String year,Boolean committedStatus){
        try {
            return payrollRepo.findCurrentMonthSalaryDetails(month, year, committedStatus);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public Payroll updatePayroll(Payroll payroll) {
        try {
            return payrollRepo.save(payroll);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public void deletePayroll(Long id) {
        try {
            payrollRepo.deleteById(id);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
        }
    }
    public  void excellimport(MultipartFile file) throws IOException {
        System.out.println("Got Called");
        List<Payroll> payroll = Excellimports.excelToPayroll(file.getInputStream());
            System.out.println("payroll" +payroll);
        payrollRepo.saveAll(payroll);
        log.info("New stock from excel added");

    }
}



package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.NSSFConfigurations;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NssfService {
    private final NssfRepo nssfRepo;

    public NssfService(NssfRepo nssfRepo) {
        this.nssfRepo = nssfRepo;
    }

    public Nssf addNssf(Nssf nssf){
        try{
            double employeePaymentRate= nssf.getEmployee_payment_rate();
            double employerPaymentRate= nssf.getCompany_payment_rate();
            double totalPaymentRate=employeePaymentRate+employerPaymentRate;

            nssf.setTotal_nssf_rate(totalPaymentRate);
            nssfRepo.save(nssf);
            return nssf;

        }catch (Exception e){
            //log.info("Set Salary Error {} "+e);
            return null;
        }

        //return nssfRepo.save(nssf);
    }

    public List<Nssf> findAllNssf(){
        return nssfRepo.findAll();
    }
    public Nssf findNssfById(Long id){
        return nssfRepo.findNssfById(id).orElseThrow(()-> new DataNotFoundException("Nssf " + id +"was not found"));
    }
    public Nssf updateNssf(Nssf nssf){
        return nssfRepo.save(nssf);
    }
    public void deleteNssf(Long id){
        nssfRepo.deleteNssfById(id);
    }

}
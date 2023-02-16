package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailconfigService {
    @Autowired
    private EmailconfigRepo emailconfigRepo;

    public Emailconfig addEmailconfig(Emailconfig emailconfig){
        return emailconfigRepo.save(emailconfig);
    }
    public List<Emailconfig> findAllEmailconfig(){
        return emailconfigRepo.findAll();
    }
    public Emailconfig findEmailconfigById(Long id){
        return emailconfigRepo.findEmailconfigById(id).orElseThrow(()-> new DataNotFoundException("Emailconfig " + id +"was not found"));
    }
    //
    public Emailconfig updateEmailconfig(Emailconfig emailconfig){
        return emailconfigRepo.save(emailconfig);
    }
}

package co.ke.emtechhouse.hrm_payroll_system.JobPortalComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.ApplicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final  ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    public Application addApplication(Application application){
        return applicationRepository.save(application);
    }
    public List<Application> findAllApplications(){
        return applicationRepository.findAll();
    }
    public Application findApplicationById(Long id){
        return applicationRepository.findApplicationById(id).orElseThrow(()-> new ApplicationNotFoundException("Application was not found!"));
    }
    public Application updateApplication(Application application){
        return applicationRepository.save(application);
    }
    public void deleteApplication(Long id){
        applicationRepository.deleteApplicationById(id);
    }
}

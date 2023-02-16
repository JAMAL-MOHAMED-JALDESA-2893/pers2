
package co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations;
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
@RequestMapping("/api/v1/email/configurations/")
//@PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_DIRECTOR','ROLE_HR','ROLE_SUPERVISOR','ROLE_USER',)")
public class EmailconfigController {
    @Autowired
    private EmailconfigRepo emailconfigRepo;
    @Autowired
    private EmailconfigService emailconfigService;
    @PostMapping("/add")
    public ResponseEntity<?> addEmailconfig(@RequestBody Emailconfig emailconfig){
//         TODO: Check if email type configurations exists
        Optional<Emailconfig> emailConfig = emailconfigRepo.findByEmailType(emailconfig.getEmail_type());
        if (emailConfig.isPresent()){
            return ResponseEntity.unprocessableEntity().body(new MessageResponse("You already have configuration for this. You may need to update to suite your desires."));
        }else{
            Emailconfig newEmailconfig = emailconfigService.addEmailconfig(emailconfig);
            return  new ResponseEntity<>(newEmailconfig, HttpStatus.CREATED);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Emailconfig>> getAllEmailconfigs () {
        List<Emailconfig> emailconfigs = emailconfigService.findAllEmailconfig();
        return  new ResponseEntity<>(emailconfigs, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Emailconfig> getDepartmentById (@PathVariable("id") Long id){
        Emailconfig emailconfig = emailconfigService.findEmailconfigById(id);
        return new ResponseEntity<>(emailconfig, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Emailconfig> updateEmailconfig(@PathVariable("id") long id, @RequestBody Emailconfig emailconfig){
        Optional<Emailconfig> emailconfigData = emailconfigRepo.findEmailconfigById(id);
        if (emailconfigData.isPresent()) {
            Emailconfig _emailconfig = emailconfigData.get();
            _emailconfig.setEmail_type(emailconfig.getEmail_type());
            _emailconfig.setEmail_salutation(emailconfig.getEmail_salutation());
            _emailconfig.setEmail_subject(emailconfig.getEmail_subject());;
            _emailconfig.setEmail_heading(emailconfig.getEmail_heading());
            _emailconfig.setEmail_message(emailconfig.getEmail_message());
            _emailconfig.setEmail_remarks(emailconfig.getEmail_remarks());
            _emailconfig.setEmail_regards_from(emailconfig.getEmail_regards_from());
            _emailconfig.setEmail_organization_name(emailconfig.getEmail_organization_name());
            _emailconfig.setEmail_organization_phone(emailconfig.getEmail_organization_phone());
            _emailconfig.setEmail_organization_mail(emailconfig.getEmail_organization_mail());
            _emailconfig.setEmail_organization_address(emailconfig.getEmail_organization_address());
            _emailconfig.setEmail_organization_location(emailconfig.getEmail_organization_location());
            _emailconfig.setEmail_organization_website(emailconfig.getEmail_organization_website());
            _emailconfig.setUpdated_at(LocalDateTime.now());
            return new ResponseEntity<>(emailconfigRepo.save(_emailconfig), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Emailconfig> deleteEmailconfig(@PathVariable("id") Long id){
       emailconfigRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

//package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.AllowancesComponent.PerdeamComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PerdeamService{
//    private  final PerdeamRipo perdeamRipo;
//
//    @Autowired
//    public PerdeamService(PerdeamRipo perdeamRipo) {
//        this.perdeamRipo = perdeamRipo;
//    }
//        public Allowance_Perdeam addPerdeam(Allowance_Perdeam allowance_perdeam){
//            return perdeamRipo.save(allowance_perdeam);
//        }
//        public List<Allowance_Perdeam> findAllPerdeam(){
//            return perdeamRipo.findAll();
//        }
//        public Allowance_Perdeam findPerdeamById(Long id){
//            return perdeamRipo.findPerdeamById(id).orElseThrow(()-> new DataNotFoundException("Perdeam by " + id +"was not found"));
//        }
//        public Allowance_Perdeam updatePerdeam(Allowance_Perdeam allowance_perdeam){
//            return perdeamRipo.save(allowance_perdeam);
//        }
//        public void deletePerdeamType(Long id){
//            perdeamRipo.deletePerdeamById(id);
//        }
//}

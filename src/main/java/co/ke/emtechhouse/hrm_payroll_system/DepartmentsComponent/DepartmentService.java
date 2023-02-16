package co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DepartmentNotFoundException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.List;

@Service
@Slf4j
public class DepartmentService{
    private final DepartmentRepo departmentRepo;
    @Value("${backup_absolute_path}")
    private String files_path;
    Gson gson = new Gson();


    public DepartmentService(DepartmentRepo departmentRepo) {

        this.departmentRepo = departmentRepo;
    }
    public Department addDepartment(Department department){
        try {
            departmentRepo.save(department);

            gson.toJson(122223, new FileWriter(files_path+"/addDepartment.json"));
            return department;
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Department> findAllDepartments(){
        try {
            return departmentRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Department findDepartmentById(Long id){
        try {
            return departmentRepo.findDepartmentById(id).orElseThrow(()-> new DepartmentNotFoundException("Department " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Department updateDepartment(Department department){
        try {
            return departmentRepo.save(department);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteDepartment(Long id){
        try {
            departmentRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }

    }
}









package co.ke.emtechhouse.hrm_payroll_system.DepartmentsComponent;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
        Optional<Department> findDepartmentById(Long id);
        void deleteDepartmentById(Long id);
}


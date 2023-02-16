package co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DocumentRepo extends JpaRepository<Document,Long> {
    Optional<Document> findDocumentById(Long id);

    @Query(value = "SELECT * FROM document WHERE document.filenameref LIKE :filenameref", nativeQuery = true)
    Optional<Document> findByFilenameref(String filenameref);

    @Query(value = "SELECT * FROM document WHERE document.user_id LIKE :user_id", nativeQuery = true)
    List<Document> findByUserId(String user_id);

    void deleteDocumentById(Long id);
}

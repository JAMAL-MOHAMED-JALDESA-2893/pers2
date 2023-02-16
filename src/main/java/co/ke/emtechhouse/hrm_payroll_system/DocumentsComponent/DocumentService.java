package co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent;

import co.ke.emtechhouse.hrm_payroll_system._exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentService {

    private final DocumentRepo documentRepo;

    public DocumentService(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    public Document addDocument(Document document){
        try {
            return documentRepo.save(document);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Document> findAllDocuments(){
        try {
            return documentRepo.findAll();
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Document findDocumentById(Long id){
        try {
            return documentRepo.findDocumentById(id).orElseThrow(()-> new DataNotFoundException("Document " + id +"was not found"));
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public List<Document> findDocumentByUserId(String user_id){
        try {
            return documentRepo.findByUserId(user_id);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public Document updateDocument(Document document){
        try {
            return documentRepo.save(document);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    public void deleteDocument(Long id){
        try {
            documentRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Error {} "+e);
        }
    }
}



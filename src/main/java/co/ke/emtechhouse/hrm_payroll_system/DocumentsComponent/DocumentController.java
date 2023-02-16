package co.ke.emtechhouse.hrm_payroll_system.DocumentsComponent;


import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Responses.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
//@Api(value = "/Document Upload API", tags = "Documents")
@RequestMapping("/api/v1/documents/")
public class DocumentController {

    private final DocumentRepo documentRepo;
    private final DocumentService documentService;

    //    @Value("${file.upload-dir}")
    public String DIRECTORY = "src/main/resources/Uploads";

    public DocumentController(DocumentRepo documentRepo, DocumentService documentService) {
        this.documentRepo = documentRepo;
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles, @RequestParam("group_by") String group_by, @RequestParam("user_id") String user_id) throws IOException {
        try {
            if(user_id.isEmpty()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Kindly fill all other required fields. This file depends on the employee data"));
            }else {

                List<String> documents = new ArrayList<>();
                for (MultipartFile file : multipartFiles) {
                    String filename = StringUtils.cleanPath(file.getOriginalFilename());
                    String filenameref = LocalDateTime.now() + StringUtils.cleanPath(file.getOriginalFilename());
//            TODO: Figure out a way of using a customised file name when adding a new file
//            store in database files name
//            check file name if exist? then reject tell user to update
                    Optional<Document> documentData = documentRepo.findByFilenameref(filenameref);
                    if (documentData.isPresent()) {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: The file with the name already exist!Kindly change the file naming or update"));
                    } else {
                        Document newDocument = new Document();
                        newDocument.setFilenameref(filenameref);
                        newDocument.setFilename(filename);
                        newDocument.setGroup_by(group_by);
                        newDocument.setUser_id(user_id);
                        Document document = documentService.addDocument(newDocument);
//                TODO: Check if the file has been saved in the databases first
                        Optional<Document> checkData = documentRepo.findByFilenameref(filenameref);
                        if (checkData.isPresent()) {
                            Path fileStorage = get(DIRECTORY, filenameref).toAbsolutePath().normalize();
                            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
                            documents.add(filenameref);
                            return new ResponseEntity<>(document, HttpStatus.CREATED);
                        } else {
                            return ResponseEntity
                                    .badRequest()
                                    .body(new MessageResponse("Error: File not saved"));
                        }
                    }
                }
                return ResponseEntity.ok().body(documents);
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateFiles(@RequestParam("id") Long id, @RequestParam("files") List<MultipartFile> multipartFiles, @RequestParam("group_by") String group_by, @RequestParam("user_id") String user_id) throws IOException {
        try {

//            find the file by id
//            get filerefname
//            update filename with new filename
//            replace file using same filenameref
//            done

            //        Check if the file exist
            Optional<Document> documentData = documentRepo.findDocumentById(id);
            if (documentData.isPresent()) {
//                get filenameref

                List<String> documents = new ArrayList<>();
                for (MultipartFile file : multipartFiles) {
                    String new_filename = StringUtils.cleanPath(file.getOriginalFilename());
                    String old_filenameref = documentData.get().getFilenameref();

                    Path fileStorage = get(DIRECTORY, old_filenameref).toAbsolutePath().normalize();
                    copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
                    documents.add(old_filenameref);
//            TODO: Figure out a way of using a customised file name when adding a new file
//            store in database files name
                    Document _document = documentData.get();
                    _document.setFilename(new_filename);
                    _document.setGroup_by(group_by);
                    _document.setUser_id(user_id);
                    _document.setUpdated_at(LocalDateTime.now());
                    return new ResponseEntity<>(documentRepo.save(_document), HttpStatus.OK);
                }
                return ResponseEntity.ok().body(documents);
            }else {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: File Not found"));
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }

    }
    @GetMapping("download/{id}")
    public ResponseEntity<?> downloadFiles(@PathVariable("id") Long id) throws IOException {
        try {
            //        Get from the user_id
//        from user_id, get filename
            Optional<Document> documentData = documentRepo.findDocumentById(id);
            if (documentData.isPresent()) {
//            download
                Document _document = documentData.get();
                String filename = _document.getFilenameref();
                Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
                if(!Files.exists(filePath)) {
                    throw new FileNotFoundException(filename + " was not found on the server");
                }
                Resource resource = new UrlResource(filePath.toUri());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("File-Name", filename);
                httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                        .headers(httpHeaders).body(resource);
            }else {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: File Not found"));
            }

        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
//    @GetMapping("download/{id}")
//    public ResponseEntity<?> downloadFiles(@PathVariable("id") Long id) throws IOException {
//        try {
//            //        Get from the user_id
////        from user_id, get filename
//            Optional<Document> documentData = documentRepo.findDocumentById(id);
//            if (documentData.isPresent()) {
////            download
//                Document _document = documentData.get();
//                String filename = _document.getFilenameref();
//                System.out.println(filename);
//                Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
//                if(!Files.exists(filePath)) {
//                    throw new FileNotFoundException(filename + " was not found on the server");
//                }
//                Resource resource = new UrlResource(filePath.toUri());
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.add("File-Name", filename);
//                httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
//                return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
//                        .headers(httpHeaders).body(resource);
//            }else {
//                return ResponseEntity
//                        .badRequest()
//                        .body(new MessageResponse("Error: File Not found"));
//            }
//
//        }catch (Exception e) {
//            log.info("Error {} "+e);
//            return null;
//        }
//    }
    @GetMapping("/by/{user_id}")
    public ResponseEntity<List<Document>> getFiles(@PathVariable("user_id") String user_id){
        try {
            System.out.println(user_id);
            List<Document> documents = documentService.findDocumentByUserId(user_id);
            return  new ResponseEntity<>(documents, HttpStatus.OK);
        }catch (Exception e) {
            log.info("Error {} "+e);
            return null;
        }
    }
}

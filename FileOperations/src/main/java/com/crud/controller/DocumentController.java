package com.crud.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crud.entity.Mansi;
import com.crud.repo.DocumentRepo;
import com.crud.service.DocumentService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/doc")
public class DocumentController {

	private static final Logger logger = Logger.getLogger(DocumentService.class.getName());


	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentRepo documentRepo;
	
	 @PostMapping("/upload")
	 public ResponseEntity<String> uploadFile(@RequestParam("doc") MultipartFile file) throws IOException 
	 {
		 String uploadDoc = documentService.UploadFile(file);
		 return ResponseEntity.ok("success");
	 }
	 
		/*
		 * @GetMapping("/download/{fileName}") public byte[] downloadFile(@PathVariable
		 * String fileName) throws IOException {
		 * 
		 * // Retrieve document metadata from MongoDB Mansi m =
		 * documentRepo.findById(fileName).orElse(null);
		 * 
		 * String filePath = m.getFilePath();
		 * 
		 * byte[] file = Files.readAllBytes(new File(filePath).toPath());
		 * 
		 * return file;
		 * 
		 * }
		 */
	 private String FilePath = "D:\\sampleTextEditor\\";
	 
		/*
		 * @GetMapping("/down/{fileName}") public String downloadFile(@PathVariable
		 * String fileName) throws IOException { // Retrieve document metadata from
		 * MongoDB Mansi m = documentRepo.findById(fileName).orElse(null);
		 * 
		 * return m.getFileName();
		 */ 
//	     if (m != null) {
//	         String filePath = m.getFilePath();
//	         File file = new File(filePath);
//
//	         if (file.exists()) {
//	             byte[] fileBytes = Files.readAllBytes(file.toPath());
//
//	             HttpHeaders headers = new HttpHeaders();
//	             headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//	             headers.setContentDispositionFormData("attachment", fileName);
//
//	             return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
//	         } else {
//	             // If the file doesn't exist, return a "Not Found" response
//	             return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
//	         }
//	     } else {
//	         // Document not found, return a "Not Found" response
//	         return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
//	     }
	 
	 @GetMapping("/download/{fileName}")
	    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
	        byte[] fileData = documentService.downloadFileByName(fileName);

	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	        response.getOutputStream().write(fileData);
	        response.flushBuffer();
	    }
	

	 
	 private static final String LOCAL_FILE_DIRECTORY = "D:\\sampleTextEditor\\"; // Local file directory

	    @GetMapping("/{fileName:.+}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
	        // Create the file path by combining the local directory and the file name
	        String filePath = LOCAL_FILE_DIRECTORY + fileName;
	        Path path = Paths.get(filePath);

	        if (Files.exists(path) && !Files.isDirectory(path)) {
	            // Read the file data from the local file system
	            byte[] fileData = Files.readAllBytes(path);

	            // Create a ByteArrayResource from the file data
	            ByteArrayResource resource = new ByteArrayResource(fileData);

	            // Set the response headers
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

	            return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(fileData.length)
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	        } else {
	            // Handle the case where the file is not found locally
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	 

	    @GetMapping("/list-documents")
	    public List<Mansi> listAllDocuments() {
	        return documentService.listAllDocuments();
	    }
		

}

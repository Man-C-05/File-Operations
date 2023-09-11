package com.crud.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crud.entity.Mansi;
import com.crud.repo.DocumentRepo;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepo documentRepo;
	
	private static final Logger logger = Logger.getLogger(DocumentService.class.getName());

	
	private String FilePath = "D:\\sampleTextEditor\\";
	
	public String UploadFile(MultipartFile file) throws IOException
	{
		logger.info("uploading file at"+FilePath);
		String filePath = FilePath+file.getOriginalFilename();
		
		Mansi m = new Mansi();
        m.setFileName(file.getOriginalFilename());
        m.setFilePath(filePath);
        
        byte[] fileContent = file.getBytes();
        
        // Set the file data
        m.setFileData(fileContent);
        
        documentRepo.save(m);
        
        file.transferTo(new File(filePath));
        
        logger.info("File uploaded successfully: " + filePath);
        return filePath;
	}
	
	 public List<Mansi> listAllDocuments() {
		 	
		 	logger.info("getting all documents");
	        return documentRepo.findAll();
	    }
	 
	 
	 public byte[] downloadFileByName(String fileName) throws IOException {
		    Optional<Mansi> optionalFileDocument = documentRepo.findByFileName(fileName);

		    if (optionalFileDocument.isPresent()) {
		        byte[] fileData = optionalFileDocument.get().getFileData();

		        if (fileData != null) {
		            return fileData;
		        } else {
		            throw new FileNotFoundException("File data is null for: " + fileName);
		        }
		    } else {
		        throw new FileNotFoundException("File not found: " + fileName);
		    }
		}

}

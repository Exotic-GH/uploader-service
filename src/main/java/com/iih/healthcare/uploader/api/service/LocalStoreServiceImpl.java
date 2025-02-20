package com.iih.healthcare.uploader.api.service;

import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LocalStoreServiceImpl implements ObjectStoreService {
    private  String baseFolderPath;
    private static final Logger logger = LoggerFactory.getLogger(LocalStoreServiceImpl.class);

    public LocalStoreServiceImpl(@Value("${file.upload.location.BaseFolderPath}") String baseFolderPath) {
        this.baseFolderPath = baseFolderPath;
    }



    @Override
    public String uploadObject(ObjectInfo objectInfo) {
        if (objectInfo == null) {
            logger.error("Received null objectInfo");
            return "Invalid request: objectInfo is null";
        }

        logger.info("Received file upload request: name={}, bucketName={}", objectInfo.getName(), objectInfo.getBucketName());

        if (objectInfo.getContent() == null) {
            logger.error("File content is null");
            return "File content is null";
        }

        if (objectInfo.getContent().length == 0) {
            logger.error("File content is empty");
            return "File content is empty";
        }

        if (objectInfo.getName() == null || objectInfo.getName().isEmpty()) {
            logger.error("File name is null or empty");
            return "File name is null or empty";
        }

        try {
            // Convert byte[] to MultipartFile
            MultipartFile multipartFile = new MockMultipartFile(
                    objectInfo.getName(),
                    objectInfo.getName(),
                    "application/octet-stream",
                    objectInfo.getContent()
            );

            // Ensure the directory exists
            Path  path = Paths.get(baseFolderPath+objectInfo.getBucketName());

            
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Save the file
            Path filePath = path.resolve(multipartFile.getOriginalFilename());
            multipartFile.transferTo(filePath.toFile());

            logger.info("Successfully uploaded {}", multipartFile.getOriginalFilename());
            return "You successfully uploaded " + multipartFile.getOriginalFilename() + "!";
        } catch (IOException e) {
            logger.error("Failed to upload file", e);
            return "Failed to upload " + objectInfo.getName() + " => " + e.getMessage();
        }

    }

    @Override
    public byte[] getObject(GetObjectInfo getObjectInfo) throws Exception {

        try {
            String fileName = getObjectInfo.getName();
            String folderPath = getObjectInfo.getBucketName();

            // Construct the full path
            Path filePath = Paths.get(baseFolderPath, folderPath, fileName);

            // Check if the file exists
            if (!Files.exists(filePath)) {
                throw new IOException("File not found: " + fileName);
            }

            // Read file into byte array
            return Files.readAllBytes(filePath);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<String> getAllObjects(String bucketName) {
        return null;
    }

    @Override
    public String deleteObject(String bucketName, String fileName) {

        // Construct the full path
        Path filePath = Paths.get(baseFolderPath, bucketName, fileName);

        try {
            // Check if the file exists
            if (Files.exists(filePath)) {
                // Delete the file
                Files.delete(filePath);
                logger.info("File " + fileName + " deleted successfully.");
                return "File " + fileName + " deleted successfully.";

            } else {
                logger.error("File " + fileName + " not found.");
                return "File " + fileName + " not found.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "Error deleting file " + fileName + ": " + e.getMessage();
        }
    }
}

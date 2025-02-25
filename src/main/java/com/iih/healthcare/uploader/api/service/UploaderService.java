package com.iih.healthcare.uploader.api.service;

import com.iih.healthcare.uploader.api.ui.controller.UploaderController;
import com.iih.healthcare.uploader.api.ui.model.GetMultipleObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UploaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploaderService.class);

    @Value("${oracleObjectStorage.cloudType}")
    private String cloudType;

    @Autowired
    private ObjectStoreFactory objectStoreFactory;

    /**
     * method for upload document to object storage
     *
     * @param objectInfo
     * @return
     */
    public void uploadToBucket(ObjectInfo objectInfo) {
        LOGGER.info("Document uploading start for  name:{} bucketName:{}",objectInfo.getName(),objectInfo.getBucketName());
        ObjectStoreService objectStoreService = objectStoreFactory.creatObjectStore(cloudType);
        objectStoreService.uploadObject(objectInfo);
    }

    /**
     * method for get single document from object storage
     *
     * @param getObjectInfo
     * @return
     */
    public byte[] getDocumentFromBucket(GetObjectInfo getObjectInfo) throws IOException {
        ObjectStoreService objectStoreService = objectStoreFactory.creatObjectStore(cloudType);
        try {
            byte[] response = objectStoreService.getObject(getObjectInfo);
            LOGGER.info("byte array returned. array size :{}", response.length);
            return response;
        } catch (IOException e){
            throw new IOException(e.getMessage(),e);
        } catch (Exception e) {
            LOGGER.error("getDocumentFromBucket");
            LOGGER.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }


    /**
     * method for get Multiple document from object storage
     *
     * @param getObjectInfo
     * @return
     */
    public List<byte[]> getMultipleDocumentFromBucket(GetMultipleObjectInfo getObjectInfo) throws IOException {
        ObjectStoreService objectStoreService = objectStoreFactory.creatObjectStore(cloudType);
        try {
            List<byte[]> response = objectStoreService.getMultipleObject(getObjectInfo);
            LOGGER.info("byte array returned. array size :{}", response.size());
            return response;
        } catch (IOException e){
            throw new IOException(e.getMessage(),e);
        } catch (Exception e) {
            LOGGER.error("getDocumentFromBucket");
            LOGGER.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public String deleteDocumentFromBucket(String bucketName, String fileName){
        ObjectStoreService objectStoreService = objectStoreFactory.creatObjectStore(cloudType);
        return objectStoreService.deleteObject(bucketName, fileName);
    }

}

package com.iih.healthcare.uploader.api.service;

import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;

import java.util.List;

/**
 * @author Zuhair Ahamed
 * @version 1.0
 * @created 15-06-2022
 */
public interface ObjectStoreService {

    /**
     * Upload file into object storage
     *
     * @param objectInfo
     * @return
     */
    String uploadObject(ObjectInfo objectInfo);

    /**
     * Get file into object storage
     *
     * @param getObjectInfo
     * @return
     */
    byte[] getObject(GetObjectInfo getObjectInfo) throws Exception;

    /**
     * Get all file into object storage in a specific bucket
     *
     * @param bucketName
     * @return
     */
    List<String> getAllObjects(String bucketName);

    /**
     * Delete file from object storage
     *
     * @param bucketName
     * * @param fileName
     * @return
     */
    String deleteObject(String bucketName, String fileName);
}

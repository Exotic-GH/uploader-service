//package com.iih.healthcare.uploader.api.service;
//
//import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
//import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;
//import com.oracle.bmc.auth.AuthenticationDetailsProvider;
//import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
//import com.oracle.bmc.objectstorage.ObjectStorage;
//import com.oracle.bmc.objectstorage.ObjectStorageClient;
//import com.oracle.bmc.objectstorage.model.ObjectSummary;
//import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
//import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
//import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
//import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
//import com.oracle.bmc.objectstorage.responses.DeleteObjectResponse;
//import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
//import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
//import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.Collections;
//import java.util.List;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
///**
// * @author Zuhair Ahamed
// * @version 1.0
// * @created 15-06-2022
// */
//@Service
//public class OracleObjectStoreServiceImpl implements ObjectStoreService {
//
//    private String nameSpace;
//
//    private ObjectStorage objStoreClient = null;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OracleObjectStoreServiceImpl.class);
//
//
//    public OracleObjectStoreServiceImpl(
//            @Value("${oracleObjectStorage.privateKeyLocation}") String privateKeyLocation,
//            @Value("${oracleObjectStorage.tenantId}") String tenantId,
//            @Value("${oracleObjectStorage.userId}") String userId,
//            @Value("${oracleObjectStorage.fingerprint}") String fingerprint,
//            @Value("${oracleObjectStorage.region}") String region,
//            @Value("${oracleObjectStorage.nameSpace}") String nameSpace) {
//
//        try {
//
//            this.nameSpace = nameSpace;
//
//            Supplier<InputStream> privateKeySupplier = () -> {
//                InputStream is = null;
//                try {
//                    is = new FileInputStream(privateKeyLocation);
//                } catch (FileNotFoundException ex) {
//                    LOGGER.error("Problem accessing OCI private key at " + privateKeyLocation + " - " + ex.getMessage());
//                }
//                return is;
//            };
//
//            AuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
//                    .tenantId(tenantId)
//                    .userId(userId)
//                    .fingerprint(fingerprint)
//                    .privateKeySupplier(privateKeySupplier::get)
//                    .build();
//
//            objStoreClient = new ObjectStorageClient(provider);
//            objStoreClient.setRegion(region);
//
//        } catch (Exception ex) {
//            LOGGER.error("==========================OracleObjectStoreServiceImpl=====================================");
//            LOGGER.error(ex.getMessage(),ex);
//            throw new RuntimeException(ex.getMessage());
//        }
//
//    }
//
//    @Override
//    public String uploadObject(ObjectInfo objectInfo) {
//        String result = "FAILED";
//
//        if (objStoreClient == null) {
//            throw new RuntimeException("There was a problem creating the ObjectStorageClient object.");
//        }
//        try {
//            PutObjectRequest por = PutObjectRequest.builder()
//                    .namespaceName(nameSpace)
//                    .bucketName(objectInfo.getBucketName())
//                    .objectName(objectInfo.getName())
//                    .putObjectBody(new ByteArrayInputStream(objectInfo.getContent()))
//                    .build();
//
//            PutObjectResponse poResp = objStoreClient.putObject(por);
//            result = "Successfully submitted Put request for object " + objectInfo.getName() + "in bucket " + objectInfo.getBucketName() + ". OPC reuquest ID is " + poResp.getOpcRequestId();
//            LOGGER.info(result);
//
//        } catch (Exception e) {
//            LOGGER.error("Error on Document uploading");
//            LOGGER.error(e.getMessage(), e);
//            result = "Error storing object in bucket " + e.getMessage();
//        }
//
//        return result;
//    }
//
//    @Override
//    public byte[] getObject(GetObjectInfo getObjectInfo) throws Exception {
//        if (objStoreClient == null) {
//            throw new IllegalArgumentException("There was a problem creating the ObjectStorageClient object. Please check logs");
//        }
//
//        try {
//            GetObjectRequest gor = GetObjectRequest.builder()
//                    .namespaceName(nameSpace)
//                    .bucketName(getObjectInfo.getBucketName())
//                    .objectName(getObjectInfo.getName())
//                    .build();
//            GetObjectResponse response = objStoreClient.getObject(gor);
//            return IOUtils.toByteArray(response.getInputStream());
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//
//    @Override
//    public List<String> getAllObjects(String bucketName) {
//        if (objStoreClient == null) {
//            return Collections.emptyList();
//        }
//        List<String> objNames = null;
//        try {
//            ListObjectsRequest lor = ListObjectsRequest.builder()
//                    .namespaceName(nameSpace)
//                    .bucketName(bucketName)
//                    .build();
//            ListObjectsResponse response = objStoreClient.listObjects(lor);
//
//            objNames = response.getListObjects().getObjects().stream()
//                    .map(ObjectSummary::getName)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            LOGGER.error("Error fetching object list from bucket " + e.getMessage());
//        }
//        return objNames;
//    }
//
//    @Override
//    public String deleteObject(String bucketName, String fileName) {
//        String result = "FAILED";
//
//        if (objStoreClient == null) {
//            LOGGER.error("There was a problem creating the ObjectStorageClient object. Please check logs");
//            return result;
//        }
//        try {
//            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                    .namespaceName(nameSpace)
//                    .bucketName(bucketName)
//                    .objectName(fileName)
//                    .build();
//
//            DeleteObjectResponse response = objStoreClient.deleteObject(deleteObjectRequest);
//            result = "Successfully deleted object " + fileName + "in bucket " + bucketName;
//            LOGGER.info(result);
//
//        } catch (Exception e) {
//            result = "Error deleting object from bucket " + e.getMessage();
//        }
//
//        return result;
//    }
//}

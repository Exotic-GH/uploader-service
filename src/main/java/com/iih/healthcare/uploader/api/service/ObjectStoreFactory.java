package com.iih.healthcare.uploader.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ObjectStoreFactory {


    private String privateKeyLocation;

    private String baseFolderPath;

    private String tenantId;

    private String userId;

    private String fingerprint;

    private String region;

    private String nameSpace;

    private String cloudType;

    public ObjectStoreFactory(
            @Value("${oracleObjectStorage.privateKeyLocation}") String privateKeyLocation,
            @Value("${oracleObjectStorage.tenantId}") String tenantId,
            @Value("${oracleObjectStorage.userId}") String userId,
            @Value("${oracleObjectStorage.fingerprint}") String fingerprint,
            @Value("${oracleObjectStorage.region}") String region,
            @Value("${oracleObjectStorage.nameSpace}") String nameSpace,
            @Value("${oracleObjectStorage.cloudType}") String cloudType,
            @Value("${file.upload.location.BaseFolderPath}") String baseFolderPath
    ) {
        this.privateKeyLocation = privateKeyLocation;
        this.tenantId = tenantId;
        this.userId = userId;
        this.fingerprint = fingerprint;
        this.region = region;
        this.nameSpace = nameSpace;
        this.cloudType = cloudType;
        this.baseFolderPath = baseFolderPath;
    }

    /**
     * Method for choosing the cloud environment
     *
     * @param type
     * @return
     */
    public ObjectStoreService creatObjectStore(String type){

        if (type == null || type.isEmpty())
            return null;
        switch (type) {
            case "ORACLE":
      //          return new OracleObjectStoreServiceImpl(this.privateKeyLocation, this.tenantId, this.userId, this.fingerprint, this.region, this.nameSpace);
            case "AWS":
                return new AwsObjectStoreServiceImpl();
            case "LOCAL":
                return new LocalStoreServiceImpl(this.baseFolderPath);
            default:
                throw new IllegalArgumentException("Unknown Cloud Provider Type ".concat(type));
        }
    }
}

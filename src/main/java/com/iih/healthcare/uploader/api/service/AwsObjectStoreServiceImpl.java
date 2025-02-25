package com.iih.healthcare.uploader.api.service;

import com.iih.healthcare.uploader.api.ui.model.GetMultipleObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;

import java.util.List;

public class AwsObjectStoreServiceImpl implements ObjectStoreService{

    @Override
    public String uploadObject(ObjectInfo objectInfo) {
        return null;
    }

    @Override
    public byte[] getObject(GetObjectInfo getObjectInfo) throws Exception {
        return new byte[0];
    }

    @Override
    public List<String> getAllObjects(String bucketName) {
        return null;
    }

    @Override
    public String deleteObject(String bucketName, String fileName) {
        return null;
    }

    @Override
    public List<byte[]> getMultipleObject(GetMultipleObjectInfo getObjectInfo) throws Exception {
        return null;
    }
}

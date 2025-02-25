package com.iih.healthcare.uploader.api.ui.controller;

import com.iih.healthcare.uploader.api.service.UploaderService;
import com.iih.healthcare.uploader.api.ui.model.GetMultipleObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.GetObjectInfo;
import com.iih.healthcare.uploader.api.ui.model.ObjectInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/document")
public class UploaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploaderController.class);

    @Autowired
    private UploaderService uploaderService;

    @Operation(summary = "Upload Document")
    @ApiResponse(responseCode = "201", description = "Document uploaded successfully...")
    @PostMapping
    public void uploadDocument(@RequestBody ObjectInfo objectInfo) {
        uploaderService.uploadToBucket(objectInfo);
    }

    @Operation(summary = "Get single uploaded document")
    @ApiResponse(responseCode = "200", description = "Document fetched successfully...")
    @PostMapping(path = "/get")
    public byte[] getDocumentFromBucket(@RequestBody GetObjectInfo getObjectInfo) {
        try{
            return uploaderService.getDocumentFromBucket(getObjectInfo);
        } catch (Exception e) {
            LOGGER.error("Error wile trying to download the document");
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

    @Operation(summary = "Delete uploaded document")
    @ApiResponse(responseCode = "200", description = "Document deleted successfully...")
    @DeleteMapping
    public void deleteDocumentFromBucket(@RequestParam(name = "bucketName") String bucketName, @RequestParam(name = "fileName") String fileName) {
        uploaderService.deleteDocumentFromBucket(bucketName, fileName);
    }


    @Operation(summary = "Get Multiple uploaded documents")
    @ApiResponse(responseCode = "200", description = "Document fetched successfully...")
    @PostMapping(path = "/get_list")
    public List<byte[]> getMultipleDocumentFromBucket(@RequestBody GetMultipleObjectInfo getObjectInfo) {
        try{
            return uploaderService.getMultipleDocumentFromBucket(getObjectInfo);
        } catch (Exception e) {
            LOGGER.error("Error wile trying to download the document");
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

}

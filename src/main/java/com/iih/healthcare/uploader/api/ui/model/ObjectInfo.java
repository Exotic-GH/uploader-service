package com.iih.healthcare.uploader.api.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zuhair Ahamed
 * @version 1.0
 * @created 15-06-2022
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectInfo {

    private String name;

    private String bucketName;

//    private MultipartFile content;
    private byte[] content;
}

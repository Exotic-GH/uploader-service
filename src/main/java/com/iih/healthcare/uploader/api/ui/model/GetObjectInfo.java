package com.iih.healthcare.uploader.api.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Zuhair Ahamed
 * @version 1.0
 * @created 15-06-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectInfo {

    private String bucketName;

    private String name;
}

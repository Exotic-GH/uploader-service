package com.iih.healthcare.uploader.api.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMultipleObjectInfo {

    private String bucketName;

    private List<String> nameList;
}

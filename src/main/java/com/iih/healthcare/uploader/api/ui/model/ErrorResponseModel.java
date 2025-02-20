package com.iih.healthcare.uploader.api.ui.model;

import java.util.Date;


/**
 * This class represents the common error model.
 *
 * @author Zuhair Ahamed
 * @version 1.0
 *
 * @since 16-06-2022
 *
 */
public class ErrorResponseModel {

    private Date timeStamp;

    private String errorMessage;

    public ErrorResponseModel(){

    }

    public ErrorResponseModel(Date timeStamp, String errorMessage) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
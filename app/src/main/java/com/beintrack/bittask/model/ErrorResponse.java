package com.beintrack.bittask.model;

import androidx.annotation.DrawableRes;

public class ErrorResponse {

    @DrawableRes
    private int errorImage;
    private String errorHeader, errorMessage;

    public ErrorResponse(int errorImage, String errorHeader, String errorMessage) {
        this.errorImage = errorImage;
        this.errorHeader = errorHeader;
        this.errorMessage = errorMessage;
    }

    @DrawableRes
    public int getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(int errorImage) {
        this.errorImage = errorImage;
    }

    public String getErrorHeader() {
        return errorHeader;
    }

    public void setErrorHeader(String errorHeader) {
        this.errorHeader = errorHeader;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

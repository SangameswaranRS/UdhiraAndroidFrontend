package com.example.sangameswaran.udhira.Entities;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class ErrorEntity {
    String statusCode,message;

    public ErrorEntity(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorEntity() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

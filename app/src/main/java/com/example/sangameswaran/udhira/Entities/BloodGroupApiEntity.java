package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class BloodGroupApiEntity {
    public int statusCode;
    public List<BloodEntity> message;

    public BloodGroupApiEntity(int statusCode, List<BloodEntity> message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public BloodGroupApiEntity() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<BloodEntity> getMessage() {
        return message;
    }

    public void setMessage(List<BloodEntity> message) {
        this.message = message;
    }

    public interface UdhiraRestClientInterface{
        public void onGetBloodGroup(BloodGroupApiEntity bloodGroupApiEntity, VolleyError error);
    }
}

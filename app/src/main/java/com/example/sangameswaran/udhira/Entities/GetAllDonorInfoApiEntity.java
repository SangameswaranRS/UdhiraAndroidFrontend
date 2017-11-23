package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class GetAllDonorInfoApiEntity  {
    int statusCode;
    List<DonorRegistrationEntity> message;

    public GetAllDonorInfoApiEntity() {
    }

    public GetAllDonorInfoApiEntity(int statusCode, List<DonorRegistrationEntity> message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<DonorRegistrationEntity> getMessage() {
        return message;
    }

    public void setMessage(List<DonorRegistrationEntity> message) {
        this.message = message;
    }
    public interface UdhiraRestClientInterface{
        public void onGetAllDonorInfo(GetAllDonorInfoApiEntity apiEntity, VolleyError error);
    }
}

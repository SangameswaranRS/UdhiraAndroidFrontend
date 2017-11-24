package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 24-11-2017.
 */

public class GetAllBloodRequestsApiEntity {
    private int statusCode;
    private List<GetBloodRequestEntity> message;

    public GetAllBloodRequestsApiEntity(int statusCode, List<GetBloodRequestEntity> message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public GetAllBloodRequestsApiEntity() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<GetBloodRequestEntity> getMessage() {
        return message;
    }

    public void setMessage(List<GetBloodRequestEntity> message) {
        this.message = message;
    }

    public interface UdhiraRestClientInterface{
        public void onGettingAllBloodRequests(GetAllBloodRequestsApiEntity getAllBloodRequestsApiEntity, VolleyError error);
    }
}

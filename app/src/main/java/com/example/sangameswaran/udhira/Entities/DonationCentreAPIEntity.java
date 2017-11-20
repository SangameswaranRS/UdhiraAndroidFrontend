package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 21-11-2017.
 */

public class DonationCentreAPIEntity {
    public int statusCode ;
    public List<DonationCentreEntity> message;

    public DonationCentreAPIEntity(int statusCode, List<DonationCentreEntity> message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public DonationCentreAPIEntity() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<DonationCentreEntity> getMessage() {
        return message;
    }

    public void setMessage(List<DonationCentreEntity> message) {
        this.message = message;
    }

    public interface UdhiraRestClientInterface{
        public void onGetCentreDetails(DonationCentreAPIEntity donationCentreAPIEntity, VolleyError error);
    }
}

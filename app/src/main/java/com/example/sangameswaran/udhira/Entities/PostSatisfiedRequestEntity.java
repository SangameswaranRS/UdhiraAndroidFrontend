package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 28-11-2017.
 */

public class PostSatisfiedRequestEntity {
    int requestId,status;

    public PostSatisfiedRequestEntity(int requestId, int status) {
        this.requestId = requestId;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public interface UdhiraRestClientInterface{
        public void onRequestSubmit(ErrorEntity errorEntity, VolleyError error);
    }
}

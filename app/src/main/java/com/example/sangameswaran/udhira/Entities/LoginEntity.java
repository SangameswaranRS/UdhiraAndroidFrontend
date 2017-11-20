package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class LoginEntity {
    String userEmailId;

    String password;

    String imeiIndex;

    public LoginEntity(String userEmailId, String password, String imeiIndex) {
        this.userEmailId = userEmailId;
        this.password = password;
        this.imeiIndex = imeiIndex;
    }

    public LoginEntity() {
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImeiIndex() {
        return imeiIndex;
    }

    public void setImeiIndex(String imeiIndex) {
        this.imeiIndex = imeiIndex;
    }

    public interface UdhiraRestClientInterface{
        public void onLoginInfoSubmit(LoginEntity loginEntity, VolleyError error);
    }
}

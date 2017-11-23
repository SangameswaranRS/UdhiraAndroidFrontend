package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 23-11-2017.
 */

public class AuthPostParamEntity {
    String userName,password;

    public AuthPostParamEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public AuthPostParamEntity() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface UdhiraRestClientInterface{
        public void postAuthParams(AuthPostParamEntity authPostParamEntity,VolleyError error);
    }
}

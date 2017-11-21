package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 21-11-2017.
 */

public class SignupEntity {
    String userEmailId,userName,contactNumber,bloodGroup,imeiIndex,password,loginFlag,canRaiseRequestFlag;

    public SignupEntity(String userEmailId, String userName, String contactNumber, String bloodGroup, String imeiIndex, String password, String loginFlag, String canRaiseRequestFlag) {
        this.userEmailId = userEmailId;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.bloodGroup = bloodGroup;
        this.imeiIndex = imeiIndex;
        this.password = password;
        this.loginFlag = loginFlag;
        this.canRaiseRequestFlag = canRaiseRequestFlag;
    }

    public SignupEntity() {
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getImeiIndex() {
        return imeiIndex;
    }

    public void setImeiIndex(String imeiIndex) {
        this.imeiIndex = imeiIndex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getCanRaiseRequestFlag() {
        return canRaiseRequestFlag;
    }

    public void setCanRaiseRequestFlag(String canRaiseRequestFlag) {
        this.canRaiseRequestFlag = canRaiseRequestFlag;
    }

    public interface UdhiraRestClientInterface{
        public void onGettingResponse(SignupEntity entity, VolleyError error);
    }
}

package com.example.sangameswaran.udhira.Entities;

/**
 * Created by Sangameswaran on 24-11-2017.
 */

public class GetBloodRequestEntity  {
    int requestId;
    int bloodId,age,emergencyStatus,isSatisfied;
    String patientName,hospital,reason,contactNumber,userEmailId;

    public GetBloodRequestEntity(int requestId, int bloodId, int age, int emergencyStatus, int isSatisfied, String patientName, String hospital, String reason, String contactNumber, String userEmailId) {
        this.requestId = requestId;
        this.bloodId = bloodId;
        this.age = age;
        this.emergencyStatus = emergencyStatus;
        this.isSatisfied = isSatisfied;
        this.patientName = patientName;
        this.hospital = hospital;
        this.reason = reason;
        this.contactNumber = contactNumber;
        this.userEmailId = userEmailId;
    }

    public GetBloodRequestEntity() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getBloodId() {
        return bloodId;
    }

    public void setBloodId(int bloodId) {
        this.bloodId = bloodId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEmergencyStatus() {
        return emergencyStatus;
    }

    public void setEmergencyStatus(int emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }

    public int getIsSatisfied() {
        return isSatisfied;
    }

    public void setIsSatisfied(int isSatisfied) {
        this.isSatisfied = isSatisfied;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }
}

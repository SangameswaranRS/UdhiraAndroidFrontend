package com.example.sangameswaran.udhira.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class DonorRegistrationEntity {
    String userEmailId,donorEmailId,donorContactNumber,gender,donorWeight,dateOfBirth,address,donationComments,donorName;
    int bloodGroupId;

    public DonorRegistrationEntity(String userEmailId, String donorEmailId, String donorContactNumber, String gender, String donorWeight, String dateOfBirth, String address, String donationComments, int bloodGroupId,String donorName) {
        this.userEmailId = userEmailId;
        this.donorEmailId = donorEmailId;
        this.donorContactNumber = donorContactNumber;
        this.gender = gender;
        this.donorWeight = donorWeight;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.donationComments = donationComments;
        this.bloodGroupId = bloodGroupId;
        this.donorName=donorName;
    }


    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public DonorRegistrationEntity() {
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getDonorEmailId() {
        return donorEmailId;
    }

    public void setDonorEmailId(String donorEmailId) {
        this.donorEmailId = donorEmailId;
    }

    public String getDonorContactNumber() {
        return donorContactNumber;
    }

    public void setDonorContactNumber(String donorContactNumber) {
        this.donorContactNumber = donorContactNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDonorWeight() {
        return donorWeight;
    }

    public void setDonorWeight(String donorWeight) {
        this.donorWeight = donorWeight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDonationComments() {
        return donationComments;
    }

    public void setDonationComments(String donationComments) {
        this.donationComments = donationComments;
    }

    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public interface UdhiraRestClientInterface{
        public void onSubmitDonorDetails(DonorRegistrationEntity donorRegistrationEntity, VolleyError error);
    }
}

package com.example.sangameswaran.udhira.Entities;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class BloodEntity {
    int bloodGroupId;
    String bloodGroup;

    public BloodEntity(int bloodGroupId, String bloodGroup) {
        this.bloodGroupId = bloodGroupId;
        this.bloodGroup = bloodGroup;
    }

    public BloodEntity() {
    }

    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

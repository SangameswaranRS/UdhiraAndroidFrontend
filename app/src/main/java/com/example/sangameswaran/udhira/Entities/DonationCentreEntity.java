package com.example.sangameswaran.udhira.Entities;

/**
 * Created by Sangameswaran on 21-11-2017.
 */

public class DonationCentreEntity {
    int centreId;
    double lattitude,longitude;
    String centreName;

    public DonationCentreEntity(int centreId, double lattitude, double longitude, String centreName) {
        this.centreId = centreId;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.centreName = centreName;
    }

    public DonationCentreEntity() {
    }

    public int getCentreId() {
        return centreId;
    }

    public void setCentreId(int centreId) {
        this.centreId = centreId;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }
}

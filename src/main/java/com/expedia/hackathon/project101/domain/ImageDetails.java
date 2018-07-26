package com.expedia.hackathon.project101.domain;

public class ImageDetails {
    //location and desc that google api will provide
    String location;
    Double latitude;
    Double longitude;
    String imageb64;

    public ImageDetails(String location, Double latitude, Double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImageb64() {
        return imageb64;
    }

    public void setImageb64(String imageb64) {
        this.imageb64 = imageb64;
    }
}

package com.expedia.hackathon.project101.domain;

import java.util.ArrayList;

public class GoogleRequest {
    ImageObj image;
    ArrayList<GoogleFeatures> features;

    public ImageObj getImage() {
        return image;
    }

    public void setImage(ImageObj image) {
        this.image = image;
    }

    public ArrayList<GoogleFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<GoogleFeatures> features) {
        this.features = features;
    }
}

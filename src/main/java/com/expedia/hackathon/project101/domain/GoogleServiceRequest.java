package com.expedia.hackathon.project101.domain;

import java.util.ArrayList;

public class GoogleServiceRequest {

    ArrayList<GoogleRequest> requests;

    public ArrayList<GoogleRequest> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<GoogleRequest> requests) {
        this.requests = requests;
    }
}

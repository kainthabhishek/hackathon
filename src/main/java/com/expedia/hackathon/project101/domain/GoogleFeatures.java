package com.expedia.hackathon.project101.domain;

public class GoogleFeatures {
    String type;
    Integer maxResults;

    public GoogleFeatures(String type, Integer maxResults) {
        this.type = type;
        this.maxResults = maxResults;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}

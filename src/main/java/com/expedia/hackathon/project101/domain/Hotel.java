package com.expedia.hackathon.project101.domain;

public class Hotel {
    String name;
    String address;
    Integer price;
    Float rating;
    String description;
    String imageUrl;

    public Hotel(String name, String address, Integer price, Float rating, String description, String imageUrl) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

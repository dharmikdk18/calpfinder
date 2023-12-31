package com.example.clapphonefinder.model;

public class OnboardingModel {

    private int image;
    private String title;
    private String description;

    public OnboardingModel(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public OnboardingModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

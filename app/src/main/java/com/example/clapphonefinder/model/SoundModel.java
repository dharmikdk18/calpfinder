package com.example.clapphonefinder.model;

public class SoundModel {

    private String name;
    private int sound;
    private int image;

    public SoundModel(String name, int sound, int image) {
        this.name = name;
        this.sound = sound;
        this.image = image;
    }

    public SoundModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

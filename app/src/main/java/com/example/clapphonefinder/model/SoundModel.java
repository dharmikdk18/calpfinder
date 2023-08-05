package com.example.clapphonefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SoundModel implements Parcelable {

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

    protected SoundModel(Parcel in) {
        name = in.readString();
        sound = in.readInt();
        image = in.readInt();
    }

    public static final Creator<SoundModel> CREATOR = new Creator<SoundModel>() {
        @Override
        public SoundModel createFromParcel(Parcel in) {
            return new SoundModel(in);
        }

        @Override
        public SoundModel[] newArray(int size) {
            return new SoundModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(sound);
        parcel.writeInt(image);
    }
}

package com.example.ahmed.ismaliacityapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Coffes implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("rating")
    private float Rating;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;

    public Coffes() {
    }

    public Coffes(int id, float rating, String name, String icon) {
        this.id = id;
        Rating = rating;
        this.name = name;
        this.icon = icon;
    }

    protected Coffes(Parcel in) {
        id = in.readInt();
        Rating = in.readFloat();
        name = in.readString();
        icon = in.readString();
    }

    public static final Creator<Coffes> CREATOR = new Creator<Coffes>() {
        @Override
        public Coffes createFromParcel(Parcel in) {
            return new Coffes(in);
        }

        @Override
        public Coffes[] newArray(int size) {
            return new Coffes[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        if (icon != null) {
            return icon;
        }
        return null;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Cofees{" +
                "name='" + name + '\'' +
                ", rating='" + Rating + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeFloat(Rating);
        parcel.writeString(name);
        parcel.writeString(icon);
    }

}
